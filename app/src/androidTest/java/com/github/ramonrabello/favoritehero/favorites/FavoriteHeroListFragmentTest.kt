package com.github.ramonrabello.favoritehero.favorites

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.MediumTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.github.ramonrabello.favoritehero.MainActivity
import com.github.ramonrabello.favoritehero.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumentation tests for [FavoriteHeroListFragmentTest] class.
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
class FavoriteHeroListFragmentTest {

    @get:Rule
    var activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun whenActivityStarted_clickOnFavoritesNavigationItem_checkEmptyState() {
        onView(withId(R.id.navigation_favorites)).perform(click())
        onView(withId(R.id.empty_view_image)).check(matches(isDisplayed()))
        onView(withId(R.id.empty_view_text)).check(matches(isDisplayed()))
    }
}