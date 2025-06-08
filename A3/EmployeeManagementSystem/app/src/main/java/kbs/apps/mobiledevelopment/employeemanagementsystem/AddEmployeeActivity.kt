package kbs.apps.mobiledevelopment.employeemanagementsystem

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class AddEmployeeActivity : AppCompatActivity() {
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
            } else {
                val employee = Employee(
                    imageUri = "default_photo",
                    firstName = firstName,
                    lastName = lastName,
                    phone = phone,
                    email = email,
                    position = position,
                    department = department,
                    reportTo = reportTo,
                    dateHired = LocalDate.parse(dateHired),
                    latestSalary = salary.toDoubleOrNull() ?: 0.0,
                    address = address,
                    city = city,
                    startDate = LocalDate.parse(startDate),
                    isOngoing = endDate.isEmpty(),
                    endDate = LocalDate.parse(endDate),
                )

                lifecycleScope.launch(Dispatchers.IO) {
                    val db = EmployeeDatabase.getDatabase(applicationContext)
                    db.employeeDao().insertEmployee(employee)
                    withContext(Dispatchers.Main) {
                        finish()
                    }
                }
            }
        }
    }
}