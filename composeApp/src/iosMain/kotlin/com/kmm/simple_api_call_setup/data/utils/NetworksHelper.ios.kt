package com.kmm.simple_api_call_setup.data.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.Foundation.NSURLSession
import platform.Foundation.NSURLSessionDataTask
import platform.Foundation.dataTaskWithRequest


actual fun createNetworkMonitor(): NetworkMonitor = IosNetworkMonitor()

class IosNetworkMonitor : NetworkMonitor {
    private val _isOnline = MutableStateFlow(false)
    override val isOnline: StateFlow<Boolean> = _isOnline.asStateFlow()
    private var task: NSURLSessionDataTask? = null

    override fun start() {
        check()
    }
    override fun stop() {
        task?.cancel()
    }

    private fun check() {
        val url = NSURL(string = "https://apple.com")!!
        val req = NSURLRequest.requestWithURL(url)
        task = NSURLSession.sharedSession.dataTaskWithRequest(req) { _, _, err ->
            _isOnline.value = (err == null)
        }
        task?.resume()
    }
}