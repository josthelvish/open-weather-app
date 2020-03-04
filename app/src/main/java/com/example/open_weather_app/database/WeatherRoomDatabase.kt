package com.example.open_weather_app.database

import android.content.Context

import androidx.room.*


@Database(entities = [DatabaseWeather::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract val weatherEntryDao: WeatherEntryDao
}

private lateinit var INSTANCE: WeatherDatabase

fun getDatabase(context: Context): WeatherDatabase {
    synchronized(WeatherDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE =
                Room.databaseBuilder(context, WeatherDatabase::class.java, "weather_entries")
                    .build()
        }
    }
    return INSTANCE
}