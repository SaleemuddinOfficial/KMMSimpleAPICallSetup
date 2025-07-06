package ui.presentation.screens.root.splash

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
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import navigation.root.RootComponent
import navigation.root.splash.SplashRootComponent
import navigation.root.splash.SplashRootComponent.SplashChild.WeatherSplashScreen
import ui.presentation.screens.splash.WeatherSplashScreen

@Composable
fun SplashRootContent(rootComponent: RootComponent, splashRootComponent: SplashRootComponent) {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars)
        ) {
            val routerState by splashRootComponent.routerState.subscribeAsState()

            Children(
                stack = splashRootComponent.routerState,
                modifier = Modifier.fillMaxSize(),
                animation = stackAnimation(fade() + scale())
            ) {
                when (val instance = routerState.active.instance) {
                    is WeatherSplashScreen -> WeatherSplashScreen(rootComponent,instance.component)
                }
            }
        }
    }
}


