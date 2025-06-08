package kbs.apps.mobiledevelopment.employeemanagementsystem

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EmployeeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: EmployeeRepository
    val allEmployees: LiveData<List<Employee>>

    init {
        val employeeDao = EmployeeDatabase.getDatabase(application).employeeDao()
        repository = EmployeeRepository(employeeDao)
        allEmployees = repository.allEmployees
    }

    fun addEmployee(employee: Employee) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertEmployee(employee)
        Log.d("Insert", "Employee inserted: $employee")
    }

}