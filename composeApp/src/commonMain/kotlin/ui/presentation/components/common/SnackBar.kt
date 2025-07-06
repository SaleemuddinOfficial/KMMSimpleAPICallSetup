package ui.presentation.components.common

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SnackBar(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    onlineState: Boolean,
) {

    SnackbarHost(
        hostState = snackbarHostState,
        modifier = modifier,
    ) {
        Snackbar(
            modifier = Modifier,
            contentColor = if (onlineState) Color.Green else Color.Red,
            containerColor = Color.White,
            snackbarData = it,
        )
    }
}
