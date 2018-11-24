package com.muslimlife.tf.footballappkotlang

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matchers.allOf


fun clickOnTab(tabText: String) {
    val matcher = allOf(
        withText(tabText),
        isDescendantOfA(withId(R.id.tabs_main))
    )
    onView(matcher).perform(click())
}