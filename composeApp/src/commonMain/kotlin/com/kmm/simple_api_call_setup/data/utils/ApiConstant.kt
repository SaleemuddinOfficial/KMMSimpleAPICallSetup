package com.kmm.simple_api_call_setup.data.utils

object ApiConstant {
    object BaseUrls {
        const val BASE_URL: String = "https://api.open-meteo.com/v1"
    }

    object ApiUrls {
        const val GET_CURRENT_WEATHER = "/forecast?"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
        const val CURRENT_WEATHER = "current_weather"
    }
}