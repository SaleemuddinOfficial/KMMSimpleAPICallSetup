package com.kmm.simple_api_call_setup

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform