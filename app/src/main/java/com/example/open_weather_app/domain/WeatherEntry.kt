package com.example.open_weather_app.domain

import java.util.*

data class WeatherEntry(
    val calendar: Calendar = Calendar.getInstance(),
    val temp: Float = 0f,
    val tempMin: Float = 0f,
    val tempMax: Float = 0f,
    val iconUrl: String = ""
)