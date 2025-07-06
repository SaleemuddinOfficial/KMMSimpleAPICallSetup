package ui.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kmm.simple_api_call_setup.data.model.home.WeatherResponse
import com.kmm.simple_api_call_setup.data.utils.createNetworkMonitor
import com.kmm.simple_api_call_setup.database.WeatherAppDatabase
import com.kmm.simple_api_call_setup.database.localdb.WeatherResponseDB
import com.kmm.simple_api_call_setup.database.mapper.WeatherMapper
import dev.jordond.compass.geocoder.Geocoder
import kmmsimpleapicallsetup.composeapp.generated.resources.Figtree_BoldItalic
import kmmsimpleapicallsetup.composeapp.generated.resources.Figtree_SemiBoldItalic
import kmmsimpleapicallsetup.composeapp.generated.resources.Res
import kmmsimpleapicallsetup.composeapp.generated.resources.day_icon
import kmmsimpleapicallsetup.composeapp.generated.resources.location_pin_icon
import kmmsimpleapicallsetup.composeapp.generated.resources.night_icon
import kotlinx.coroutines.launch
import navigation.home.HomeComponent
import navigation.root.RootComponent
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import ui.presentation.base.UIState
import ui.presentation.components.common.SnackBar
import ui.presentation.viewModel.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    rootComponent: RootComponent,
    homeComponent: HomeComponent,
    appDatabase: WeatherAppDatabase
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val monitor = createNetworkMonitor()
    val online by monitor.isOnline.collectAsState(initial = false)

    val weatherDetailsDao = appDatabase.weatherDetailsDao()
    var weatherResponseDB: WeatherResponseDB? = null

    scope.launch {
        val exists = weatherDetailsDao.isTableExists("WeatherResponseDB")
        if (exists != null) {
            val data = weatherDetailsDao.getWeatherData()
            if (data != null) {
                weatherResponseDB = data
            }
        }
    }

    LaunchedEffect(Unit) { monitor.start() }
    DisposableEffect(Unit) { onDispose { monitor.stop() } }
    if (online) {
        scope.launch {
            snackbarHostState.showSnackbar(" 🟢 Online")
        }
    }

    Scaffold(
        snackbarHost = { SnackBar(snackbarHostState = snackbarHostState, onlineState = online) },
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.shadow(elevation = 8.dp),
                title = {
                    Text(
                        "Weather App", style = TextStyle(
                            fontSize = 24.sp,
                            fontFamily = FontFamily(Font(Res.font.Figtree_BoldItalic))
                        )
                    )
                },
            )
        },
        content = {
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
                val homeViewModel: HomeViewModel = koinViewModel<HomeViewModel>()
                val uiState by homeViewModel.weatherResponse.collectAsState()
                LaunchedEffect(Unit) {
                    homeViewModel.getWeatherUpdate(
                        latitude = "40.710335",
                        longitude = "-73.99309",
                        currentWeather = "true"
                    )
                }
                when (uiState) {
                    is UIState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.fillMaxSize().wrapContentSize()
                        )
                    }

                    is UIState.Success -> {
                        val weatherResponse = (uiState as UIState.Success<WeatherResponse>).data
                        scope.launch {
                            weatherDetailsDao.insertWeatherData(
                                WeatherMapper.from(
                                    weatherResponse
                                )
                            )
                        }
                        WeatherDetailCard(weatherResponse)
                    }

                    is UIState.Failure -> {
                        val error = (uiState as UIState.Failure).error
                        error?.let { error -> scope.launch { snackbarHostState.showSnackbar(error) } }
                        weatherResponseDB?.let { WeatherDetailDBCard(it) }
                    }

                    is UIState.Empty -> {
                        Text(
                            text = "No Weather Data available.", modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        },
    )
}

