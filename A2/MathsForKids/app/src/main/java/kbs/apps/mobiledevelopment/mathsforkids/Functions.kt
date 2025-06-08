package kbs.apps.mobiledevelopment.mathsforkids

class Functions {

    // Calculate result of question 1.
    fun calculateQuestion1(num1: Int, num2: Int): Int{

        // Return the result.
        return num1 + num2

    }

    // Calculate result of question 2.
    fun calculateQuestion2(num1: Int, num2: Int): Int{

        // Return the result.
        return num1 * num2

    }

    // Calculate result of question 3.
    fun calculateQuestion3(num1: Int, num2: Int): Int{

        // Return the result.
        return num1 - num2

    }

    // Calculate result of question 4.
    fun calculateQuestion4(num: Int, listNumbers: List<Int>): List<String>{

        // Define a list to store the result.
        var results = mutableListOf<String>()

        // Loop through each number in the list of options.
        for ((index,value) in listNumbers.withIndex()) {

            // If the number is less than the number in the question.
            if (value < num) {

                // Add that number to the list of result.
                results.add(value.toString())
            }

        }
        // Return the result.
        return results

    }

    // Calculate result of question 5.
    fun calculateQuestion5(num1: Int, num2: Int): Int{

        // Return 0 if num2 is 0.
        if (num2 == 0) {
            return 0

        } else {

            // Return the result.
            return num1 / num2

        }

    }

    // This function is to validate the result.
    // Test validateUserInput
    // Random numbers (eg. 1 + 1 - random numbers) in questions
    fun validateResult(userAnswers: List<Any>, correctAnswers: List<Any>): Pair<List<String>, Int> {

        // Initialise variables to store results and points.
        var results = mutableListOf<String>()
        var points: Int = 0

        // Loop through each answer and check with the correct answers.
        // https://stackoverflow.com/questions/37609071/array-list-iteration-without-extra-object-allocations
        // https://discuss.kotlinlang.org/t/1-how-can-i-change-text-colour-of-one-word-in-a-textview-using-kotlin-dont-even-know-where-to-start/12348
        for ((index,value) in userAnswers.withIndex()) {
            if (value == correctAnswers[index]){
                results.add("<font color = '#03A9F4'> ✅ Q${index + 1}: $value </font>")
                points += 20
            } else {
                results.add("<font color = '#FF4081'> ❌ Q${index + 1}: $value " +
                        "(Correct answer: ${correctAnswers[index]}) </font>")
            }
        }

        return Pair(results, points)

    }

}