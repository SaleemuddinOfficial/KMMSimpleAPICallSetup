package ui.presentation.screens.root.home

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.kmm.simple_api_call_setup.database.WeatherAppDatabase
import navigation.root.RootComponent
import navigation.root.home.HomeRootComponent
import navigation.root.home.HomeRootComponent.HomeChild.HomeScreen
import ui.presentation.screens.home.HomeScreen

@Composable
fun HomeRootContent(
    rootComponent: RootComponent,
    homeRootComponent: HomeRootComponent,
    appDatabase: WeatherAppDatabase
) {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars)
        ) {
            val routerState by homeRootComponent.routerState.subscribeAsState()

            Surface(
                modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars)
            ) {
                Children(
                    stack = homeRootComponent.routerState,
                    modifier = Modifier.fillMaxSize(),
                    animation = stackAnimation(slide())
                ) {
                    when (val instance = routerState.active.instance) {
                        is HomeScreen -> HomeScreen(
                            rootComponent,
                            instance.component,
                            appDatabase = appDatabase
                        )
                    }

                }
            }
        }
    }
}