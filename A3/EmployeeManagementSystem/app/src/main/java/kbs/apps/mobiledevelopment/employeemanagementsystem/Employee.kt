package kbs.apps.mobiledevelopment.employeemanagementsystem
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
@Entity(tableName = "employee_table")
data class Employee(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "image_uri") val imageUri: String? = "default_photo",
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "position") val position: String,
    @ColumnInfo(name = "department") val department: String,
    @ColumnInfo(name = "report_to") val reportTo: String,
    // How to represent dates in Kotlin/Android
    // https://stackoverflow.com/questions/53252759/how-do-i-represent-dates-in-kotlin-android
    @ColumnInfo(name = "date_hired") val dateHired: LocalDate,
    @ColumnInfo(name = "start_date") val startDate: LocalDate,
    @ColumnInfo(name = "is_ongoing") val isOngoing: Boolean,
    @ColumnInfo(name = "end_date") val endDate: LocalDate?,
    @ColumnInfo(name = "latest_salary") val latestSalary: Double
): Parcelable
{
    fun getFullName(): String {
        return "$firstName $lastName"
    }

    fun getInitials(): String {
        val firstInitial = firstName.firstOrNull()?.uppercaseChar() ?: ""
        val lastInitial = lastName.firstOrNull()?.uppercaseChar() ?: ""
        return "$firstInitial$lastInitial"
    }

    fun hasLeftCompany(): Boolean {
        return !isOngoing && endDate != null && endDate.isBefore(LocalDate.now())
    }
}
