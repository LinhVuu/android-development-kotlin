package kbs.apps.mobiledevelopment.employeemanagementsystem


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
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
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AddNewEmployeeTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun addNewEmployeeTest() {
        val floatingActionButton = onView(
            allOf(
                withId(R.id.floatingButtonAddEmployee), withContentDescription("Add New"),
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
        floatingActionButton.perform(click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.editTextFirstName),
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
        appCompatEditText.perform(replaceText("Linh"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.editTextLastName),
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
        appCompatEditText2.perform(replaceText("Vuu"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.editTextAddress),
                childAtPosition(
                    allOf(
                        withId(R.id.main),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatEditText3.perform(replaceText("15 Grenfell "), closeSoftKeyboard())

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.editTextCity),
                childAtPosition(
                    allOf(
                        withId(R.id.main),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        appCompatEditText4.perform(replaceText("Adelaide"), closeSoftKeyboard())

        val appCompatEditText5 = onView(
            allOf(
                withId(R.id.editTextPhone),
                childAtPosition(
                    allOf(
                        withId(R.id.main),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        appCompatEditText5.perform(replaceText("+619876543210"), closeSoftKeyboard())

        val appCompatEditText6 = onView(
            allOf(
                withId(R.id.editTextEmail),
                childAtPosition(
                    allOf(
                        withId(R.id.layoutEmail),
                        childAtPosition(
                            withId(R.id.main),
                            8
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText6.perform(replaceText("linh@gmail.com"), closeSoftKeyboard())

        val appCompatEditText7 = onView(
            allOf(
                withId(R.id.editTextPosition),
                childAtPosition(
                    allOf(
                        withId(R.id.layoutPosition),
                        childAtPosition(
                            withId(R.id.main),
                            9
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText7.perform(replaceText("DA"), closeSoftKeyboard())

        val appCompatEditText8 = onView(
            allOf(
                withId(R.id.editTextSalary),
                childAtPosition(
                    allOf(
                        withId(R.id.layoutSalary),
                        childAtPosition(
                            withId(R.id.main),
                            10
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText8.perform(replaceText("90"), closeSoftKeyboard())

        val appCompatEditText9 = onView(
            allOf(
                withId(R.id.editTextDateHired),
                childAtPosition(
                    allOf(
                        withId(R.id.layoutDateHired),
                        childAtPosition(
                            withId(R.id.main),
                            13
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText9.perform(replaceText("10/09/2015"), closeSoftKeyboard())

        val appCompatEditText10 = onView(
            allOf(
                withId(R.id.editTextStartDate),
                childAtPosition(
                    allOf(
                        withId(R.id.layoutStartDate),
                        childAtPosition(
                            withId(R.id.main),
                            14
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText10.perform(replaceText("10/11/2015"), closeSoftKeyboard())

        val materialButton = onView(
            allOf(
                withId(R.id.buttonSave), withText("Save"),
                childAtPosition(
                    allOf(
                        withId(R.id.main),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    16
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.titleTextView), withText("EMPLOYEE LIST"),
                withParent(
                    allOf(
                        withId(R.id.main),
                        withParent(withId(android.R.id.content))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("EMPLOYEE LIST")))
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
