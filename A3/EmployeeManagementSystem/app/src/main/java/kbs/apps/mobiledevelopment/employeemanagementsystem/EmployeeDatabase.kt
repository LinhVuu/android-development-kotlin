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

// Getting Started with Room Database in Android using Kotlin
// https://www.linkedin.com/pulse/todo-list-app-room-database-kotlin-mvvm-architecture-abeythilake
// https://www.youtube.com/watch?v=-LNg-K7SncM
// The database is the container for all the data in the Room database.
// It is defined as an abstract class that extends the RoomDatabase class.
// The database class is annotated with the @Database annotation
// and includes a list of all the entities in the database and the version of the database.
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

    }

    private class AppDatabaseCallback(private val context: Context) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            // Launch on background thread to avoid blocking UI
            CoroutineScope(Dispatchers.IO).launch {
                val dao = getDatabase(context).employeeDao()

                // Create sample data.
                val sampleEmployees = listOf(
                    Employee(
                        imageUri = "snow_white",
                        firstName = "Snow",
                        lastName = "White",
                        address = "105 Gray Street",
                        city = "Adelaide",
                        phone = "0987654321",
                        email = "snow.white@kbs.edu.au",
                        position = "Data Engineer",
                        department = "IT",
                        reportTo = "",
                        dateHired = LocalDate.of(2025, 3, 1),
                        startDate = LocalDate.of(2025, 3, 1),
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

                // Insert sample data to the database.
                dao.insertAll(sampleEmployees)
            }
        }

        // Function to generate random employees.
        private fun generateRandomEmployee(): Employee {

            // A list of random values.
            val firstNames = listOf("Alice", "Bob", "Charlie", "Diana", "Eve", "Frank")
            val lastNames = listOf("Smith", "Johnson", "Williams", "Brown", "Jones")
            val departments = listOf("IT", "HR", "Finance", "Marketing")
            val positions = listOf("Software Engineer", "HR Manager", "Accountant", "Marketing Specialist")

            // Get a random value for each field.
            val firstName = firstNames.random()
            val lastName = lastNames.random()
            val fullName = "$firstName.$lastName".lowercase()
            val dateHired = LocalDate.now().minusDays((0..1000).random().toLong())
            val startDate = dateHired
            val endDate = startDate.plusYears(1)

            // Return a random employee.
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
                reportTo = "Snow White",
                dateHired = dateHired,
                startDate = startDate,
                endDate = endDate,
                latestSalary = (20..50).random().toDouble()
            )
        }
    }
}