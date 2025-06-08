package kbs.apps.mobiledevelopment.mathsforkids

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FunctionsTest {

    @Test
    // a + b.
    fun testCalculateQuestion1ValidInput(){
        val result = Functions().calculateQuestion1(5,5)
        assertEquals(10, result)
    }

    @Test
    // a + b.
    fun testCalculateQuestion1WithZero(){
        val result = Functions().calculateQuestion1(0,0)
        assertEquals(0, result)
    }

    @Test
    // a x b.
    fun testCalculateQuestion2ValidInput(){
        val result = Functions().calculateQuestion2(3,4)
        assertEquals(12, result)
    }

    @Test
    // a x b.
    fun testCalculateQuestion2WithZero(){
        val result = Functions().calculateQuestion2(0,0)
        assertEquals(0, result)
    }

    @Test
    // a - b.
    fun testCalculateQuestion3ValidInput(){
        val result = Functions().calculateQuestion3(50,8)
        assertEquals(42, result)
    }

    @Test
    // a - b.
    fun testCalculateQuestion3WithZero(){
        val result = Functions().calculateQuestion3(0,0)
        assertEquals(0, result)
    }

    @Test
    // a - b.
    fun testCalculateQuestion3NegativeResult(){
        val result = Functions().calculateQuestion3(9,100)
        assertEquals(-91, result)
    }

    @Test
    // Numbers less than the input number.
    fun testCalculateQuestion4ValidInputMultipleResult(){
        val result = Functions().calculateQuestion4(9,listOf(4,5,10))
        assertEquals(listOf("4","5"), result)
    }

    @Test
    // Numbers less than the input number.
    fun testCalculateQuestion4ValidInputOneResult(){
        val result = Functions().calculateQuestion4(50,listOf(40,50,100))
        assertEquals(listOf("40"), result)
    }

    @Test
    // Numbers less than the input number.
    fun testCalculateQuestion4ValidInputNoResult(){
        val result = Functions().calculateQuestion4(30,listOf(40,50,100))
        assertTrue(result.isEmpty())
    }

    @Test
    // a / b
    fun testCalculateQuestion5ValidInputDivideByItself(){
        val result = Functions().calculateQuestion5(5,5)
        assertEquals(1, result)
    }

    @Test
    // a / b
    fun testCalculateQuestion5ValidInputDivideByOne(){
        val result = Functions().calculateQuestion5(10,1)
        assertEquals(10, result)
    }

    @Test
    // a / b
    fun testCalculateQuestion5WithZero(){
        val result = Functions().calculateQuestion5(10,0)
        assertEquals(0, result)
    }

    @Test
    // Test Validate result.
    fun testValidateResultAllCorrect(){
        val userAnswers = listOf("15", "Correct", "10", listOf("90","80"), "37")
        val correctAnswers = listOf("15", "Correct", "10", listOf("90","80"), "37")
        val (results, points) = Functions().validateResult(userAnswers,correctAnswers)
        assertEquals(100, points)
        assertEquals(5, results.size)
    }

    @Test
    // Test Validate result.
    fun testValidateResultAllFail(){
        val userAnswers = listOf("5", "Incorrect", "15", listOf("91","80"), "90")
        val correctAnswers = listOf("15", "Correct", "10", listOf("90","80"), "37")
        val (results, points) = Functions().validateResult(userAnswers,correctAnswers)
        assertEquals(0, points)
        assertEquals(5, results.size)
    }

    @Test
    // Test Validate result.
    fun testValidateResultFourCorrect(){
        val userAnswers = listOf("5", "Correct", "10", listOf("90","80"), "37")
        val correctAnswers = listOf("15", "Correct", "10", listOf("90","80"), "37")
        val (results, points) = Functions().validateResult(userAnswers,correctAnswers)
        assertEquals(80, points)
        assertEquals(5, results.size)
    }

    @Test
    // Test Validate result.
    fun testValidateResultThreeCorrect(){
        val userAnswers = listOf("15", "Correct", "10", listOf("91","81"), "27")
        val correctAnswers = listOf("15", "Correct", "10", listOf("90","80"), "37")
        val (results, points) = Functions().validateResult(userAnswers,correctAnswers)
        assertEquals(60, points)
        assertEquals(5, results.size)
    }

    @Test
    // Test Validate result.
    fun testValidateResultTwoCorrect(){
        val userAnswers = listOf("15", "Correct", "11", listOf("91","81"), "17")
        val correctAnswers = listOf("15", "Correct", "10", listOf("90","80"), "37")
        val (results, points) = Functions().validateResult(userAnswers,correctAnswers)
        assertEquals(40, points)
        assertEquals(5, results.size)
    }

    @Test
    // Test Validate result.
    fun testValidateResultOneCorrect(){
        val userAnswers = listOf("5", "Correct", "4", listOf("0","50"), "77")
        val correctAnswers = listOf("15", "Correct", "10", listOf("90","80"), "37")
        val (results, points) = Functions().validateResult(userAnswers,correctAnswers)
        assertEquals(20, points)
        assertEquals(5, results.size)
    }
}