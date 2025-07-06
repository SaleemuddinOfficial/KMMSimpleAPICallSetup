package ui.presentation.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kmmsimpleapicallsetup.composeapp.generated.resources.Res
import kmmsimpleapicallsetup.composeapp.generated.resources.weather_splash_img
import kotlinx.coroutines.delay
import navigation.root.RootComponent
import navigation.splash.WeatherSplashComponent
import org.jetbrains.compose.resources.painterResource

@Composable
fun WeatherSplashScreen(rootComponent: RootComponent, component: WeatherSplashComponent) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        Image(painter = painterResource(Res.drawable.weather_splash_img), "")
        LaunchedEffect(Unit) {
            while (true) {
                delay(3000)
                rootComponent.navigateToHome()
            }
        }
    }
}