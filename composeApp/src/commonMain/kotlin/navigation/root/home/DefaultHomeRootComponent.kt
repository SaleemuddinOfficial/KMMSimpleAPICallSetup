package navigation.root.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import navigation.home.DefaultHomeComponent
import navigation.home.HomeComponent
import navigation.root.home.DefaultHomeRootComponent.HomeConfig.Home
import navigation.root.home.HomeRootComponent.HomeChild


class DefaultHomeRootComponent(
    componentContext: ComponentContext,
) : HomeRootComponent,
    ComponentContext by componentContext {

    private val homeRootNavigation = StackNavigation<HomeConfig>()

    override val routerState: Value<ChildStack<*, HomeChild>> = childStack(
        source = homeRootNavigation,
        serializer = HomeConfig.serializer(),
        initialStack = { listOf(Home) },
        handleBackButton = true,
        childFactory = ::menuChild
    )

    override fun onBackClicked() {
        homeRootNavigation.pop()
    }


    private fun menuChild(
        config: HomeConfig, componentContext: ComponentContext
    ): HomeChild = when (config) {
        is Home -> HomeChild.HomeScreen(homeComponent(componentContext))
    }

    @Serializable
    sealed interface HomeConfig {

        @Serializable
        data object Home : HomeConfig

    }

    private fun homeComponent(componentContext: ComponentContext): HomeComponent =
        DefaultHomeComponent(
            componentContext = componentContext, onShowOnBoardingOne = {

            }
        )


}