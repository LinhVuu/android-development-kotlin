package kbs.apps.mobiledevelopment.employeemanagementsystem

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
// https://www.youtube.com/watch?v=N7J27pBTtuI
// Using ViewModel helps in separating the UI-related data from the UI components, making it
//easier to manage the data lifecycle and ensuring that data survives configuration changes.
//Additionally, LiveData can be observed, allowing for automatic updates of the UI
//whenever the underlying data changes.
class EmployeeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: EmployeeRepository
    val allEmployees: LiveData<List<Employee>>

    init {
        // Initialize the repository and LiveData for all employees
        val employeeDao = EmployeeDatabase.getDatabase(application).employeeDao()
        repository = EmployeeRepository(employeeDao)
        allEmployees = repository.allEmployees
    }

    // Adds a new employee to the database
    fun addEmployee(employee: Employee) = viewModelScope.launch(Dispatchers.IO) {

        // Calls the repository to insert the new employee into the database.
        try {
            repository.insertEmployee(employee)
        } catch (e: Exception) {
            // Log the error for debugging purposes.
            Log.e("InsertError", "Failed to insert employee", e)
        }

        // Log a debug message indicating the employee was inserted.
        Log.d("Insert", "Employee inserted: $employee")
    }

    // Updates an existing employee in the database
    fun updateEmployee(employee: Employee) = viewModelScope.launch(Dispatchers.IO) {
        // Calls the repository to update the employee in the database.
        try {
            repository.updateEmployee(employee)
        } catch (e: Exception) {
            // Log the error for debugging purposes.
            Log.e("UpdateError", "Failed to update employee", e)
        }
        // Log a debug message indicating the employee was updated.
        Log.d("Update", "Employee updated: $employee")
    }

}