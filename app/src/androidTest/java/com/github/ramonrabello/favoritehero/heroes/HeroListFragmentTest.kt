package com.github.ramonrabello.favoritehero.heroes

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.MediumTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.widget.AutoCompleteTextView
import com.github.ramonrabello.favoritehero.MainActivity
import com.github.ramonrabello.favoritehero.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations


/**
 * Instrumentation tests for [HeroListFragment] class.
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
class HeroListFragmentTest {

    @get:Rule
    var activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun whenListLoaded_scrollToPosition_clickOnItem() {
        Thread.sleep(3000)
        onView(withId(R.id.recycler_view)).perform(scrollToPosition<CharacterViewHolder>(5))
    }

    @Test
    fun typeOnSearchMenuItem_typeSearchQuery() {
        onView(withId(R.id.action_search)).perform(click())
        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform(typeText("Spider-Man"))
    }
}