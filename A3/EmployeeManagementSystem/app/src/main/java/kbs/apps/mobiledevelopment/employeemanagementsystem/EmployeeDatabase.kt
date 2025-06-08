package kbs.apps.mobiledevelopment.employeemanagementsystem

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

@Database(entities = [Employee::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
public abstract class EmployeeDatabase : RoomDatabase()
{
    abstract fun employeeDao(): EmployeeDao

    companion object
    {
        @Volatile
        private var INSTANCE: EmployeeDatabase? = null

        fun getDatabase(context: Context): EmployeeDatabase
        {
            return INSTANCE ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EmployeeDatabase::class.java,
                    "employee_database"
                ).fallbackToDestructiveMigration()
                    .addCallback(AppDatabaseCallback(context))
//                )
                    .build()
                INSTANCE = instance
                instance
            }
        }

        fun addEmployee(context: Context, employee: Employee) {
            CoroutineScope(Dispatchers.IO).launch {
                getDatabase(context).employeeDao().insertEmployee(employee)
            }
        }
    }

    private class AppDatabaseCallback(private val context: Context) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            // Launch on background thread to avoid blocking UI
            CoroutineScope(Dispatchers.IO).launch {
                val dao = getDatabase(context).employeeDao()
                val sampleEmployees = listOf(
                    Employee(
                        imageUri = "snow_white",
                        firstName = "Snow",
                        lastName = "White",
                        address = "105 Gray Street",
                        city = "Adelaide",
                        phone = "+61987654321",
                        email = "snow.white@kbs.edu.au",
                        position = "Data Engineer",
                        department = "IT",
                        reportTo = "Linh Vuu",
                        dateHired = LocalDate.of(2025, 3, 1),
                        startDate = LocalDate.of(2025, 3, 1),
                        isOngoing = true,
                        endDate = LocalDate.of(2026, 3, 1),
                        latestSalary = 20.7
                    ),
                    generateRandomEmployee(),
                    generateRandomEmployee(),
                    generateRandomEmployee(),
                    generateRandomEmployee(),
                    generateRandomEmployee(),
                    generateRandomEmployee()
                )
                dao.insertAll(sampleEmployees)
            }
        }

        private fun generateRandomEmployee(): Employee {
            val firstNames = listOf("Alice", "Bob", "Charlie", "Diana", "Eve", "Frank")
            val lastNames = listOf("Smith", "Johnson", "Williams", "Brown", "Jones")
            val departments = listOf("IT", "HR", "Finance", "Marketing")
            val positions = listOf("Software Engineer", "HR Manager", "Accountant", "Marketing Specialist")

            val firstName = firstNames.random()
            val lastName = lastNames.random()
            val fullName = "$firstName.$lastName".lowercase()
            val dateHired = LocalDate.now().minusDays((0..1000).random().toLong())
            val startDate = dateHired
            val endDate = startDate.plusYears(1)

            return Employee(
                imageUri = "default_photo",
                firstName = firstName,
                lastName = lastName,
                address = "${(1..999).random()} Example St",
                city = "Adelaide",
                phone = "+61${(400000000..499999999).random()}",
                email = "$fullName@kbs.edu.au",
                position = positions.random(),
                department = departments.random(),
                reportTo = "Linh Vuu",
                dateHired = dateHired,
                startDate = startDate,
                isOngoing = true,
                endDate = endDate,
                latestSalary = (20..50).random().toDouble()
            )
        }
    }
}