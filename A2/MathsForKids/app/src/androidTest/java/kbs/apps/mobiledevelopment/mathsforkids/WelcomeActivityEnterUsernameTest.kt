package kbs.apps.mobiledevelopment.mathsforkids


import android.view.View
import android.view.ViewGroup
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
class WelcomeActivityEnterUsernameTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(WelcomeActivity::class.java)

    @Test
    fun welcomeActivityEnterUsernameTest() {
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
        appCompatEditText.perform(replaceText("Linh Vuu"), closeSoftKeyboard())

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
        appCompatEditText2.perform(scrollTo(), replaceText("90"), closeSoftKeyboard())

        val materialRadioButton = onView(
            allOf(
                withId(R.id.radio_button_answer2_incorrect_quiz), withText("Incorrect"),
                childAtPosition(
                    allOf(
                        withId(R.id.radio_group_answer2_quiz),
                        childAtPosition(
                            withClassName(`is`("android.widget.TableRow")),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialRadioButton.perform(click())

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
        appCompatEditText3.perform(scrollTo(), replaceText("8"), closeSoftKeyboard())

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

        onView(allOf(
            withId(R.id.text_view_result),
            withParent(
                allOf(
                    withId(R.id.main),
                    withParent(withId(android.R.id.content))
                )
            ),
            isDisplayed()
        ))
            .check(matches(
                withText(containsString("Hi Linh Vuu, here is your result"))
            ))
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
