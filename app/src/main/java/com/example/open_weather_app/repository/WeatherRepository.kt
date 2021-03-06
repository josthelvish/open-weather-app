package com.example.open_weather_app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.open_weather_app.database.WeatherDatabase
import com.example.open_weather_app.database.asDomainModel
import com.example.open_weather_app.domain.WeatherForecast
import com.example.open_weather_app.network.Network
import com.example.open_weather_app.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(private val database: WeatherDatabase) {

    val entries: LiveData<WeatherForecast> =
        Transformations.map(database.weatherEntryDao.getWeatherEntries()) {
            it.asDomainModel()
        }

    suspend fun refreshWeather() {
        withContext(Dispatchers.IO) {
            val entries = Network.entries.getWeatherAPIInfo().await()
            database.weatherEntryDao.updateEntries(*entries.asDatabaseModel())
        }
    }
}