package navigation.root.home

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import navigation.home.HomeComponent

interface HomeRootComponent {

    fun onBackClicked()

    val routerState: Value<ChildStack<*, HomeChild>>

    sealed class HomeChild {
        class HomeScreen(val component: HomeComponent) : HomeChild()
    }
}