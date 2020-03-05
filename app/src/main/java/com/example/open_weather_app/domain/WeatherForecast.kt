package com.example.open_weather_app.domain

data class WeatherForecast(
    val currentDay: WeatherDay,
    val longForecast: List<WeatherDay>
)