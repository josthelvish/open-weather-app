package com.example.open_weather_app.network

import com.example.open_weather_app.BuildConfig
import com.example.open_weather_app.utils.API_CITY_ID
import com.example.open_weather_app.utils.API_UNITS
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {
    @GET("/data/2.5/forecast")
    fun getWeatherAPIInfo(
        @Query("id") cityId: String = API_CITY_ID,
        @Query("units") extras: String = API_UNITS,
        @Query("APPID") apiKey: String = BuildConfig.WEATHER_API_KEY
    ): Deferred<WeatherNetworkContainer>
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

object Network {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val entries = retrofit.create(OpenWeatherService::class.java)
}

