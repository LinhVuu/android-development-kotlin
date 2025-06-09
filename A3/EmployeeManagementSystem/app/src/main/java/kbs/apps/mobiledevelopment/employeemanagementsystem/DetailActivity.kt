package kbs.apps.mobiledevelopment.employeemanagementsystem

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
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

interface OnUpdateButtonClickListener {
    fun onUpdateButtonClicked()
}

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)

        // Get the employee from the MainActivity page.
        val i = intent
        val employee: Employee?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            employee = i?.getParcelableExtra("employee", Employee::class.java)
        } else {
            @Suppress("DEPRECATION", "INFERRED_TYPE_VARIABLE_INTO_POSSIBLE_EMPTY_INTERSECTION")
            employee = i.getParcelableExtra("employee")
        }

        // For debugging purposes.
        // Log the behaviour of the app to see if at this point, it can get the employee or not.
        Log.d("DetailActivity", "Received employee = $employee")

        // Pass employee to the ViewEmployeeFragment.
        val viewFragment = ViewEmployeeFragment().apply {
            arguments = Bundle().apply {
                putParcelable("employee", employee)
            }
        }

        // Start this page with ViewEmployeeFragment.
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, viewFragment)
            addToBackStack(null)
            commit()
        }

        // Link to button element on the UI.
        val backButton = findViewById<Button>(R.id.buttonBack)
        val updateButton = findViewById<Button>(R.id.buttonUpdate)
        val deleteButton = findViewById<Button>(R.id.buttonDelete)

        // When users click on the 'Back' button, go back to previous screen.
        // https://stackoverflow.com/questions/51684696/how-to-goback-to-the-previous-activity-kotlin-using-a-button-outside-the-act
        backButton.setOnClickListener {
            finish()
        }

        // When users click on the 'Update' button, open a form for users to update employee's info.
        updateButton.setOnClickListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)

            // The Update button serves 2 purposes.
            // 1. On the UpdateEmployeeFragment, it is "Save" to save the info
            // after users update the info
            if (currentFragment is OnUpdateButtonClickListener) {
                currentFragment.onUpdateButtonClicked()
            } else {

                // 2. On the ViewEmployeeFragment, it is "Update" to open a form
                // for users to update employee's info.
                val updateFragment = UpdateEmployeeFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable("employee", employee)
                    }
                }

                // Open UpdateEmployeeFragment.
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragmentContainerView, updateFragment)
                    addToBackStack(null)
                    commit()
                }

                // When UpdateEmployeeFragment is displayed, change button text to "Save"
                updateButton.text = "Save"
            }
        }

        // When users click on the 'Delete' button, ask whether user wants to delete.
        // If so, delete the record.
        deleteButton.setOnClickListener {
            employee?.let { emp ->
                val builder = AlertDialog.Builder(this)
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

                // If no, simply close the notification.
                builder.setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                builder.create().show()
            }
        }
    }
}