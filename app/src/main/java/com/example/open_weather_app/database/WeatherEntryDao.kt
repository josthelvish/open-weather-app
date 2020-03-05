package com.example.open_weather_app.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WeatherEntryDao {
    @Query("SELECT * FROM databaseWeather")
    fun getWeatherEntries(): LiveData<List<DatabaseWeather>>

    @Transaction
    fun updateEntries(vararg entries: DatabaseWeather) {
        clearEntries()
        insertAll(entries)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entries: Array<out DatabaseWeather>)

    @Query("DELETE FROM databaseWeather")
    fun clearEntries()
}