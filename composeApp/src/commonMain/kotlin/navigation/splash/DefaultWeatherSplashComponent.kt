package navigation.splash

import com.arkivanov.decompose.ComponentContext

class DefaultWeatherSplashComponent(
    componentContext: ComponentContext,
    private val onShowOnHome: () -> Unit
) : WeatherSplashComponent,
    ComponentContext by componentContext {
    override fun onShowHomeScreen() {
        onShowOnHome()
    }

}