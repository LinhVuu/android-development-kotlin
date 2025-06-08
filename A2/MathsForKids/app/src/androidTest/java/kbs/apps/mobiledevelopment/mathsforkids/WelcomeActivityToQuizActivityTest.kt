package kbs.apps.mobiledevelopment.mathsforkids


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class WelcomeActivityToQuizActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(WelcomeActivity::class.java)

    @Test
    fun welcomeActivityToQuizActivityTest() {
        val appCompatEditText = onView(
            allOf(
                withId(R.id.edit_text_username_welcome),
                childAtPosition(
                    allOf(
                        withId(R.id.main),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("Anh"), closeSoftKeyboard())

        val materialButton = onView(
            allOf(
                withId(R.id.button_start_welcome), withText("Ready? Go!"),
                childAtPosition(
                    allOf(
                        withId(R.id.main),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        onView(withId(R.id.text_view_app_title_quiz))
            .check(matches(withText(R.string.title_text)))

        val imageView = onView(
            allOf(
                withId(R.id.image_view_quiz),
                withParent(withParent(withId(R.id.main))),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

        onView(withId(R.id.edit_text_answer1_quiz))
            .check(matches(allOf(
                withHint("Enter your answer"),
                withText("")
            )))

        val radioButton = onView(
            allOf(
                withId(R.id.radio_button_answer2_correct_quiz), withText("Correct"),
                withParent(
                    allOf(
                        withId(R.id.radio_group_answer2_quiz),
                        withParent(IsInstanceOf.instanceOf(android.widget.TableRow::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        radioButton.check(matches(isDisplayed()))

        val radioButton2 = onView(
            allOf(
                withId(R.id.radio_button_answer2_incorrect_quiz), withText("Incorrect"),
                withParent(
                    allOf(
                        withId(R.id.radio_group_answer2_quiz),
                        withParent(IsInstanceOf.instanceOf(android.widget.TableRow::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        radioButton2.check(matches(isDisplayed()))

        onView(withId(R.id.edit_text_answer5_quiz))
            .check(matches(allOf(
                withHint("Enter your answer"),
                withText("")
            )))

        val button = onView(
            allOf(
                withId(R.id.button_reset_quiz), withText("Reset"),
                withParent(withParent(withId(R.id.main))),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val button2 = onView(
            allOf(
                withId(R.id.button_result_quiz), withText("Result"),
                withParent(withParent(withId(R.id.main))),
                isDisplayed()
            )
        )
        button2.check(matches(isDisplayed()))

        val button3 = onView(
            allOf(
                withId(R.id.button_result_quiz), withText("Result"),
                withParent(withParent(withId(R.id.main))),
                isDisplayed()
            )
        )
        button3.check(matches(isDisplayed()))
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
