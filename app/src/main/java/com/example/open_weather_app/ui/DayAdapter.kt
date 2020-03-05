package com.example.open_weather_app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.open_weather_app.R
import com.example.open_weather_app.databinding.DayForecastItemBinding
import com.example.open_weather_app.domain.WeatherDay

class DayAdapter : RecyclerView.Adapter<DayViewHolder>() {

    var days: List<WeatherDay> = emptyList()
        set(value) {
            field = value

            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val withDataBinding: DayForecastItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            DayViewHolder.LAYOUT,
            parent,
            false
        )
        return DayViewHolder(withDataBinding)
    }

    override fun getItemCount() = days.size

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {

        holder.viewDataBinding.also {
            it.textView.text = days[position].longDate
            it.rvEntries.adapter = EntryAdapter(days[position].weather)
        }
    }
}

class DayViewHolder(val viewDataBinding: DayForecastItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.day_forecast_item
    }
}