@Composable
fun WeatherDetailCard(weatherResponse: WeatherResponse) {
    Card(
        modifier = Modifier.fillMaxWidth().wrapContentSize().padding(16.dp).background(Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        val isADay: Boolean = weatherResponse.currentWeather.isDay == 0
        val dayOrNight: String = if (isADay) "Day" else "Night"
        Column(modifier = Modifier.background(Color.White)) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.location_pin_icon),
                    "Location Pin Icon",
                    modifier = Modifier.width(36.dp).height(36.dp).padding(end = 8.dp)
                )
                Text(
                    "Latitude : ${weatherResponse.latitude}\nLongitude : ${weatherResponse.longitude}",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(Res.font.Figtree_SemiBoldItalic))
                    )
                )
            }
            Text(
                "${weatherResponse.currentWeather.temperature}${weatherResponse.currentWeatherUnits.temperature}",
                style = TextStyle(
                    fontSize = 24.sp, fontFamily = FontFamily(Font(Res.font.Figtree_BoldItalic))
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Image(
                painter = if (isADay) painterResource(Res.drawable.day_icon) else painterResource(
                    Res.drawable.night_icon
                ),
                "",
                modifier = Modifier.padding(16.dp).widthIn(48.dp).height(48.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                "Time: ${weatherResponse.currentWeather.time} ${weatherResponse.timezone}",
                style = TextStyle(
                    fontSize = 18.sp, fontFamily = FontFamily(Font(Res.font.Figtree_SemiBoldItalic))
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                "Wind: ${weatherResponse.currentWeather.windspeed} ${weatherResponse.currentWeatherUnits.windspeed}",
                style = TextStyle(
                    fontSize = 16.sp, fontFamily = FontFamily(Font(Res.font.Figtree_SemiBoldItalic))
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 16.dp)
            )
            Text(
                "Elevation: ${weatherResponse.elevation}", style = TextStyle(
                    fontSize = 16.sp, fontFamily = FontFamily(Font(Res.font.Figtree_SemiBoldItalic))
                ), modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 16.dp)
            )

            Text(
                "Day/Night: $dayOrNight", style = TextStyle(
                    fontSize = 16.sp, fontFamily = FontFamily(Font(Res.font.Figtree_SemiBoldItalic))
                ), modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 16.dp)
            )
            Text(
                "Weather Code: ${weatherResponse.currentWeather.weathercode}",
                style = TextStyle(
                    fontSize = 16.sp, fontFamily = FontFamily(Font(Res.font.Figtree_SemiBoldItalic))
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp, bottom = 16.dp)
            )
        }
    }
}

@Composable
fun WeatherDetailDBCard(weatherResponseDB: WeatherResponseDB) {
    Card(
        modifier = Modifier.fillMaxWidth().wrapContentSize().padding(16.dp).background(Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        val isADay: Boolean = weatherResponseDB.isDayCurrent == 0
        val dayOrNight: String = if (isADay) "Day" else "Night"
        Column(modifier = Modifier.background(Color.White)) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.location_pin_icon),
                    "Location Pin Icon",
                    modifier = Modifier.width(36.dp).height(36.dp).padding(end = 8.dp)
                )
                Text(
                    "Latitude : ${weatherResponseDB.latitude}\nLongitude : ${weatherResponseDB.longitude}",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(Res.font.Figtree_SemiBoldItalic))
                    )
                )
            }
            Text(
                "${weatherResponseDB.temperatureCurrent}${weatherResponseDB.temperatureUnits}",
                style = TextStyle(
                    fontSize = 24.sp, fontFamily = FontFamily(Font(Res.font.Figtree_BoldItalic))
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Image(
                painter = if (isADay) painterResource(Res.drawable.day_icon) else painterResource(
                    Res.drawable.night_icon
                ),
                "",
                modifier = Modifier.padding(16.dp).widthIn(48.dp).height(48.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                "Time: ${weatherResponseDB.timeCurrent} ${weatherResponseDB.timezone}",
                style = TextStyle(
                    fontSize = 18.sp, fontFamily = FontFamily(Font(Res.font.Figtree_SemiBoldItalic))
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                "Wind: ${weatherResponseDB.windSpeedCurrent} ${weatherResponseDB.windSpeedUnits}",
                style = TextStyle(
                    fontSize = 16.sp, fontFamily = FontFamily(Font(Res.font.Figtree_SemiBoldItalic))
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 16.dp)
            )
            Text(
                "Elevation: ${weatherResponseDB.elevation}", style = TextStyle(
                    fontSize = 16.sp, fontFamily = FontFamily(Font(Res.font.Figtree_SemiBoldItalic))
                ), modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 16.dp)
            )

            Text(
                "Day/Night: $dayOrNight", style = TextStyle(
                    fontSize = 16.sp, fontFamily = FontFamily(Font(Res.font.Figtree_SemiBoldItalic))
                ), modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 16.dp)
            )
            Text(
                "Weather Code: ${weatherResponseDB.weatherCodeCurrent}",
                style = TextStyle(
                    fontSize = 16.sp, fontFamily = FontFamily(Font(Res.font.Figtree_SemiBoldItalic))
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp, bottom = 16.dp)
            )
        }
    }
}


