package com.kmm.simple_api_call_setup.data.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

actual fun createNetworkMonitor(): NetworkMonitor = AndroidNetworkMonitor(context = LocalContextProvider.context )

class AndroidNetworkMonitor(private val context: Context) : NetworkMonitor {
    private val _isOnline = MutableStateFlow(false)
    override val isOnline: StateFlow<Boolean> = _isOnline.asStateFlow()
    private lateinit var cm: ConnectivityManager
    private val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) { _isOnline.value = true }
        override fun onLost(network: Network) { _isOnline.value = false }
    }

    override fun start() {
        cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.registerDefaultNetworkCallback(callback)
        _isOnline.value = cm.activeNetwork != null
    }

    override fun stop() {
        cm.unregisterNetworkCallback(callback)
    }
}

object LocalContextProvider : ContextProvider {
    lateinit var context: Context
    override fun application(): Context = context
}