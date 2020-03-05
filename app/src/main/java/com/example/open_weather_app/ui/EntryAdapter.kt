package com.example.open_weather_app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.open_weather_app.R
import com.example.open_weather_app.utils.ICON_TYPE_NORMAL
import com.example.open_weather_app.databinding.WeatherEntryItemBinding
import com.example.open_weather_app.domain.WeatherEntry
import java.util.*

class EntryAdapter(entries: List<WeatherEntry>) : RecyclerView.Adapter<EntryViewHolder>() {
    var entries: List<WeatherEntry> = mutableListOf()
        set(value) {
            field = value

            notifyDataSetChanged()
        }

    init {
        this.entries = entries
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val withDataBinding: WeatherEntryItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            EntryViewHolder.LAYOUT,
            parent,
            false
        )
        return EntryViewHolder(withDataBinding)
    }

    override fun getItemCount() = entries.size

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {

        holder.viewDataBinding.also {
            val context = holder.itemView.context

            it.tvEntryTemp.text = context.getString(
                R.string.append_celsius,
                entries[position].temp
            )

            it.tvEntryHour.text = context.getString(
                R.string.append_hours,
                entries[position].calendar.get(Calendar.HOUR_OF_DAY).toString()
            )

            //inflating the icon
            Glide.with(context).load(entries[position].iconUrl + ICON_TYPE_NORMAL)
                .into(it.ivEntry)
        }
    }
}

class EntryViewHolder(val viewDataBinding: WeatherEntryItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.weather_entry_item
    }
}