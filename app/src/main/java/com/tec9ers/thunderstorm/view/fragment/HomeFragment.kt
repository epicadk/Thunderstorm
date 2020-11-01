package com.tec9ers.thunderstorm.view.fragment

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tec9ers.thunderstorm.R
import com.tec9ers.thunderstorm.model.onecallapi.OneCallAPIResponse
import com.tec9ers.thunderstorm.utils.FormatUtils
import com.tec9ers.thunderstorm.view.adapter.DailyForecastAdapter
import com.tec9ers.thunderstorm.view.adapter.HourlyForecastRecyclerViewAdapter
import com.tec9ers.thunderstorm.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModels()
    @Inject
    lateinit var formatUtils: FormatUtils
    @Inject
    lateinit var hourlyForecastRecyclerViewAdapter: HourlyForecastRecyclerViewAdapter
    @Inject
    lateinit var dailyForecastAdapter: DailyForecastAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Invert the gradient for the title textview (instead of creating a new drawable)
        val gradientDrawable: GradientDrawable = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.hourly_forecast
        )?.mutate() as GradientDrawable
        gradientDrawable.orientation = GradientDrawable.Orientation.BOTTOM_TOP
        hourly_forecast_title_tv.background = gradientDrawable

        // To prevent clipping of content across linearlayout rounded corners
        hourly_forecast_layout.clipToOutline = true

        hourly_forecast_rv.adapter = hourlyForecastRecyclerViewAdapter
        hourly_forecast_rv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        hourly_forecast_rv.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.HORIZONTAL
            )
        )

        daily_forecast_rv.adapter = dailyForecastAdapter
        daily_forecast_rv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        hourly_forecast_rv.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.HORIZONTAL
            )
        )

        main_card_title_tv.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_homeFragment_to_favCities)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel.oneCallApiLivaData.observe(viewLifecycleOwner) { oneCallAPIResponse ->
            setData(oneCallAPIResponse)
        }

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun setData(oneCallAPIResponse: OneCallAPIResponse) {
        oneCallAPIResponse.apply {
            with(formatUtils) {
                tv_feels_like_temp.text = formatTemp(current.feelsLike)
                tv_temp_main.text = formatTemp(current.temp)
                tv_wind_speed.text = formatWindSpeed(current.windSpeed, current.windDeg)
                tv_weather_condition.text = capitalizeDescription(current.weather[0].description)
                tv_sun_rise.text = formatTime(current.sunrise)
                tv_sun_set.text = formatTime(current.sunset)
                tv_uv_index.text = formatUVI(current.uvi)
                tv_pressure.text = formatPressure(current.pressure)
                tv_humidity.text = formatHumidity(current.humidity)
                tv_visibility.text = formatVisibility(current.visibility)
                hourlyForecastRecyclerViewAdapter.setData(hourly)
                dailyForecastAdapter.setData(daily)
            }
        }
    }
}
