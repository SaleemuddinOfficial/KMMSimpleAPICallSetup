package ui.presentation.base

sealed interface UIState<out T> {
    data class Success<T>(val data: T) : UIState<T>
    data class Failure<T>(val throwable: Throwable? = null, val data: T? = null, val error: String? = null) : UIState<T>
    data object Loading : UIState<Nothing>
    data object Empty : UIState<Nothing>
}