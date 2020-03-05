package com.example.open_weather_app

import android.os.SystemClock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MainActivityTest {

    @get:Rule
    var activityTest = ActivityTestRule(MainActivity::class.java)

    private val loadDataDelay = 500L
    @Before
    fun setUp() {
    }

    @Test
    fun mainActivity_onStartApplication_viewIsDisplayed() {
        SystemClock.sleep(loadDataDelay)
        onView(withId(R.id.rv_weather)).check(matches(isDisplayed()))
    }

    @Test
    fun mainActivity_onStartApplication_recyclerViewContainsElements() {
        SystemClock.sleep(loadDataDelay)
        onView(withId(R.id.rv_weather)).check(RecyclerViewItemCountAssertion(4))
    }
}
