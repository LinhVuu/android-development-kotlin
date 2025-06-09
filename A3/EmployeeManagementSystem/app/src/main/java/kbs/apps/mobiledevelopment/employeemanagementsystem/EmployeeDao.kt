package kbs.apps.mobiledevelopment.employeemanagementsystem

import androidx.lifecycle.LiveData
import androidx.room.*

// Getting Started with Room Database in Android using Kotlin
// https://www.linkedin.com/pulse/todo-list-app-room-database-kotlin-mvvm-architecture-abeythilake
// https://www.youtube.com/watch?v=-LNg-K7SncM
// DAOs provide an interface for interacting with the database.
// They define the SQL queries that will be used to access, insert, update, or delete data in the
// database. Each DAO is annotated with the @Dao annotation.
@Dao
interface EmployeeDao {

    // Function to get all employees.
    @Query("SELECT * FROM employee_table ORDER BY date_hired DESC")
    fun getAllEmployees(): LiveData<List<Employee>>

    // Function to insert an employees.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employee: Employee)

    // Function to insert a list of employees.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(employees: List<Employee>)

    // Function to update employee.
    @Update
    suspend fun updateEmployee(employee: Employee)

    // Function to delete employee.
    @Delete
    suspend fun deleteEmployee(employee: Employee)

    // Function to get all full names.
    @Query("SELECT first_name || ' ' || last_name FROM employee_table")
    suspend fun getAllEmployeeFullNames(): List<String>
}