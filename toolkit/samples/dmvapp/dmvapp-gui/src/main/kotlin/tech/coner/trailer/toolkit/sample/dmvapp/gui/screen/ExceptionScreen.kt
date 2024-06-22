package tech.coner.trailer.toolkit.sample.dmvapp.gui.screen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tech.coner.trailer.toolkit.sample.dmvapp.gui.theme.ConerTheme

@Composable
fun ExceptionScreen(
    cause: Throwable,
    onCloseRequest: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val scrollState = rememberScrollState()
        Box {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(scrollState)
            ) {
                OutlinedTextField(
                    value = cause.stackTraceToString(),
                    onValueChange = {},
                    readOnly = true,
                )
            }
            VerticalScrollbar(
                adapter = rememberScrollbarAdapter(scrollState),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxHeight()
            )
        }
    }
}

@Preview
@Composable
fun ExceptionScreenPreview() {
    ConerTheme {
        ExceptionScreen(
            cause = Exception("Preview exception message"),
            onCloseRequest = {}
        )
    }
}