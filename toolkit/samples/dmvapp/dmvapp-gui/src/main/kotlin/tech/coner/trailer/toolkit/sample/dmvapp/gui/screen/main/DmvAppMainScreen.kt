package tech.coner.trailer.toolkit.sample.dmvapp.gui.screen.main

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import org.kodein.di.compose.rememberDI
import org.kodein.di.compose.rememberInstance
import org.kodein.di.instance
import tech.coner.trailer.toolkit.sample.dmvapp.gui.presentation.entity.ThemeModePreference
import tech.coner.trailer.toolkit.sample.dmvapp.gui.presentation.presenter.SettingsPresenter
import tech.coner.trailer.toolkit.sample.dmvapp.gui.screen.DmvAppScreen
import tech.coner.trailer.toolkit.sample.dmvapp.gui.screen.driverslicenseapplication.DriversLicenseApplicationFormScreen
import tech.coner.trailer.toolkit.sample.dmvapp.gui.screen.settings.SettingsScreen
import tech.coner.trailer.toolkit.sample.dmvapp.gui.theme.ConerTheme
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.localization.Strings

@Composable
@Preview
fun DmvAppMainScreen() {
    val strings: Strings by rememberInstance()
    val selectedScreen = remember {
        mutableStateOf<DmvAppScreen>(DmvAppScreen.DriversLicenseApplication())
    }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val openNavigationDrawer: () -> Unit = remember {
        {
            coroutineScope.launch {
                drawerState.open()
            }
        }
    }
    Scaffold {
        DmvAppNavigationDrawerScreen(
            drawerState = drawerState,
            selectedScreen = selectedScreen
        ) {
            when (val screen = selectedScreen.value) {
                is DmvAppScreen.DriversLicenseApplication -> {
                    DriversLicenseApplicationFormScreen(
                        screen = screen,
                        openNavigationDrawer = openNavigationDrawer
                    )
                }
                DmvAppScreen.Settings -> {
                    SettingsScreen(
                        openNavigationDrawer = openNavigationDrawer
                    )
                }
            }
        }
    }
}
