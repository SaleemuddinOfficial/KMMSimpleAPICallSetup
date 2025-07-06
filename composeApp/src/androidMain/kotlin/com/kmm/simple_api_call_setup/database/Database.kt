package com.kmm.simple_api_call_setup.database

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers

fun getDatabaseBuilder(ctx: Context): WeatherAppDatabase {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath("weather_app.db")
    return Room.databaseBuilder<WeatherAppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    ).setDriver(BundledSQLiteDriver()).setQueryCoroutineContext(Dispatchers.IO).build()
}