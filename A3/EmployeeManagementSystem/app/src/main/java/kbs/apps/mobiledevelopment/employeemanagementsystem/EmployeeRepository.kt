package kbs.apps.mobiledevelopment.employeemanagementsystem

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class EmployeeRepository (private val employeeDao: EmployeeDao)
{
    val allEmployees: LiveData<List<Employee>> = employeeDao.allEmployees()

    @WorkerThread
    suspend fun insertEmployee(employee: Employee)
    {
        employeeDao.insertEmployee(employee)
    }

    @WorkerThread
    suspend fun updateEmployee(employee: Employee)
    {
        employeeDao.updateEmployee(employee)
    }

}