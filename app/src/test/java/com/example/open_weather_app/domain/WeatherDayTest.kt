package com.example.open_weather_app.domain

import org.junit.Before

import org.junit.Assert.*
import org.hamcrest.CoreMatchers.*
import org.junit.Test
import java.util.*
import java.util.concurrent.TimeUnit


class WeatherDayTest {

    lateinit var SUT: WeatherDay

    @Before
    fun setUp() {
        SUT = WeatherDay(createCalendar(), createFakeList())
    }

    private fun createFakeList(): List<WeatherEntry> {
        val list = mutableListOf<WeatherEntry>()
        list.add(WeatherEntry(createCalendar(), 5f, 5f, 5f, ""))
        list.add(WeatherEntry(createCalendar(), 3f, 3f, 3f, ""))
        list.add(WeatherEntry(createCalendar(), 3f, 3f, 3f, ""))
        return list

    }

    fun createCalendar(): Calendar {
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = Date(TimeUnit.MILLISECONDS.convert(1582826400, TimeUnit.SECONDS))
        return calendar
    }


    @Test
    fun weatherDay_gettingCurrentTemp_listFirstElementIsReturned() {
        val result = SUT.currentTemp
        assertTrue(result.temp == 5f)
    }


    @Test
    fun weatherDay_gettingCurrentTemp_stringWithShortDateReturned() {
        val result = SUT.shortDate
        assertThat(result, `is`("Thu, Feb 27"))

    }

    @Test
    fun weatherDay_gettingCurrentTemp_stringWithLongDateReturned() {
        val result = SUT.longDate
        assertThat(result, `is`("Thursday, February 27"))
    }
}