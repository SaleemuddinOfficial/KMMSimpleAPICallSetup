package com.kmm.simple_api_call_setup

import AppRoot
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.kmm.simple_api_call_setup.database.getDatabaseBuilder
import navigation.root.DefaultRootComponent

fun MainViewController() = ComposeUIViewController(configure = { }) {
    val dao = remember { getDatabaseBuilder() }
    val root = remember { DefaultRootComponent(componentContext = DefaultComponentContext(LifecycleRegistry()), appDatabase = dao) }
    AppRoot(
        rootComponent = root,
        appDatabase = dao
    )
}