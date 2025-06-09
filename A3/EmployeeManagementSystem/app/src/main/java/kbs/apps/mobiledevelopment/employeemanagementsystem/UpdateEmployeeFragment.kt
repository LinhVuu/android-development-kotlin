package kbs.apps.mobiledevelopment.employeemanagementsystem

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.fragment.app.viewModels
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.format.DateTimeFormatter

class UpdateEmployeeFragment : Fragment(R.layout.fragment_update_employee), OnUpdateButtonClickListener {

    // Define the view model and the launcher.
    private val employeeViewModel: EmployeeViewModel by viewModels()
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>

    // Initialise variables to hold the URI.
    var selectedImageUri: Uri? = null
    val PICK_IMAGE_REQUEST = 1

    private var currentEmployee: Employee? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Link to the elements on the UI.
        val firstNameInput = view.findViewById<EditText>(R.id.editTextFirstName)
        val lastNameInput = view.findViewById<EditText>(R.id.editTextLastName)
        val emailInput = view.findViewById<EditText>(R.id.editTextEmail)
        val phoneInput = view.findViewById<EditText>(R.id.editTextPhone)
        val addressInput = view.findViewById<EditText>(R.id.editTextAddress)
        val cityInput = view.findViewById<EditText>(R.id.editTextCity)
        val positionInput = view.findViewById<EditText>(R.id.editTextPosition)
        val salaryInput = view.findViewById<EditText>(R.id.editTextSalary)
        val dateHiredInput = view.findViewById<EditText>(R.id.editTextDateHired)
        val startDateInput = view.findViewById<EditText>(R.id.editTextStartDate)
        val endDateInput = view.findViewById<EditText>(R.id.editTextEndDate)
        val imageView = view.findViewById<ImageView>(R.id.profileImage)

