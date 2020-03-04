package com.example.open_weather_app.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.open_weather_app.BuildConfig
import com.example.open_weather_app.domain.WeatherDay
import com.example.open_weather_app.domain.WeatherEntry
import com.example.open_weather_app.domain.WeatherForecast
import java.util.*
import java.util.concurrent.TimeUnit

@Entity
data class DatabaseWeather constructor(
    @PrimaryKey
    val dt: Long,
    val temp: Float,
    val temp_min: Float,
    val temp_max: Float,
    val icon: String
)

fun List<DatabaseWeather>.asDomainModel(): WeatherForecast {
    return map {
        val calendar = GregorianCalendar()
        calendar.time = Date(
            TimeUnit.MILLISECONDS.convert(
                it.dt,
                TimeUnit.SECONDS
            )
        )
        WeatherEntry(
            calendar = calendar,
            temp = it.temp,
            tempMin = it.temp_min,
            tempMax = it.temp_max,
            iconUrl = BuildConfig.ICONS_BASE_URL + it.icon + ".png"
        )
    }.toWeatherDay()
}

private fun List<WeatherEntry>.toWeatherDay(): WeatherForecast {
    val listByDay = mutableMapOf<Int, MutableList<WeatherEntry>>()

    forEach {
        val day = it.calendar.get(Calendar.DAY_OF_MONTH)
        if (!listByDay.containsKey(day)) {
            val list = mutableListOf<WeatherEntry>()
            list.add(it)
            listByDay[day] = list
        } else {
            listByDay[day]?.add(it)
        }
    }

    val list = mutableListOf<WeatherDay>()
    listByDay.forEach { (i, mutableList) ->
        list.add(WeatherDay(mutableList[0].calendar, mutableList))
    }

    //todo review internet signal
    return WeatherForecast(list.removeAt(0), list)
}
