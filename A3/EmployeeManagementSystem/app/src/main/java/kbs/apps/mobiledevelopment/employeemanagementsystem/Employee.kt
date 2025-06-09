package kbs.apps.mobiledevelopment.employeemanagementsystem
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

// Getting Started with Room Database in Android using Kotlin
// https://medium.com/@harimoradiya123/getting-started-with-room-database-in-android-using-kotlin-92f84b6a5e6c
@Parcelize
@Entity(tableName = "employee_table")
data class Employee(

    // First column is ID, it is auto generated.
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    // Other columns in table "employee_table"
    @ColumnInfo(name = "image_uri") val imageUri: String? = "default_photo",
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "position") val position: String,
    @ColumnInfo(name = "department") val department: String,

    // The employee may have or may not have any manager / supervisor.
    @ColumnInfo(name = "report_to") val reportTo: String? = null,

    // How to represent dates in Kotlin/Android
    // https://stackoverflow.com/questions/53252759/how-do-i-represent-dates-in-kotlin-android
    @ColumnInfo(name = "date_hired") val dateHired: LocalDate,
    @ColumnInfo(name = "start_date") val startDate: LocalDate,
    @ColumnInfo(name = "end_date") val endDate: LocalDate?,
    @ColumnInfo(name = "latest_salary") val latestSalary: Double
): Parcelable
{
    // Function to get full name from first name and last name.
    fun getFullName(): String {
        return "$firstName $lastName"
    }

    // Function to get initials from first name and last name.
    fun getInitials(): String {
        val firstInitial = firstName.firstOrNull()?.uppercaseChar() ?: ""
        val lastInitial = lastName.firstOrNull()?.uppercaseChar() ?: ""
        return "$firstInitial$lastInitial"
    }

    // Function to verify if the employee has left the company.
    fun hasLeftCompany(): Boolean {

        // When the end date is before today, this function returns TRUE.
        return endDate != null && endDate.isBefore(LocalDate.now())
    }
}
