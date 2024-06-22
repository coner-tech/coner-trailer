package tech.coner.trailer.toolkit.sample.dmvapp.gui.screen.main

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import org.kodein.di.compose.rememberInstance
import org.kodein.di.compose.withDI
import tech.coner.trailer.toolkit.sample.dmvapp.gui.composable.ConerTopLevelNavigationDrawer
import tech.coner.trailer.toolkit.sample.dmvapp.gui.di
import tech.coner.trailer.toolkit.sample.dmvapp.gui.screen.DmvAppScreen
import tech.coner.trailer.toolkit.sample.dmvapp.gui.theme.ConerTheme
import tech.coner.trailer.toolkit.sample.dmvapp.gui.util.strings
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.localization.Strings

@Composable
fun DmvAppNavigationDrawerScreen(
    drawerState: DrawerState,
    selectedScreen: MutableState<DmvAppScreen>,
    appContent: @Composable () -> Unit
) {
    val strings: Strings by rememberInstance()
    DmvAppNavigationDrawerContent(
        drawerState = drawerState,
        selectedScreen = selectedScreen,
        appContent = appContent
    )
}

@Composable
fun DmvAppNavigationDrawerContent(
    drawerState: DrawerState,
    selectedScreen: MutableState<DmvAppScreen>,
    appContent: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val closeAndSelect: (DmvAppScreen) -> Unit = remember {
        {
            coroutineScope.launch {
                drawerState.close()
            }
            selectedScreen.value = it
        }
    }
    ConerTopLevelNavigationDrawer(
        drawerState = drawerState,
        heroTitle = strings.dmvLabel,
        heroSubtitle = strings.dmvMotto,
        innerDrawerContent = {
            NavigationDrawerItem(
                label = { Text(strings.driversLicenseApplicationHeading) },
                icon = { Icon(Icons.Default.AccountBox, strings.driversLicenseApplicationHeading) },
                selected = selectedScreen.value is DmvAppScreen.DriversLicenseApplication,
                onClick = { closeAndSelect(DmvAppScreen.DriversLicenseApplication()) }
            )
            Spacer(Modifier.weight(1f))
            NavigationDrawerItem(
                label = { Text(strings.settings) },
                icon = { Icon(Icons.Default.Settings, strings.settings) },
                selected = selectedScreen.value == DmvAppScreen.Settings,
                onClick = { closeAndSelect(DmvAppScreen.Settings) }
            )
        },
        content = appContent
    )
}

@Composable
@Preview
private fun DmvAppNavigationDrawerScreenPreview() {
    withDI(di) {
        ConerTheme {
            Scaffold {
                DmvAppNavigationDrawerContent(
                    drawerState = rememberDrawerState(DrawerValue.Open),
                    selectedScreen = remember { mutableStateOf(DmvAppScreen.DriversLicenseApplication()) },
                    appContent = {}
                )
            }
        }
    }
}