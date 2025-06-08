package kbs.apps.mobiledevelopment.employeemanagementsystem

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employee_table ORDER BY id ASC")
    fun allEmployees(): LiveData<List<Employee>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employee: Employee)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(employees: List<Employee>)

    @Update
    suspend fun updateEmployee(employee: Employee)

    @Delete
    suspend fun deleteEmployee(employee: Employee)

    @Query("SELECT first_name || ' ' || last_name FROM employee_table")
    suspend fun getAllEmployeeFullNames(): List<String>
}