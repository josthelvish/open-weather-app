package com.example.open_weather_app.network

import com.example.open_weather_app.database.DatabaseWeather
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherNetworkContainer(
    val list: List<WeatherNetworkEntry>
)

@JsonClass(generateAdapter = true)
data class WeatherNetworkEntry(
    val dt: Long,
    val main: NetworkContainerMain,
    val weather: List<NetworkContainerWeather>
)

@JsonClass(generateAdapter = true)
data class NetworkContainerMain(
    val temp: Float,
    val temp_min: Float,
    val temp_max: Float
)

@JsonClass(generateAdapter = true)
data class NetworkContainerWeather(
    val icon: String
)

fun WeatherNetworkContainer.asDatabaseModel(): Array<DatabaseWeather> {
    return list.map {
        DatabaseWeather(
            dt = it.dt,
            temp = it.main.temp,
            temp_min = it.main.temp_min,
            temp_max = it.main.temp_max,
            icon = it.weather[0].icon
        )
    }.toTypedArray()
}