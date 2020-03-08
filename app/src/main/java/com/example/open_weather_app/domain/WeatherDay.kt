package com.example.open_weather_app.domain

import com.example.open_weather_app.utils.dateAsString
import java.util.*

data class WeatherDay(
    val date: Calendar = java.util.Calendar.getInstance(),
    val weather: List<WeatherEntry> = mutableListOf()
) {

    val currentTemp: WeatherEntry
        get() = if (weather.isEmpty()) {
            WeatherEntry()
        } else {
            weather.first()
        }

    val shortDate: String
        get() = date.dateAsString(Calendar.SHORT)

    val longDate: String
        get() = date.dateAsString(Calendar.LONG)

}