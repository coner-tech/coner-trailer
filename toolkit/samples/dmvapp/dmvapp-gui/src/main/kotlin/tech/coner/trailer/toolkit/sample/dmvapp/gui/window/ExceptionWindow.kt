package tech.coner.trailer.toolkit.sample.dmvapp.gui.window

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import tech.coner.trailer.toolkit.sample.dmvapp.gui.screen.ExceptionScreen

@Composable
fun ExceptionWindow(
    cause: Throwable,
    onCloseRequest: () -> Unit
) {
    Window(
        title = "Exception${cause::class.simpleName?.let { ": $it" } }",
        onCloseRequest = onCloseRequest,
    ) {
        ExceptionScreen(
            cause = cause,
            onCloseRequest = onCloseRequest
        )
    }
}