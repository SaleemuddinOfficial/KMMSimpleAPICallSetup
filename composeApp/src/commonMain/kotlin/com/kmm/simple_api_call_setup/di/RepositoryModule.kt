package com.kmm.simple_api_call_setup.di

import com.kmm.simple_api_call_setup.data.repository.home.HomeRepository
import org.koin.dsl.module

val repositoryModule= module {
   single <HomeRepository> { HomeRepository(get()) }
}