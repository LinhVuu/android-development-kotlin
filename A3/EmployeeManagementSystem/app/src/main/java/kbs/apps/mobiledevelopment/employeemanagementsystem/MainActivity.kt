// https://stackoverflow.com/questions/37598165/how-do-i-allow-users-to-upload-images-to-my-app-and-use-them/37598432#37598432

package kbs.apps.mobiledevelopment.employeemanagementsystem


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import java.time.LocalDate
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EmployeeAdapter
    private val employeeViewModel: EmployeeViewModel by viewModels() // Kotlin property delegate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Setup RecyclerView
        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewEmployee)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Setup Adapter to show employee's details.
        adapter = EmployeeAdapter { employee ->
            showDetail(employee)
        }
        recyclerView.adapter = adapter
        employeeViewModel.allEmployees.observe(this) { employees ->
            adapter.submitList(employees)

            // For debugging purposes.
            // To show the number of employees in the database.
            Log.d("MainActivity", "Employee list size: ${employees.size}")
        }

        // Link to elements on the UI.
        val addButton = findViewById<FloatingActionButton>(R.id.floatingButtonAddEmployee)

        // When clicking the button, move to the AddEmployee page.
        addButton.setOnClickListener {

            // Move to the AddEmployee page.
            val intent = Intent(this, AddEmployeeActivity::class.java)

            // Start the next screen.
            startActivity(intent)

        }

    }

    // Show Detail of each employee.
    private fun showDetail(employee: Employee) {

        // Pass the employee to the next page to show details.
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("employee", employee)

        // Start the next page.
        startActivity(intent)
    }


}