package kbs.apps.mobiledevelopment.week8_unittest

class InputValidator {

    fun validateInput(editText: String): String {
        val displayText: String
        if (editText != "") {
            displayText = editText
        } else {
            displayText = "invalid input!"
        }
        return displayText
    }
}