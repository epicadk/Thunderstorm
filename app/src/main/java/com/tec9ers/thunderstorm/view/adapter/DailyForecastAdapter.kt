package com.tec9ers.thunderstorm.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tec9ers.thunderstorm.R
import com.tec9ers.thunderstorm.model.onecallapi.Daily
import com.tec9ers.thunderstorm.utils.FormatUtils
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.android.synthetic.main.item_daily_forecast.view.*
import javax.inject.Inject

@ActivityContext
class DailyForecastAdapter @Inject constructor(
    @ActivityContext val context: Context,
    private val format: FormatUtils
) :
    RecyclerView.Adapter<DailyForecastAdapter.DailyForecastViewHolder>() {

    private var data: List<Daily>? = null

    fun setData(data: List<Daily>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        return DailyForecastViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_daily_forecast, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        data?.get(position)?.let { holder.populate(it, format) }
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    class DailyForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun populate(daily: Daily, format: FormatUtils) {
            with(
                itemView,
                {
                    min_temp_tv.text = daily.temp.min.toString()
                    min_temp_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        null, null,
                        ContextCompat.getDrawable(context, R.drawable.ic_arrow_downward_24px), null
                    )
                    max_temp_tv.text = daily.temp.max.toString()
                    max_temp_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        null, null,
                        ContextCompat.getDrawable(context, R.drawable.ic_arrow_upward_24px), null
                    )
                    desc_tv.text = format.capitalizeDescription(daily.weather[0].description)
                    day_tv.text = format.formatDay(daily.dt)
                }
            )
        }
    }
}
