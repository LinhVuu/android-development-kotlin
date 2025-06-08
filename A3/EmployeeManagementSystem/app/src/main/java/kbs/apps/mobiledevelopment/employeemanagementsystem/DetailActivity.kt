package kbs.apps.mobiledevelopment.employeemanagementsystem

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.CheckBox
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val firstName = findViewById<TextView>(R.id.firstName)
        val lastName = findViewById<TextView>(R.id.lastName)
        val address = findViewById<TextView>(R.id.address)
        val city = findViewById<TextView>(R.id.city)
        val phoneNumber = findViewById<TextView>(R.id.phoneNumber)
        val email = findViewById<TextView>(R.id.email)
        val position = findViewById<TextView>(R.id.position)
        val department = findViewById<TextView>(R.id.department)
        val manager = findViewById<TextView>(R.id.manager)
        val dateHired = findViewById<TextView>(R.id.dateHired)
        val startDate = findViewById<TextView>(R.id.startDate)
        val endDate = findViewById<TextView>(R.id.endDate)
        val latestSalary = findViewById<TextView>(R.id.latestSalary)
        val ongoingEmployee = findViewById<CheckBox>(R.id.ongoingEmployee)
        val profileImage = findViewById<ImageView>(R.id.profileImage)

        val employee: Employee? = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra("employee", Employee::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("employee")
        }

        if (employee == null) {
            Toast.makeText(this, "No employee data found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        employee?.let {
            firstName.text = it.firstName
            lastName.text = it.lastName
            address.text = it.address
            city.text = it.city
            phoneNumber.text = it.phone
            email.text = it.email
            position.text = it.position
            department.text = it.department
            manager.text = it.reportTo
            dateHired.text = it.dateHired.toString()
            startDate.text = it.startDate.toString()
            endDate.text = it.endDate?.toString() ?: "N/A"
            latestSalary.text = it.latestSalary.toString()
            ongoingEmployee.isChecked = it.isOngoing

            val imageRes = resources.getIdentifier(it.imageUri, "drawable", packageName)
            if (imageRes != 0) {
                profileImage.setImageResource(imageRes)
            }
        }

        // Link to button element on the UI.
        val backButton = findViewById<Button>(R.id.buttonBack)

        // When users click on the 'Back' button, go back to previous screen.
        // https://stackoverflow.com/questions/51684696/how-to-goback-to-the-previous-activity-kotlin-using-a-button-outside-the-act
        backButton.setOnClickListener {
            finish()
        }

        val deleteButton = findViewById<Button>(R.id.buttonDelete)

        deleteButton.setOnClickListener {
            employee?.let { emp ->
                val builder = android.app.AlertDialog.Builder(this)
                builder.setTitle("Delete Employee")
                builder.setMessage("Are you sure you want to delete this employee?")
                builder.setPositiveButton("Yes") { _, _ ->
                    val db = EmployeeDatabase.getDatabase(this)
                    CoroutineScope(Dispatchers.IO).launch {
                        db.employeeDao().deleteEmployee(emp)
                        runOnUiThread {
                            finish()
                        }
                    }
                }
                builder.setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                builder.create().show()
            }
        }
    }
}