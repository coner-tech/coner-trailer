package tech.coner.trailer.toolkit.sample.dmvapp.gui.window

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import org.kodein.di.compose.rememberInstance
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicense
import tech.coner.trailer.toolkit.sample.dmvapp.gui.presentation.presenter.SettingsPresenter
import tech.coner.trailer.toolkit.sample.dmvapp.gui.screen.driverslicenseapplication.DriversLicenseScreen
import tech.coner.trailer.toolkit.sample.dmvapp.gui.util.strings


@Composable
fun DriversLicenseWindow(
    driversLicense: DriversLicense,
    onCloseRequest: () -> Unit
) {
    val settingsPresenter: SettingsPresenter by rememberInstance()
    val windowState = rememberWindowState(width = 480.dp, height = 240.dp)
    Window(
        title = strings.driversLicenseHeading,
        state = windowState,
        resizable = false,
        onCloseRequest = onCloseRequest,
    ) {
        Scaffold {
            DriversLicenseScreen(driversLicense)
        }
    }
}
