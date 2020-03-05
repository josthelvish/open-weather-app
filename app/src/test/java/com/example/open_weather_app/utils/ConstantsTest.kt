package com.example.open_weather_app.utils

import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import java.util.*

class ConstantsTest {

    lateinit var calendar: Calendar

    @Before
    fun setUp() {
        calendar = Calendar.getInstance()
        calendar.time = Date(1583451961272)
    }

    @Test
    fun constants_calendarDateShortString_dateShortFormatReturned() {
        assertEquals(calendar.dateAsString(Calendar.SHORT), "Thu, Mar 5")
    }

    @Test
    fun constants_calendarDateLongString_dateLongFormatReturned() {
        assertEquals(calendar.dateAsString(Calendar.LONG), "Thursday, March 5")
    }

    @Test
    fun constants_cityId_cityIDValueIsNotChanged() {
        assertEquals(API_CITY_ID, "6458924")
    }
}