package com.kmm.simple_api_call_setup.di

import org.koin.dsl.module
import ui.presentation.viewModel.home.HomeViewModel

val viewModelModule = module {
    single { HomeViewModel(get()) }
}