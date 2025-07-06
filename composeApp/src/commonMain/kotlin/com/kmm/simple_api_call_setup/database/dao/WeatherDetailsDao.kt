package com.kmm.simple_api_call_setup.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kmm.simple_api_call_setup.database.localdb.WeatherResponseDB

@Dao
interface WeatherDetailsDao {

    @Query("SELECT name FROM sqlite_master WHERE type='table' AND name=:tableName")
    suspend fun isTableExists(tableName: String): String?

    @Query("SELECT * FROM WeatherResponseDB LIMIT 1")
    suspend fun getWeatherData(): WeatherResponseDB?

    @Update
    suspend fun updateWeatherData(weatherResponseDB: WeatherResponseDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherData(weatherResponseDB: WeatherResponseDB)
}