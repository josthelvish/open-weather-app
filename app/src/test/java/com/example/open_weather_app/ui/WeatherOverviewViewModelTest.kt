package com.example.open_weather_app.ui

import android.app.Application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class WeatherOverviewViewModelTest {

    lateinit var SUT: WeatherOverviewViewModel
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Mock
    lateinit var mockApplication: Application

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.resetMain()
        SUT = WeatherOverviewViewModel(mockApplication)
    }



    @Test
    fun test() {
        Dispatchers.setMain(Dispatchers.Main)
        val result = SUT.weather

        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }



    }


}

