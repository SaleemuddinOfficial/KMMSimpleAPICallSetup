package navigation.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.kmm.simple_api_call_setup.database.WeatherAppDatabase
import kotlinx.serialization.Serializable
import navigation.root.DefaultRootComponent.RootConfig.*
import navigation.root.RootComponent.*
import navigation.root.RootComponent.RootChild.*
import navigation.root.home.DefaultHomeRootComponent
import navigation.root.splash.DefaultSplashRootComponent

class DefaultRootComponent(
    componentContext: ComponentContext,
    appDatabase: WeatherAppDatabase
) : RootComponent,
    ComponentContext by componentContext {

    private val rootNavigation = StackNavigation<RootConfig>()

    override val routerState: Value<ChildStack<*, RootChild>> =
        childStack(
            source = rootNavigation,
            serializer = RootConfig.serializer(),
            initialStack = { listOf(Splash) },
            handleBackButton = true,
            childFactory = ::child,
        )


    private fun child(config: RootConfig, componentContext: ComponentContext): RootChild =
        when (config) {
            is Splash -> SplashRoot(
                DefaultSplashRootComponent(
                    componentContext = componentContext,
                )
            )
            is Home -> HomeRoot(DefaultHomeRootComponent(componentContext = componentContext))

        }

    override fun navigateToHome() {
         rootNavigation.replaceAll(Home)
    }

    @Serializable
    private sealed interface RootConfig {
        @Serializable
        data object Splash : RootConfig

        @Serializable
        data object Home : RootConfig
    }

}