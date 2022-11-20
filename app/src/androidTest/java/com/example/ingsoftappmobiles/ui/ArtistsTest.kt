package com.example.ingsoftappmobiles.ui
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.ingsoftappmobiles.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ArtistsTest {
    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun artistsTest() {
        Thread.sleep(500)

        val bottomNavigationItemView = onView(allOf(withId(R.id.navigation_artists), withContentDescription("Artistas"),
                    childAtPosition(childAtPosition(withId(R.id.nav_view),0),1),isDisplayed()))
        bottomNavigationItemView.perform(click())

        val textView = onView(allOf(withText("Artistas"),withParent(allOf(withId(androidx.constraintlayout.widget.R.id.action_bar),
                        withParent(withId(androidx.constraintlayout.widget.R.id.action_bar_container)))),isDisplayed()))
        textView.check(matches(withText("Artistas")))
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