package com.example.open_weather_app.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.open_weather_app.R
import com.example.open_weather_app.databinding.WeatherOverviewBinding
import com.example.open_weather_app.domain.WeatherEntry
import com.example.open_weather_app.domain.WeatherForecast
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
                    viewModelAdapter?.days = forecast.longForecast
                    drawLineChart(forecast.currentDay.weather)
                }
            })
    }

    private fun drawLineChart(weather: List<WeatherEntry>) {
        lineChartView = view?.findViewById(R.id.lchart_plot)!!
        val revenueComp1 = arrayListOf(10000f, 20000f, 30000f, 60000f)
        val revenueComp2 = arrayListOf(12000f, 23000f, 35000f, 48000f)
        val entries1 = revenueComp1.mapIndexed { index, arrayList ->
            Entry(index.toFloat(), arrayList)
        }

        val entries2 = revenueComp2.mapIndexed { index, arrayList ->
            Entry(index.toFloat(), arrayList)
        }

        val lineDataSet1 = LineDataSet(entries1, "")
        lineDataSet1.color = Color.RED
        //lineDataSet1.setDrawValues(true)
        lineDataSet1.setAxisDependency(YAxis.AxisDependency.LEFT)

//        val lineDataSet2 = LineDataSet(entries2, "Company 2")
//        lineDataSet2.color = Color.BLUE
//        lineDataSet1.setDrawValues(false)
//        lineDataSet2.setAxisDependency(YAxis.AxisDependency.LEFT)
//
//        val lineDataSets = listOf(lineDataSet1, lineDataSet2)
        val lineDataSets = listOf(lineDataSet1)
        lineChartView.axisLeft.mAxisMaximum = 1f
        lineChartView.axisLeft.mAxisMinimum = -1f
        lineChartView.axisLeft.mAxisRange = 2f

        val lineData = LineData(lineDataSets)
        lineChartView.axisLeft.setDrawAxisLine(false)
        lineChartView.axisLeft.setDrawTopYLabelEntry(false)
        lineChartView.axisRight.setDrawGridLines(false)
        lineChartView.xAxis.setDrawGridLines(false)
        lineChartView.axisLeft.setDrawGridLines(false)


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