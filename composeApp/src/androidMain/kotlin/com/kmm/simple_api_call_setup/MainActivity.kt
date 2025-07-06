package com.kmm.simple_api_call_setup

import AppRoot
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.defaultComponentContext
import com.kmm.simple_api_call_setup.data.utils.LocalContextProvider
import com.kmm.simple_api_call_setup.database.getDatabaseBuilder
import navigation.root.DefaultRootComponent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        LocalContextProvider.context = applicationContext
        val db = getDatabaseBuilder(applicationContext)
        val root = DefaultRootComponent(componentContext = defaultComponentContext(), appDatabase = db)
        setContent {
            AppRoot(rootComponent = root, appDatabase = db)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {

}