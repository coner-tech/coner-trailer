package tech.coner.trailer.toolkit.sample.dmvapp.gui.window

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import tech.coner.trailer.toolkit.sample.dmvapp.gui.screen.main.DmvAppMainScreen
import tech.coner.trailer.toolkit.sample.dmvapp.gui.util.LocalWindowState
import tech.coner.trailer.toolkit.sample.dmvapp.gui.util.strings
import java.awt.Dimension

@Composable
fun ApplicationScope.DmvAppMainWindow() {
    val state = rememberWindowState()
    CompositionLocalProvider(LocalWindowState provides state) {
        Window(
            title = strings.dmvLabel,
            icon = painterResource("coner-icon/coner-icon_512.png"),
            state = state,
            onCloseRequest = ::exitApplication,
        ) {
            LaunchedEffect(null) {
                window.minimumSize = Dimension(600, 600)
            }
            DmvAppMainScreen()
        }
    }
}