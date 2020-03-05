package com.example.open_weather_app

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert
import java.util.concurrent.atomic.AtomicIntegerArray
import org.hamcrest.core.AllOf.allOf

class RecyclerViewItemCountAssertion() : ViewAssertion {
    lateinit var matcher: Matcher<Int>

    constructor(expectedCount: Int) : this() {
        matcher = Matchers.`is`(expectedCount)
    }

    constructor(matcher: Matcher<Int>) : this() {
        this.matcher = matcher
    }

    override fun check(
        view: View,
        noViewFoundException: NoMatchingViewException?
    ) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }
        val recyclerView: RecyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        if (adapter != null) {
            Assert.assertThat(adapter.itemCount, matcher)
        }
    }

    companion object {
        fun getCountFromRecyclerView(@IdRes RecyclerViewId: Int): Int {
            val COUNT =
                AtomicIntegerArray(intArrayOf(0))
            val matcher: Matcher<*> =
                object : TypeSafeMatcher<View>() {
                    override fun matchesSafely(item: View): Boolean {
                        COUNT[0] = (item as RecyclerView).adapter?.itemCount!!
                        return true
                    }

                    override fun describeTo(description: Description) {}
                }
            onView(allOf(withId(RecyclerViewId), isDisplayed())).check(matches(matcher as Matcher<in View>?))
            val result = COUNT[0]
            COUNT[0] = 0
            return result
        }
    }
}