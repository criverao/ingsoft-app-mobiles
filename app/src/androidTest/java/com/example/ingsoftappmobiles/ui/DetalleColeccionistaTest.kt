package com.example.ingsoftappmobiles.ui


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.ingsoftappmobiles.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class DetalleColeccionistaTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun detalleColeccionistaTest() {
        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.navigation_collectors), withContentDescription("Coleccionistas"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val textView = onView(
            allOf(
                withText("Listado de Coleccionistas"),
                withParent(
                    allOf(
                        withId(androidx.constraintlayout.widget.R.id.action_bar),
                        withParent(withId(androidx.constraintlayout.widget.R.id.action_bar_container))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Listado de Coleccionistas")))

        val recyclerView = onView(
            allOf(
                withId(R.id.collectorsRecyclerView),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(1, click()))

        val appCompatImageButton = onView(
            allOf(
                withContentDescription("Navigate up"),
                childAtPosition(
                    allOf(
                        withId(androidx.constraintlayout.widget.R.id.action_bar),
                        childAtPosition(
                            withId(androidx.constraintlayout.widget.R.id.action_bar_container),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val recyclerView2 = onView(
            allOf(
                withId(R.id.collectorsRecyclerView),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        )
        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(0, click()))
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
