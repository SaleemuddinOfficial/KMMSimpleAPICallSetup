package com.kmm.simple_api_call_setup.data.factory

import io.ktor.client.HttpClient

expect class ApiService() {
    fun build(): HttpClient
}

