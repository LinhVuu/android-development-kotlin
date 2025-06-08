package kbs.apps.mobiledevelopment.employeemanagementsystem

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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Link to button element on the UI.
        val cancelButton = findViewById<Button>(R.id.buttonCancel)
        val saveButton = findViewById<Button>(R.id.buttonSave)

        // Link to the combo boxes.
        val departmentSpinner = findViewById<Spinner>(R.id.spinnerDepartment)
        val managerSpinner = findViewById<Spinner>(R.id.spinnerManager)

        // Add options to the combo box
        // https://developer.android.com/develop/ui/views/components/spinner
        // https://www.geeksforgeeks.org/spinner-in-kotlin/
        val departments = listOf("Engineering", "HR", "Finance", "Marketing", "Sales")

        val departmentAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, departments)
        departmentSpinner.adapter = departmentAdapter

        lifecycleScope.launch {
            val employeesFullname = withContext(Dispatchers.IO) {
                val db = EmployeeDatabase.getDatabase(applicationContext)
                db.employeeDao().getAllEmployeeFullNames()
            }

            val managerAdapter = ArrayAdapter(
                this@AddEmployeeActivity,
                android.R.layout.simple_spinner_dropdown_item,
                employeesFullname
            )
            managerSpinner.adapter = managerAdapter
        }

        // Register the launcher.
        imagePickerLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val uri = result.data?.data
                if (uri != null) {
                    selectedImageUri = uri
                    val imageView = findViewById<ImageView>(R.id.profileImage)
                    imageView.setImageURI(uri)
                }
            }
        }

        // Open image picker when users click on the image.
        val imageView = findViewById<ImageView>(R.id.profileImage)
        imageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            imagePickerLauncher.launch(intent)
        }

        // When users click on the 'Cancel' button, go back to previous screen.
        // https://stackoverflow.com/questions/51684696/how-to-goback-to-the-previous-activity-kotlin-using-a-button-outside-the-act
        cancelButton.setOnClickListener {
            finish()
        }

        saveButton.setOnClickListener {
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

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() ||
                address.isEmpty() || position.isEmpty() || salary.isEmpty() ||
                department.isEmpty() || reportTo.isEmpty() || dateHired.isEmpty()
            ) {
                Toast.makeText(this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Date validation and parsing.
            val formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")

            // Validate date formats.
            val parsedDateHired = try {
                java.time.LocalDate.parse(dateHired, formatter)
            } catch (e: Exception) {
                Toast.makeText(this, "Date Hired must be in DD/MM/YYYY format.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val parsedStartDate = try {
                java.time.LocalDate.parse(startDate, formatter)
            } catch (e: Exception) {
                Toast.makeText(this, "Start Date must be in DD/MM/YYYY format.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val parsedEndDate = if (endDate.isEmpty()) null else try {
                java.time.LocalDate.parse(endDate, formatter)
            } catch (e: Exception) {
                Toast.makeText(this, "End Date must be in DD/MM/YYYY format.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val employee = Employee(
                imageUri = selectedImageUri?.toString() ?: "default_photo",
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
                isOngoing = parsedEndDate == null,
                endDate = parsedEndDate
            )

            employeeViewModel.addEmployee(employee)
            finish()
        }
    }

    // Handle the result of image selection.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                selectedImageUri = uri
                val imageView = findViewById<ImageView>(R.id.profileImage)
                imageView.setImageURI(uri)
            }
        }
    }

}