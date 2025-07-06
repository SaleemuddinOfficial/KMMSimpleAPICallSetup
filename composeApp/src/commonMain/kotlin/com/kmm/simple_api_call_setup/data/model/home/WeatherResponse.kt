package com.kmm.simple_api_call_setup.data.model.home


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("current_weather")
    val currentWeather: CurrentWeather = CurrentWeather(),
    @SerialName("current_weather_units")
    val currentWeatherUnits: CurrentWeatherUnits = CurrentWeatherUnits(),
    @SerialName("elevation")
    val elevation: String = "",
    @SerialName("generationtime_ms")
    val generationtimeMs: Double = 0.0,
    @SerialName("latitude")
    val latitude: Double = 0.0,
    @SerialName("longitude")
    val longitude: Double = 0.0,
    @SerialName("timezone")
    val timezone: String = "",
    @SerialName("timezone_abbreviation")
    val timezoneAbbreviation: String = "",
    @SerialName("utc_offset_seconds")
    val utcOffsetSeconds: Int = 0
)