package kbs.apps.mobiledevelopment.week8_unittest

import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MainActivityTest {

    @Test
    fun whenInputIsvalid(){
        val inputText = ""
        val result = MainActivity().validateInput(inputText)

    }
}