package com.example.open_weather_app.utils

import java.util.*

const val API_CITY_ID: String = "6458924"
const val API_UNITS: String = "metric"

fun Calendar.dateAsString(style: Int): String {
    val dayOfWeek = this.getDisplayName(Calendar.DAY_OF_WEEK, style, Locale.getDefault())
    val month = this.getDisplayName(Calendar.MONTH, style, Locale.getDefault())
    val day = this.get(Calendar.DAY_OF_MONTH)
    return "$dayOfWeek, $month $day"
}