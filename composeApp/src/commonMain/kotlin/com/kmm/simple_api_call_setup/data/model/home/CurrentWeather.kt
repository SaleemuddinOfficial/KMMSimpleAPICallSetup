package com.kmm.simple_api_call_setup.data.model.home


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeather(
    @SerialName("interval")
    val interval: Int = 0,
    @SerialName("is_day")
    val isDay: Int = 0,
    @SerialName("temperature")
    val temperature: Double = 0.0,
    @SerialName("time")
    val time: String = "",
    @SerialName("weathercode")
    val weathercode: Int = 0,
    @SerialName("winddirection")
    val winddirection: Int = 0,
    @SerialName("windspeed")
    val windspeed: Double = 0.0
)