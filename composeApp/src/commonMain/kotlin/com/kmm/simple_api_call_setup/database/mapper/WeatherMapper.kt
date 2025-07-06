package com.kmm.simple_api_call_setup.database.mapper

import com.kmm.simple_api_call_setup.data.model.home.WeatherResponse
import com.kmm.simple_api_call_setup.database.localdb.WeatherResponseDB

object WeatherMapper {
    fun from(weatherResponse: WeatherResponse): WeatherResponseDB {
        return WeatherResponseDB(
            elevation = weatherResponse.elevation,
            generationtimeMs = weatherResponse.generationtimeMs,
            latitude = weatherResponse.latitude,
            longitude = weatherResponse.longitude,
            timezone = weatherResponse.timezone,
            timezoneAbbreviation = weatherResponse.timezoneAbbreviation,
            utcOffsetSeconds = weatherResponse.utcOffsetSeconds,
            intervalCurrent = weatherResponse.currentWeather.interval,
            isDayCurrent = weatherResponse.currentWeather.isDay,
            temperatureCurrent = weatherResponse.currentWeather.temperature,
            timeCurrent = weatherResponse.currentWeather.time,
            weatherCodeCurrent = weatherResponse.currentWeather.weathercode,
            windDirectionCurrent = weatherResponse.currentWeather.winddirection,
            windSpeedCurrent = weatherResponse.currentWeather.windspeed,
            intervalUnits = weatherResponse.currentWeatherUnits.interval,
            isDayUnits = weatherResponse.currentWeatherUnits.isDay,
            temperatureUnits = weatherResponse.currentWeatherUnits.temperature,
            timeUnits = weatherResponse.currentWeatherUnits.time,
            weatherCodeUnits = weatherResponse.currentWeatherUnits.weathercode,
            windDirectionUnits = weatherResponse.currentWeatherUnits.winddirection,
            windSpeedUnits = weatherResponse.currentWeatherUnits.windspeed,
        )
    }
}