        // Register the launcher.
        imagePickerLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == android.app.Activity.RESULT_OK) {
                val uri = result.data?.data
                if (uri != null) {
                    selectedImageUri = uri
                    try {
                        imageView.setImageURI(uri)
                    } catch (e: SecurityException) {
                        Toast.makeText(requireContext(), "Cannot load image. Please reselect.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // Open image picker when users click on the image.
        imageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            imagePickerLauncher.launch(intent)
        }

        currentEmployee = arguments?.getParcelable("employee")

        // Link to the combo boxes.
        val departmentSpinner = view.findViewById<Spinner>(R.id.spinnerDepartment)
        val managerSpinner = view.findViewById<Spinner>(R.id.spinnerManager)

        // Add options to the combo box
        // https://developer.android.com/develop/ui/views/components/spinner
        // https://www.geeksforgeeks.org/spinner-in-kotlin/
        val departments = listOf("Engineering", "HR", "Finance", "Marketing", "Sales")

        // Add values to Department combo box.
        val departmentAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, departments)
        departmentSpinner.adapter = departmentAdapter

        // Get all employees for users to select a manager.
        lifecycleScope.launch {
            val employeesFullname = withContext(Dispatchers.IO) {
                val db = EmployeeDatabase.getDatabase(requireContext())
                val list = db.employeeDao().getAllEmployeeFullNames()

                // Besides the list of employees to select from, there is an empty option to choose
                // because the employee may have or may not have any manager / supervisor.
                listOf("") + list // prepend empty item
            }

            // Add values to Manager combo box.
            val managerAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                employeesFullname
            )
            managerSpinner.adapter = managerAdapter

        }

        currentEmployee?.let { emp ->
            // Get the photo.
            val imageRes = resources.getIdentifier(emp.imageUri, "drawable", requireContext().packageName)
            if (imageRes != 0) {
                imageView.setImageResource(imageRes)
            }

            // Get data to populate on the form.
            firstNameInput.setText(emp.firstName)
            lastNameInput.setText(emp.lastName)
            emailInput.setText(emp.email)
            phoneInput.setText(emp.phone)
            addressInput.setText(emp.address)
            cityInput.setText(emp.city)
            positionInput.setText(emp.position)
            salaryInput.setText(emp.latestSalary.toString())
            departmentSpinner.setSelection((departmentSpinner.adapter as ArrayAdapter<String>).getPosition(emp.department))

            // If the previous manager no longer exists, the spinner will display empty and let users select a new one.
            // Users can either select a new one or leave it empty.
            (managerSpinner.adapter as? ArrayAdapter<String>)?.let { adapter ->
                val position = adapter.getPosition(emp.reportTo)
                if (position >= 0) {
                    managerSpinner.setSelection(position)
                } else {
                    managerSpinner.setSelection(0) // default to empty
                    Toast.makeText(requireContext(), "Manager '${emp.reportTo}' not found. Please select a new one.", Toast.LENGTH_SHORT).show()
                }
            }
            dateHiredInput.setText(emp.dateHired.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
            startDateInput.setText(emp.startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
            endDateInput.setText(emp.endDate?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) ?: "")

        }

    }

    // When users click on the Save button, save the employee.
    override fun onUpdateButtonClicked() {
        saveEmployee()
    }

    private fun saveEmployee() {

        // Collect input fields and save the employee (same logic as previously in saveButton).
        view?.let { view ->

            // Link to elements on the UI.
            val firstNameInput = view.findViewById<EditText>(R.id.editTextFirstName)
            val lastNameInput = view.findViewById<EditText>(R.id.editTextLastName)
            val emailInput = view.findViewById<EditText>(R.id.editTextEmail)
            val phoneInput = view.findViewById<EditText>(R.id.editTextPhone)
            val addressInput = view.findViewById<EditText>(R.id.editTextAddress)
            val cityInput = view.findViewById<EditText>(R.id.editTextCity)
            val positionInput = view.findViewById<EditText>(R.id.editTextPosition)
            val salaryInput = view.findViewById<EditText>(R.id.editTextSalary)
            val departmentSpinner = view.findViewById<Spinner>(R.id.spinnerDepartment)
            val managerSpinner = view.findViewById<Spinner>(R.id.spinnerManager)
            val dateHiredInput = view.findViewById<EditText>(R.id.editTextDateHired)
            val startDateInput = view.findViewById<EditText>(R.id.editTextStartDate)
            val endDateInput = view.findViewById<EditText>(R.id.editTextEndDate)

            // Get input from the UI.
            val firstName = firstNameInput.text.toString().trim()
            val lastName = lastNameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val phone = phoneInput.text.toString().trim()
            val address = addressInput.text.toString().trim()
            val city = cityInput.text.toString().trim()
            val position = positionInput.text.toString().trim()
            val salary = salaryInput.text.toString().trim()
            val department = departmentSpinner.selectedItem?.toString()?.trim() ?: ""
            val reportTo = managerSpinner.selectedItem?.toString()?.trim().takeIf { it?.isNotEmpty() == true }
            val dateHired = dateHiredInput.text.toString().trim()
            val startDate = startDateInput.text.toString().trim()
            val endDate = endDateInput.text.toString().trim()

            // Ensure all required fields are provided.
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() ||
                address.isEmpty() || position.isEmpty() || salary.isEmpty() ||
                department.isEmpty() || dateHired.isEmpty()
            ) {
                Toast.makeText(requireContext(), "Please fill in all required fields.", Toast.LENGTH_SHORT).show()
                return
            }

            // Validate email address.
            if (!Functions.isValidEmail(email)) {
                Toast.makeText(requireContext(), "Invalid email address.", Toast.LENGTH_SHORT).show()
                return
            }

            // Validate phone number.
            if (!Functions.isValidPhone(phone)) {
                Toast.makeText(requireContext(), "Invalid phone number.", Toast.LENGTH_SHORT).show()
                return
            }

            // Validate and parse dates.
            val parsedDateHired = Functions.parseDateOrShowError(dateHired, "Date Hired")
            if (parsedDateHired == null) {
                Toast.makeText(requireContext(), "Date Hired must be in DD/MM/YYYY format.", Toast.LENGTH_SHORT).show()
                return
            }

            // Validate and parse dates.
            val parsedStartDate = Functions.parseDateOrShowError(startDate, "Start Date")
            if (parsedStartDate == null) {
                Toast.makeText(requireContext(), "Start Date must be in DD/MM/YYYY format.", Toast.LENGTH_SHORT).show()
                return
            }

            // Validate and parse dates.
            val parsedEndDate = if (endDate.isEmpty()) null else {
                val parsed = Functions.parseDateOrShowError(endDate, "End Date")
                if (parsed == null) {
                    Toast.makeText(requireContext(), "End Date must be in DD/MM/YYYY format.", Toast.LENGTH_SHORT).show()
                    return
                }
                parsed
            }

            // Validate that start date is not before date hired.
            if (!Functions.validateStartAfterHired(parsedStartDate, parsedDateHired)) {
                Toast.makeText(requireContext(), "Start Date must be the same as or after Date Hired.", Toast.LENGTH_SHORT).show()
                return
            }

            // Validate that start date is not before end date.
            if (!Functions.validateEndAfterStart(parsedStartDate, parsedEndDate)) {
                Toast.makeText(requireContext(), "End Date must be after Start Date.", Toast.LENGTH_SHORT).show()
                return
            }

            // Get the new data to update.
            val employee = Employee(
                imageUri = selectedImageUri?.toString() ?: currentEmployee?.imageUri ?: "default_photo",
                firstName = firstName,
                lastName = lastName,
                phone = phone,
                email = email,
                position = position,
                department = department,
                reportTo = reportTo,
                dateHired = parsedDateHired,
                latestSalary = salary.toDoubleOrNull() ?: 0.0,
                address = address,
                city = city,
                startDate = parsedStartDate,
                endDate = parsedEndDate
            )

            if (currentEmployee == null) {
                Toast.makeText(requireContext(), "No employee found to update.", Toast.LENGTH_SHORT).show()
                return
            }

            // Update employee.
            val updatedEmployee = employee.copy(id = currentEmployee!!.id)

            try {
                employeeViewModel.updateEmployee(updatedEmployee)
            } catch (e: Exception) {
                Log.e("UpdateError", "Failed to update", e)
                Toast.makeText(requireContext(), "Update failed. Please try again.", Toast.LENGTH_SHORT).show()
            }

            // Pass the employee to get the updated data of that employee.
            val refreshedViewFragment = ViewEmployeeFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("employee", updatedEmployee)
                }
            }

            // Refresh the page to load the newest data.
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, refreshedViewFragment)
                .commit()
        }
    }
}