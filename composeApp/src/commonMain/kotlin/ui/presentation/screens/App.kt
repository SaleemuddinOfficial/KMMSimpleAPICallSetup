import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.kmm.simple_api_call_setup.database.WeatherAppDatabase
import com.kmm.simple_api_call_setup.di.appModule
import navigation.root.RootComponent
import org.koin.compose.KoinApplication
import ui.presentation.screens.root.home.HomeRootContent
import ui.presentation.screens.root.splash.SplashRootContent

@Composable
fun AppRoot(
    rootComponent: RootComponent,appDatabase: WeatherAppDatabase,modifier: Modifier = Modifier
) {
   KoinApplication(application = {
       modules(appModule())
   }) {
       Surface(
                modifier = modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars)
            ) {
           val routerState by rootComponent.routerState.subscribeAsState()
                when (val child = routerState.active.instance) {
                    is RootComponent.RootChild.SplashRoot -> SplashRootContent(rootComponent,child.splashRootComponent)
                    is RootComponent.RootChild.HomeRoot -> HomeRootContent(rootComponent,child.homeRootComponent,appDatabase=appDatabase)
                }
            }

    }
}