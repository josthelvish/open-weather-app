package com.example.open_weather_app.ui

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.open_weather_app.database.getDatabase
import com.example.open_weather_app.domain.WeatherForecast
import com.example.open_weather_app.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class WeatherOverviewViewModel(application: Application) : ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val database = getDatabase(application)
    private val weatherRepository = WeatherRepository(database)

    init {
        viewModelScope.launch {
            weatherRepository.refreshWeather()
        }
    }

    val weather: LiveData<WeatherForecast> = weatherRepository.entries

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WeatherOverviewViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return WeatherOverviewViewModel(
                    application
                ) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}