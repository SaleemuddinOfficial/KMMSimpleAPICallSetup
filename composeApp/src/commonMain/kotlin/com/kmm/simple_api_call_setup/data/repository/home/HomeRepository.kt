package com.kmm.simple_api_call_setup.data.repository.home

import com.kmm.simple_api_call_setup.data.model.home.WeatherResponse
import com.kmm.simple_api_call_setup.data.utils.ApiConstant.ApiUrls.CURRENT_WEATHER
import com.kmm.simple_api_call_setup.data.utils.ApiConstant.ApiUrls.GET_CURRENT_WEATHER
import com.kmm.simple_api_call_setup.data.utils.ApiConstant.ApiUrls.LATITUDE
import com.kmm.simple_api_call_setup.data.utils.ApiConstant.ApiUrls.LONGITUDE
import com.kmm.simple_api_call_setup.data.utils.ApiConstant.BaseUrls.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class HomeRepository(private val httpClient: HttpClient) {
    suspend fun getWeatherUpdate(
        latitude: String,
        longitude: String,
        currentWeather: String
    ): Flow<WeatherResponse> = flow {
        val response = httpClient.get(BASE_URL + GET_CURRENT_WEATHER) {
            url {
                parameters.append(LATITUDE, latitude)
                parameters.append(LONGITUDE, longitude)
                parameters.append(CURRENT_WEATHER, currentWeather)
            }
             //contentType(ContentType.Application.Json)
            //header(key = HEADER_KEY, value = AuthToken)
        }.body<WeatherResponse>()
        emit(response)
    }
}