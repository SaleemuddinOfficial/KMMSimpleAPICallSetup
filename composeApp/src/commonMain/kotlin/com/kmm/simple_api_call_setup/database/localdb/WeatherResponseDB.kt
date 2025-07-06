package com.kmm.simple_api_call_setup.database.localdb


import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName

@Entity(tableName = "WeatherResponseDB")
data class WeatherResponseDB(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 1L,
    val elevation: String = "",
    val generationtimeMs: Double = 0.0,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val timezone: String = "",
    val timezoneAbbreviation: String = "",
    val utcOffsetSeconds: Int = 0,

    val intervalCurrent: Int = 0,
    val isDayCurrent: Int = 0,
    val temperatureCurrent: Double = 0.0,
    val timeCurrent: String = "",
    val weatherCodeCurrent: Int = 0,
    val windDirectionCurrent: Int = 0,
    val windSpeedCurrent: Double = 0.0,

    val intervalUnits: String = "",
    val isDayUnits: String = "",
    val temperatureUnits: String = "",
    val timeUnits: String = "",
    val weatherCodeUnits: String = "",
    val windDirectionUnits: String = "",
    val windSpeedUnits: String = ""
)