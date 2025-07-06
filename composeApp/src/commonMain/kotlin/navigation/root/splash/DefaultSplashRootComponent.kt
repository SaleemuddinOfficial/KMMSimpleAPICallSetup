package navigation.root.splash

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import navigation.root.splash.DefaultSplashRootComponent.SplashConfig.WeatherSplash
import navigation.root.splash.SplashRootComponent.SplashChild
import navigation.root.splash.SplashRootComponent.SplashChild.WeatherSplashScreen
import navigation.splash.DefaultWeatherSplashComponent
import navigation.splash.WeatherSplashComponent


class DefaultSplashRootComponent(componentContext: ComponentContext) : SplashRootComponent,
    ComponentContext by componentContext {

    private val splashNavigation = StackNavigation<SplashConfig>()

    override val routerState: Value<ChildStack<*, SplashChild>> =
        childStack(
            source = splashNavigation,
            serializer = SplashConfig.serializer(),
            initialStack = { listOf(WeatherSplash) },
            handleBackButton = true,
            childFactory = ::splashChild
        )

    private fun splashChild(
        config: SplashConfig,
        componentContext: ComponentContext
    ): SplashChild =
        when (config) {
            WeatherSplash -> WeatherSplashScreen(
                weatherSplashComponent(componentContext)
            )
        }

    @Serializable
    sealed interface SplashConfig {
        @Serializable
        data object WeatherSplash : SplashConfig
    }

    private fun weatherSplashComponent(componentContext: ComponentContext): WeatherSplashComponent =
        DefaultWeatherSplashComponent(componentContext = componentContext,
            onShowOnHome = {})

}
