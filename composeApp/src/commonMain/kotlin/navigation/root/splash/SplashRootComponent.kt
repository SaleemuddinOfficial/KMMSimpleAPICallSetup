package navigation.root.splash

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import navigation.splash.WeatherSplashComponent

interface SplashRootComponent {

    val routerState: Value<ChildStack<*, SplashChild>>

    sealed class SplashChild {
        class WeatherSplashScreen(
            val component: WeatherSplashComponent
        ) : SplashChild()
    }
}