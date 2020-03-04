package com.example.open_weather_app.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WeatherEntryDao {
    @Query("SELECT * FROM databaseWeather")
    fun getWeatherEntries(): LiveData<List<DatabaseWeather>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg entries: DatabaseWeather)

    @Query("DELETE FROM databaseWeather")
    fun clearEntries()
}