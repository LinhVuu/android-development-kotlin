package kbs.apps.mobiledevelopment.employeemanagementsystem

import androidx.room.TypeConverter
import java.time.LocalDate

class Converters {

    @TypeConverter
    // Convert LocalDate to String.
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.toString()
    }

    @TypeConverter
    // Convert String to LocalDate.
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it) }
    }
}