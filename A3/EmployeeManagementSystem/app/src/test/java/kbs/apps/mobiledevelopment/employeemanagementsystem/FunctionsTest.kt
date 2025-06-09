package kbs.apps.mobiledevelopment.employeemanagementsystem

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.time.LocalDate

@RunWith(JUnit4::class)
class FunctionsTest {

    // Test Email Address.
    @Test
    fun testIsValidEmail() {
        assertTrue(Functions.isValidEmail("test@example.com"))
        assertFalse(Functions.isValidEmail("invalid-email"))
    }

    // Test Phone number.
    @Test
    fun testIsValidPhone() {
        assertTrue(Functions.isValidPhone("+61412345678"))
        assertFalse(Functions.isValidPhone("123"))
    }

    // Test Email edge cases.
    @Test
    fun testIsValidEmailEdgeCases() {
        assertTrue(Functions.isValidEmail("user.name+tag@domain.co.uk"))
        assertFalse(Functions.isValidEmail("user@.com"))
        assertFalse(Functions.isValidEmail("@domain.com"))
        assertFalse(Functions.isValidEmail("user@domain"))
    }

    // Test Phone number edge cases.
    @Test
    fun testIsValidPhoneEdgeCases() {

        // Australian mobile without country code
        assertTrue(Functions.isValidPhone("0412345678"))

        // Too short
        assertFalse(Functions.isValidPhone("+12"))

        // Letters in number
        assertFalse(Functions.isValidPhone("abcdefg123"))
    }

    // Test Validate and Parse Date with Valid Date.
    @Test
    fun testParseDateOrShowError_ValidDate() {
        val result = Functions.parseDateOrShowError("01/01/2024", "Date")
        assertNotNull(result)
        assertEquals(LocalDate.of(2024, 1, 1), result)
    }

    // Test Validate and Parse Date with Invalid Date.
    @Test
    fun testParseDateOrShowError_InvalidDate() {
        val result = Functions.parseDateOrShowError("2024-01-01", "Date")
        assertNull(result) // Wrong format
    }

    // Test Validate and Parse Date with Empty Input.
    @Test
    fun testParseDateOrShowError_EmptyInput() {
        val result = Functions.parseDateOrShowError("", "Date")
        assertNull(result)
    }

    // Test Validate Start Date After Hired Date.
    @Test
    fun testValidateStartAfterHired_Valid() {
        val hired = LocalDate.of(2023, 1, 1)
        val start = LocalDate.of(2023, 1, 1)
        assertTrue(Functions.validateStartAfterHired(start, hired))
    }

    // Test Validate Start Date Before Hired Date.
    @Test
    fun testValidateStartAfterHired_Invalid() {
        val hired = LocalDate.of(2023, 1, 1)
        val start = LocalDate.of(2022, 12, 31)
        assertFalse(Functions.validateStartAfterHired(start, hired))
    }

    // Test Validate End Date After Start Date.
    @Test
    fun testValidateEndAfterStart_Valid() {
        val start = LocalDate.of(2023, 1, 1)
        val end = LocalDate.of(2023, 12, 31)
        assertTrue(Functions.validateEndAfterStart(start, end))
    }

    // Test Validate End Date Equals Start Date.
    @Test
    fun testValidateEndAfterStart_EqualDates() {
        val date = LocalDate.of(2023, 1, 1)
        assertTrue(Functions.validateEndAfterStart(date, date))
    }

    // Test Validate End Date After Start Date with Null End Date.
    @Test
    fun testValidateEndAfterStart_NullEndDate() {
        val start = LocalDate.of(2023, 1, 1)
        assertTrue(Functions.validateEndAfterStart(start, null))
    }

    // Test Validate End Date Before Start Date.
    @Test
    fun testValidateEndAfterStart_Invalid() {
        val start = LocalDate.of(2023, 12, 31)
        val end = LocalDate.of(2023, 1, 1)
        assertFalse(Functions.validateEndAfterStart(start, end))
    }
}