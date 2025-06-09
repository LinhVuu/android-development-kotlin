package kbs.apps.mobiledevelopment.employeemanagementsystem

import java.time.LocalDate

class Functions {
    companion object {

        // Check if the given email matches a valid email address pattern.
        // https://stackoverflow.com/a/5296201
        fun isValidEmail(email: String): Boolean {
            val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
            return emailRegex.matches(email)
        }

        // Validate phone number format using a general regex pattern.
        // https://stackoverflow.com/a/6358825
        fun isValidPhone(phone: String): Boolean {
            val phoneRegex = Regex("^\\+?[0-9]{10,15}$")
            return phoneRegex.matches(phone)
        }

        // Validate and parses a date string in "dd/MM/yyyy" format.
        // Return null and shows a Toast if invalid.
        fun parseDateOrShowError(dateStr: String, label: String): LocalDate? {
            return try {
                val formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")
                LocalDate.parse(dateStr, formatter)
            } catch (e: Exception) {
                null
            }
        }

        // Validate that start date is not before date hired.
        fun validateStartAfterHired(startDate: LocalDate, dateHired: LocalDate): Boolean {
            return if (startDate.isBefore(dateHired)) {
                false
            } else true
        }

        // Validate that end date is after start date.
        fun validateEndAfterStart(startDate: LocalDate, endDate: LocalDate?): Boolean {
            return if (endDate != null && endDate.isBefore(startDate)) {
                false
            } else true
        }

    }
}