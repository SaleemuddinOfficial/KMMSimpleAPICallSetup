package ui.presentation.viewModel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmm.simple_api_call_setup.data.model.home.WeatherResponse
import com.kmm.simple_api_call_setup.data.repository.home.HomeRepository
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ui.presentation.base.UIState

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {
    private val _weatherResponse = MutableStateFlow<UIState<WeatherResponse>>(UIState.Empty)
    val weatherResponse: MutableStateFlow<UIState<WeatherResponse>> = _weatherResponse

    fun getWeatherUpdate(
        latitude: String,
        longitude: String,
        currentWeather: String
    ) {
        viewModelScope.launch {
            homeRepository.getWeatherUpdate(
                latitude = latitude,
                longitude = longitude,
                currentWeather = currentWeather
            )
                .onStart {
                    _weatherResponse.value = UIState.Loading
                }
                .catch { e ->
                    when (e) {
                        is IOException -> _weatherResponse.value =
                            UIState.Failure(e, error = "No Internet")

                        is ConnectTimeoutException -> _weatherResponse.value =
                            UIState.Failure(e, error = "Connection Timeout")

                        else -> _weatherResponse.value =
                            UIState.Failure(e, error = "Something went wrong")
                    }
                }
                .collect { weatherResponse ->
                    _weatherResponse.value = UIState.Success(weatherResponse)
                }
        }
    }
}

