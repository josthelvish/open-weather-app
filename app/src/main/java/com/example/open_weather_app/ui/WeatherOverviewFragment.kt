package com.example.open_weather_app.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.open_weather_app.R
import com.example.open_weather_app.databinding.WeatherOverviewBinding
import com.example.open_weather_app.domain.WeatherEntry
import com.example.open_weather_app.domain.WeatherForecast
import com.example.open_weather_app.utils.ICON_TYPE_LARGE
import com.example.open_weather_app.utils.ICON_TYPE_NORMAL
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class WeatherOverviewFragment : Fragment() {

    private val viewModel: WeatherOverviewViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "ViewModel can't be accessed after onActivityCreated()"
        }
        ViewModelProviders.of(
            this,
            WeatherOverviewViewModel.Factory(
                activity.application
            )
        )
            .get(WeatherOverviewViewModel::class.java)
    }

    lateinit var lineChartView: LineChart

    private var viewModelAdapter: DayAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.weather.observe(
            viewLifecycleOwner,
            Observer<WeatherForecast> { forecast ->
                forecast?.apply {
                    if(forecast.longForecast.isNotEmpty()){
                        viewModelAdapter?.days = forecast.longForecast
                        drawLineChart(forecast.currentDay.weather)
                        updateCurrentIcon(forecast.currentDay.currentTemp)
                    }
                }
            })
    }

    private fun updateCurrentIcon(currentTemp: WeatherEntry) {
        val view = view?.findViewById<ImageView>(R.id.iv_weather_icon)
        if (view != null) {

            Glide.with(view.context).load(currentTemp.iconUrl + ICON_TYPE_LARGE)
                .into(view)
        }
    }

    private fun drawLineChart(weather: List<WeatherEntry>) {
        lineChartView = view?.findViewById(R.id.lchart_plot)!!

        val temperature = weather.map { it.temp }.toList()
        val entries1 = temperature.mapIndexed { index, arrayList ->
            Entry(index.toFloat(), arrayList)
        }

        val lineDataSet1 = LineDataSet(entries1, "Temperature")
        lineDataSet1.color = Color.RED
        lineDataSet1.setAxisDependency(YAxis.AxisDependency.LEFT)

        val lineDataSets = listOf(lineDataSet1)
        val lineData = LineData(lineDataSets)

        lineChartView.axisLeft.setDrawLabels(false);
        lineChartView.axisRight.setDrawLabels(false);
        lineChartView.xAxis.setDrawLabels(false);
        lineChartView.data = lineData
        lineChartView.invalidate()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: WeatherOverviewBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.weather_overview,
            container,
            false
        )

        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        viewModelAdapter = DayAdapter()

        binding.root.findViewById<RecyclerView>(R.id.rv_weather).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }

        return binding.root
    }
}