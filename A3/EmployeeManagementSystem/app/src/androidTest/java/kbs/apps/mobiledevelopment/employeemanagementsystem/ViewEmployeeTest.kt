package kbs.apps.mobiledevelopment.employeemanagementsystem


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
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
class ViewEmployeeTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun addNewEmployeeTest() {
        val recyclerView = onView(
            allOf(
                withId(R.id.recyclerViewEmployee),
                childAtPosition(
                    withId(R.id.main),
                    1
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val button = onView(
            allOf(
                withId(R.id.buttonBack), withText("Back"),
                withParent(
                    allOf(
                        withId(R.id.main),
                        withParent(withId(android.R.id.content))
                    )
                ),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val button2 = onView(
            allOf(
                withId(R.id.buttonUpdate), withText("Update"),
                withParent(
                    allOf(
                        withId(R.id.main),
                        withParent(withId(android.R.id.content))
                    )
                ),
                isDisplayed()
            )
        )
        button2.check(matches(isDisplayed()))

        val button3 = onView(
            allOf(
                withId(R.id.buttonDelete), withText("Delete"),
                withParent(
                    allOf(
                        withId(R.id.main),
                        withParent(withId(android.R.id.content))
                    )
                ),
                isDisplayed()
            )
        )
        button3.check(matches(isDisplayed()))

        val textView = onView(
            allOf(
                withId(R.id.firstNameLabel), withText("First Name"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.TableLayout::class.java))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("First Name")))

        val textView2 = onView(
            allOf(
                withId(R.id.lastNameLabel), withText("Last Name"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.TableLayout::class.java))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Last Name")))

        val textView3 = onView(
            allOf(
                withId(R.id.addressLabel), withText("Address"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.TableLayout::class.java))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Address")))

        val textView4 = onView(
            allOf(
                withId(R.id.phoneNumberLabel), withText("Phone"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.TableLayout::class.java))),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("Phone")))

        val textView5 = onView(
            allOf(
                withId(R.id.emailLabel), withText("Email"),
                withParent(withParent(withId(R.id.workInfoTable))),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("Email")))

        val textView6 = onView(
            allOf(
                withId(R.id.positionLabel), withText("Position"),
                withParent(withParent(withId(R.id.workInfoTable))),
                isDisplayed()
            )
        )
        textView6.check(matches(withText("Position")))

        val textView7 = onView(
            allOf(
                withId(R.id.latestSalaryLabel), withText("Latest Salary"),
                withParent(withParent(withId(R.id.workInfoTable))),
                isDisplayed()
            )
        )
        textView7.check(matches(withText("Latest Salary")))

        val textView8 = onView(
            allOf(
                withId(R.id.latestSalaryLabel), withText("Latest Salary"),
                withParent(withParent(withId(R.id.workInfoTable))),
                isDisplayed()
            )
        )
        textView8.check(matches(withText("Latest Salary")))
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
