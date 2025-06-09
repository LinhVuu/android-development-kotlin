package kbs.apps.mobiledevelopment.employeemanagementsystem

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

// This class acts as a middle layer between the ViewModel and the Room database (DAO).
class EmployeeRepository (private val employeeDao: EmployeeDao)
{
    // LiveData list of all employees fetched from the DAO.
    val allEmployees: LiveData<List<Employee>> = employeeDao.getAllEmployees()

    @WorkerThread
    suspend fun insertEmployee(employee: Employee)
    {
        // Calls DAO to insert the employee.
        employeeDao.insertEmployee(employee)
    }

    @WorkerThread
    suspend fun updateEmployee(employee: Employee)
    {
        // Calls DAO to update the employee.
        employeeDao.updateEmployee(employee)
    }

}