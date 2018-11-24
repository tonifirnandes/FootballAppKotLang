package com.muslimlife.tf.footballappkotlang

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.muslimlife.tf.footballappkotlang.R.id.*
import com.muslimlife.tf.footballappkotlang.features.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        //check last match view
        onView(withId(pb_home)).check(matches(isDisplayed()))
        onView(withId(bnv_main)).check(matches(isDisplayed()))
        delay(5000)
        onView(withIndex(withId(rv_match_schedule), 0))
            .check(matches(isDisplayed()))
        onView(
            withIndex(
                withId(rv_match_schedule),
                0
            )
        ).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withIndex(withId(rv_match_schedule), 0)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click())
        )
        delay(2000)
        onView(withId(iv_home_team_logo)).check(matches(isDisplayed()))
        onView(withId(iv_away_team_logo)).check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        pressBack()

        //check next match view
        clickOnTab("NEXT MATCH")
        onView(withIndex(withId(rv_match_schedule), 1))
            .check(matches(isDisplayed()))
        onView(
            withIndex(
                withId(rv_match_schedule),
                1
            )
        ).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withIndex(withId(rv_match_schedule), 1)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click())
        )
        delay(2000)
        onView(withId(iv_home_team_logo)).check(matches(isDisplayed()))
        onView(withId(iv_away_team_logo)).check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        pressBack()

        //check favorite match view
        onView(withId(R.id.bnv_main)).check(matches(isDisplayed()))
        onView(withId(R.id.nav_favorite)).perform(click())
        onView(
            withIndex(
                withId(rv_match_schedule),
                0
            )
        ).check(matches(isDisplayed()))
        onView(withIndex(withId(rv_match_schedule), 0)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        onView(withId(iv_home_team_logo)).check(matches(isDisplayed()))
        onView(withId(iv_away_team_logo)).check(matches(isDisplayed()))
        pressBack()

    }

    //Todo : remove delay part
    private fun delay(duration: Long) {
        try {
            Thread.sleep(duration)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}