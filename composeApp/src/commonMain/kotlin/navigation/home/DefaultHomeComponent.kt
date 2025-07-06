package navigation.home

import com.arkivanov.decompose.ComponentContext

class DefaultHomeComponent(
    componentContext: ComponentContext,
    private val onShowOnBoardingOne: () -> Unit
) : HomeComponent,
    ComponentContext by componentContext {
    override fun onShowOnBoardingOneScreen() {
        onShowOnBoardingOne()
    }

}