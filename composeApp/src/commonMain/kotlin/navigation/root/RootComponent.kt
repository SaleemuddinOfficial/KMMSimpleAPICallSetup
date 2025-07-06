package navigation.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import navigation.root.home.HomeRootComponent
import navigation.root.splash.SplashRootComponent

interface RootComponent {

    val routerState: Value<ChildStack<*, RootChild>>

    fun navigateToHome()

    //This will be used to decide between navigation's
    sealed class RootChild {
        data class SplashRoot(val splashRootComponent: SplashRootComponent) : RootChild()
        data class HomeRoot(val homeRootComponent: HomeRootComponent) : RootChild()
    }

}