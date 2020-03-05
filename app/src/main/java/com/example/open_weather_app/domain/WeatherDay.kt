package com.example.open_weather_app.domain

import com.example.open_weather_app.utils.dateAsString
import java.util.*

data class WeatherDay(
    val date: Calendar,
    val weather: List<WeatherEntry>
) {

    val currentTemp: WeatherEntry
        get() = weather.first()

    val shortDate: String
        get() = date.dateAsString(Calendar.SHORT)

    val longDate: String
        get() = date.dateAsString(Calendar.LONG)

}