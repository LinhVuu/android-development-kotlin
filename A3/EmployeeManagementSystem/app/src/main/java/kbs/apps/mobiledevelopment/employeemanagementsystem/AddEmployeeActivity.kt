package kbs.apps.mobiledevelopment.employeemanagementsystem

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class AddEmployeeActivity : AppCompatActivity() {

    // Define the view model and the launcher.
    private val employeeViewModel: EmployeeViewModel by viewModels()
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>

    // Initialise variables to hold the URI.
    var selectedImageUri: Uri? = null
    val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_employee)

        // Link to button element on the UI.
        val cancelButton = findViewById<Button>(R.id.buttonCancel)
        val saveButton = findViewById<Button>(R.id.buttonSave)
        val imageView = findViewById<ImageView>(R.id.profileImage)

        // Register the launcher.
        // https://developer.android.com/training/data-storage/shared/photopicker
        val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                if (uri != null) {
                    contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    selectedImageUri = uri
                    try {
                        imageView.setImageURI(uri)
                    } catch (e: SecurityException) {
                        Toast.makeText(this, "Cannot load image. Please reselect.", Toast.LENGTH_SHORT).show()
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

        // Link to the combo boxes.
        val departmentSpinner = findViewById<Spinner>(R.id.spinnerDepartment)
        val managerSpinner = findViewById<Spinner>(R.id.spinnerManager)

        // Add options to the combo box.
        // https://developer.android.com/develop/ui/views/components/spinner
        // https://www.geeksforgeeks.org/spinner-in-kotlin/
        val departments = listOf("Engineering", "HR", "Finance", "Marketing", "Sales")

        // Add values to Department combo box.
        val departmentAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, departments)
        departmentSpinner.adapter = departmentAdapter

        // Get all employees for users to select a manager.
        lifecycleScope.launch {
            val employeesFullname = withContext(Dispatchers.IO) {
                val db = EmployeeDatabase.getDatabase(this@AddEmployeeActivity)
                val list = db.employeeDao().getAllEmployeeFullNames()

                // Besides the list of employees to select from, there is an empty option to choose
                // because the employee may have or may not have any manager / supervisor.
                listOf("") + list // prepend empty item
            }

            // Add values to Manager combo box.
            val managerAdapter = ArrayAdapter(
                this@AddEmployeeActivity,
                android.R.layout.simple_spinner_dropdown_item,
                employeesFullname
            )
            managerSpinner.adapter = managerAdapter

        }

        // When users click on the 'Cancel' button, go back to previous screen.
        // https://stackoverflow.com/questions/51684696/how-to-goback-to-the-previous-activity-kotlin-using-a-button-outside-the-act
        cancelButton.setOnClickListener {
            finish()
        }

        // When users click on the Save button, save the data into the database.
        saveButton.setOnClickListener {

            // Link to elements on the UI.
            val firstNameInput = findViewById<EditText>(R.id.editTextFirstName)
            val lastNameInput = findViewById<EditText>(R.id.editTextLastName)
            val emailInput = findViewById<EditText>(R.id.editTextEmail)
            val phoneInput = findViewById<EditText>(R.id.editTextPhone)
            val addressInput = findViewById<EditText>(R.id.editTextAddress)
            val cityInput = findViewById<EditText>(R.id.editTextCity)
            val positionInput = findViewById<EditText>(R.id.editTextPosition)
            val salaryInput = findViewById<EditText>(R.id.editTextSalary)
            val departmentSpinner = findViewById<Spinner>(R.id.spinnerDepartment)
            val managerSpinner = findViewById<Spinner>(R.id.spinnerManager)
            val dateHiredInput = findViewById<EditText>(R.id.editTextDateHired)
            val startDateInput = findViewById<EditText>(R.id.editTextStartDate)
            val endDateInput = findViewById<EditText>(R.id.editTextEndDate)

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
            val reportTo = managerSpinner.selectedItem?.toString()?.trim() ?: ""
            val dateHired = dateHiredInput.text.toString().trim()
            val startDate = startDateInput.text.toString().trim()
            val endDate = endDateInput.text.toString().trim()

            // Ensure all required fields are provided.
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() ||
                address.isEmpty() || position.isEmpty() || salary.isEmpty() ||
                department.isEmpty() || dateHired.isEmpty()
            ) {
                Toast.makeText(this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate email address.
            if (!Functions.isValidEmail(email)) {
                Toast.makeText(this, "Invalid email address.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate phone number.
            if (!Functions.isValidPhone(phone)) {
                Toast.makeText(this, "Invalid phone number.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate and parse dates.
            val parsedDateHired = Functions.parseDateOrShowError(dateHired, "Date Hired")
            if (parsedDateHired == null) {
                Toast.makeText(this, "Date Hired must be in DD/MM/YYYY format.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate and parse dates.
            val parsedStartDate = Functions.parseDateOrShowError(startDate, "Start Date")
            if (parsedStartDate == null) {
                Toast.makeText(this, "Start Date must be in DD/MM/YYYY format.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate and parse dates.
            val parsedEndDate = if (endDate.isEmpty()) null else {
                val parsed = Functions.parseDateOrShowError(endDate, "End Date")
                if (parsed == null) {
                    Toast.makeText(this, "End Date must be in DD/MM/YYYY format.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                parsed
            }

            // Validate that start date is not before date hired.
            if (!Functions.validateStartAfterHired(parsedStartDate, parsedDateHired)) {
                Toast.makeText(this, "Start Date must be the same as or after Date Hired.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate that start date is not before end date.
            if (!Functions.validateEndAfterStart(parsedStartDate, parsedEndDate)) {
                Toast.makeText(this, "End Date must be after Start Date.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Generate a new employee based on the provided data.
            val employee = Employee(
                imageUri = selectedImageUri?.toString() ?: "",
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

            // Add the new employee to the database.
            employeeViewModel.addEmployee(employee)

            // Close the form.
            // https://stackoverflow.com/questions/51684696/how-to-goback-to-the-previous-activity-kotlin-using-a-button-outside-the-act
            finish()
        }
    }

    // Handle the result of image selection.
    // https://codetheory.in/android-pick-select-image-from-gallery-with-intents/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            try {
                if (data != null && data.data != null) {
                    // Get the image URI from the returned Intent.
                    val uri = data.data

                    // Save the selected image URI to a variable for later use.
                    selectedImageUri = uri

                    // Find the ImageView where we want to display the image.
                    val imageView = findViewById<ImageView>(R.id.profileImage)

                    // Set the selected image to display in the ImageView.
                    imageView.setImageURI(uri)
                } else {
                    // If the image wasn't selected or something went wrong, show a message.
                    Toast.makeText(this, "Image selection failed.", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Failed to load image: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }

}
