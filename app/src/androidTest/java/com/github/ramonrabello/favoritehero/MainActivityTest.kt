package com.github.ramonrabello.favoritehero

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom
import android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.SmallTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.Toolbar
import com.github.ramonrabello.favoritehero.util.matchers.ToolbarMatchers.withToolbarTitle
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumentation tests for [MainActivity] class.
 */
@RunWith(AndroidJUnit4::class)
@SmallTest
class MainActivityTest {

    @get:Rule
    var activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)
    private lateinit var context: Context

    @Before
    fun beforeTest() {
        context = InstrumentationRegistry.getContext()
    }

    @Test
    fun whenActivityStarted_checkIfAllViewsAreVisible() {
        onView(withId(R.id.main_toolbar)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.container)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.navigation)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun whenActivityStarted_clickOnHeroesNavigationItem_shouldCheckIfToolbarTitleChanged() {
        onView(withId(R.id.navigation_heroes)).perform(click())
        onView(isAssignableFrom(Toolbar::class.java)).check(matches(withToolbarTitle(R.string.title_heroes)))
    }

    @Test
    fun whenActivityStarted_clickOnFavoritesNavigationItem_shouldCheckIfToolbarTitleChanged() {
        onView(withId(R.id.navigation_favorites)).perform(click())
        onView(isAssignableFrom(Toolbar::class.java)).check(matches(withToolbarTitle(R.string.title_favorites)))
    }
}