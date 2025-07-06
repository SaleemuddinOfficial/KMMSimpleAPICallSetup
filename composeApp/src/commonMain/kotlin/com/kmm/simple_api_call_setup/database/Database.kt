package com.kmm.simple_api_call_setup.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.kmm.simple_api_call_setup.database.dao.WeatherDetailsDao
import com.kmm.simple_api_call_setup.database.localdb.WeatherResponseDB


@Database(entities = [WeatherResponseDB::class], version = 1)
@ConstructedBy(MyDatabaseConstructor::class)
abstract class WeatherAppDatabase : RoomDatabase() {
    abstract fun weatherDetailsDao(): WeatherDetailsDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object MyDatabaseConstructor : RoomDatabaseConstructor<WeatherAppDatabase> {
    override fun initialize(): WeatherAppDatabase
}