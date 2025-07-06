package com.kmm.simple_api_call_setup.di

import com.kmm.simple_api_call_setup.data.factory.ApiService
import com.kmm.simple_api_call_setup.database.WeatherAppDatabase
import com.kmm.simple_api_call_setup.database.getDatabaseBuilder
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { ApiService().build() }
    single<WeatherAppDatabase> { getDatabaseBuilder(get()) }
}