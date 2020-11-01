package com.tec9ers.thunderstorm.utils

import android.content.Context
import com.tec9ers.thunderstorm.R
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@ActivityScoped
class FormatUtils @Inject constructor(@ApplicationContext val context: Context) {

    fun formatTemp(temp: Double): String {
        return context.getString(R.string.temp, temp.toInt())
    }

    fun formatWindSpeed(windSpeed: Double, direction: Int): String {
        val dir = when (direction / 45) {
            0 -> "N"
            1 -> "NE"
            2 -> "E"
            3 -> "SE"
            4 -> "S"
            5 -> "SW"
            6 -> "W"
            7 -> "NW"
            17 -> "N"
            else -> ""
        }
        return context.getString(R.string.wind_speed, windSpeed) + " " + dir
    }

    fun formatTime(time: Long): String {
        val utcDateTime = LocalDateTime.ofEpochSecond(time, 0, ZoneOffset.UTC)
        val zonedDateTime = ZonedDateTime.of(utcDateTime, ZoneOffset.UTC).withZoneSameInstant(
            ZoneId.systemDefault()
        )
        val format = DateTimeFormatter.ofPattern("hh:mm\na")
        return format.format(zonedDateTime).toUpperCase(Locale.ROOT)
    }

    fun formatDay(time: Long): String {
        val utcDateTime = LocalDateTime.ofEpochSecond(time, 0, ZoneOffset.UTC)
        val zonedDateTime = ZonedDateTime.of(utcDateTime, ZoneOffset.UTC).withZoneSameInstant(
            ZoneId.systemDefault()
        )
        val format = DateTimeFormatter.ofPattern("EEEE, d LLL")
        return format.format(zonedDateTime)
    }

    fun formatPressure(pressure: Int): String {
        val bar = pressure.toFloat() / 1000
        return (context.getString(R.string.pressure, bar))
    }

    fun formatVisibility(visibility: Int): String {
        val km = visibility * 0.001
        return context.getString(R.string.visibility, km)
    }

    fun formatHumidity(humidity: Int): String {
        return context.getString(R.string.humidity, humidity) + " %"
    }

    fun formatUVI(UVI: Double): String {
        return context.getString(R.string.uv_index, UVI)
    }

    fun capitalizeDescription(desc: String): String{
        val words = desc.split(" ")
        var capitalized = ""
        words.forEach{
            capitalized += it.capitalize(Locale.ROOT) + " "
        }

        return capitalized
    }
}
