package com.example.open_weather_app.domain

import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import java.util.*
import java.util.concurrent.TimeUnit

class WeatherDayTest {

    lateinit var SUT: WeatherDay
    lateinit var time:Date
    lateinit var calendar: Calendar
    @Before
    fun setUp() {

        time = Date(TimeUnit.MILLISECONDS.convert(1582826400, TimeUnit.SECONDS))

        calendar = Calendar.getInstance()
        calendar.time = time

        SUT = WeatherDay(calendar, mutableListOf())
    }

    @Test
    fun name() {
        println(time.day)
        println(calendar.get(Calendar.DAY_OF_WEEK))
    }
}