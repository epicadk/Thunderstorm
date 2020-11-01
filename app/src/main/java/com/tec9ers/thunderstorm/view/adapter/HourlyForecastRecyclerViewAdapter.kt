package com.tec9ers.thunderstorm.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tec9ers.thunderstorm.R
import com.tec9ers.thunderstorm.model.onecallapi.Hourly
import com.tec9ers.thunderstorm.utils.FormatUtils
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.android.synthetic.main.item_hourly_forecast.view.*
import javax.inject.Inject

@ActivityContext
class HourlyForecastRecyclerViewAdapter @Inject constructor(@ActivityContext val context: Context, private val format: FormatUtils) :
    RecyclerView.Adapter<HourlyForecastRecyclerViewAdapter.ForecastViewHolder>() {

    private var data: List<Hourly>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_hourly_forecast, parent, false)
        return ForecastViewHolder(view)
    }
    fun setData(data: List<Hourly>) {
        this.data = data
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return if (data == null) 0 else data!!.size
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        data?.get(position)?.let { holder.setData(it, format) }
    }

    class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(hourly: Hourly, format: FormatUtils) {
            with(itemView) {
                tv_forecast_hourly_desc.text = format.capitalizeDescription(hourly.weather[0].description)
                tv_forecast_hourly_temp.text = format.formatTemp(hourly.temp)
                tv_forecast_hourly_time.text = format.formatTime(hourly.dt)
            }
        }
    }
}
