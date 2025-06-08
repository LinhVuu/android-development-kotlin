package kbs.apps.mobiledevelopment.mathsforkids


import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class QuizActivityTestAllIncorrect {

    @get:Rule
    // Start QuizActivity with the defined seed to generate the same randomised questions.
    val mActivityScenarioRule = ActivityScenarioRule<QuizActivity>(
        Intent(
            ApplicationProvider.getApplicationContext<Context>(),
            QuizActivity::class.java
        ).apply {
            putExtra("USERNAME_STRING", "Lin")
            putExtra("RANDOM_SEED", 123456789)
        }
    )

    @Test
    fun quizActivityTestAllIncorrect() {

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.edit_text_answer1_quiz),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.table_layout_questions_quiz),
                        1
                    ),
                    1
                )
            )
        )
        appCompatEditText2.perform(scrollTo(), replaceText("1"), closeSoftKeyboard())

        val materialRadioButton = onView(
            allOf(
                withId(R.id.radio_button_answer2_correct_quiz), withText("Correct"),
                childAtPosition(
                    allOf(
                        withId(R.id.radio_group_answer2_quiz),
                        childAtPosition(
                            withClassName(`is`("android.widget.TableRow")),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialRadioButton.perform(click())

        val materialCheckBox = onView(
            allOf(
                withId(R.id.check_box_answer4_option2_quiz), withText("8"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.TableRow")),
                        1
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialCheckBox.perform(click())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.edit_text_answer5_quiz),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.table_layout_questions_quiz),
                        13
                    ),
                    1
                )
            )
        )
        appCompatEditText3.perform(scrollTo(), replaceText("3"), closeSoftKeyboard())

        val materialButton2 = onView(
            allOf(
                withId(R.id.button_result_quiz), withText("Result"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main),
                        0
                    ),
                    1
                )
            )
        )
        materialButton2.perform(scrollTo(), click())

        onView(withId(R.id.text_view_result))
            .check(matches(withText(containsString("0/100"))))

        onView(withId(R.id.image_view_after_quiz_result))
            .check(matches(isDisplayed()))

    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
