package com.kmm.simple_api_call_setup.data.utils

import kotlinx.coroutines.flow.StateFlow

interface NetworkMonitor {
    val isOnline: StateFlow<Boolean>
    fun start()
    fun stop()
}
expect fun createNetworkMonitor(): NetworkMonitor


interface ContextProvider {
    fun application(): Any
}
