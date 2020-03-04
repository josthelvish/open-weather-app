package com.example.open_weather_app.domain

import java.util.*

data class WeatherEntry(
    val calendar: Calendar,
    val temp: Float,
    val tempMin: Float,
    val tempMax: Float,
    val iconUrl: String
)