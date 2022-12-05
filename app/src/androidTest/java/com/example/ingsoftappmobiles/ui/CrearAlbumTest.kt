/*
package com.example.ingsoftappmobiles.ui



import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.ingsoftappmobiles.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class CrearAlbumTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun crearAlbumTest() {
        val actionMenuItemView = onView(
            allOf(
                withId(R.id.create_albums), withContentDescription("Crear"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.my_toolbar),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        actionMenuItemView.perform(click())

        val textInputEditText = onView(
            allOf(
                withId(R.id.txt_album_name),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("com.google.android.material.textfield.TextInputLayout")),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText.perform(scrollTo(), click())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.txt_album_name),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("com.google.android.material.textfield.TextInputLayout")),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText2.perform(scrollTo(), replaceText("Los Magnificos"), closeSoftKeyboard())

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.txt_album_image),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("com.google.android.material.textfield.TextInputLayout")),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText3.perform(
            scrollTo(),
            replaceText("https://www.editando.cl/wp-content/uploads/2014/11/Kickboxer_simpsonized.png"),
            closeSoftKeyboard()
        )

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.txt_album_desc),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("com.google.android.material.textfield.TextInputLayout")),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText4.perform(scrollTo(), replaceText("Nuevo album 2022"), closeSoftKeyboard())

        val materialAutoCompleteTextView = onView(
            allOf(
                withId(R.id.txt_album_genre), withText("Seleccione un Género"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("com.google.android.material.textfield.TextInputLayout")),
                        0
                    ),
                    0
                )
            )
        )
        materialAutoCompleteTextView.perform(scrollTo(), click())

        val materialAutoCompleteTextView2 = onView(
            allOf(
                withId(R.id.txt_album_disc), withText("Casa Discográfica"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("com.google.android.material.textfield.TextInputLayout")),
                        0
                    ),
                    0
                )
            )
        )
        materialAutoCompleteTextView2.perform(scrollTo(), click())

        val materialButton = onView(
            allOf(
                withId(R.id.album_create_button), withText("Crear"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        6
                    ),
                    1
                )
            )
        )
        materialButton.perform(scrollTo(), click())
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
*/