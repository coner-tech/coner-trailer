package tech.coner.trailer.toolkit.sample.dmvapp.gui.screen.settings

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.kodein.di.compose.rememberInstance
import org.kodein.di.compose.withDI
import tech.coner.trailer.toolkit.sample.dmvapp.gui.composable.ConerTopLevelTopAppBar
import tech.coner.trailer.toolkit.sample.dmvapp.gui.di
import tech.coner.trailer.toolkit.sample.dmvapp.gui.presentation.entity.ThemeModePreference
import tech.coner.trailer.toolkit.sample.dmvapp.gui.presentation.presenter.SettingsPresenter
import tech.coner.trailer.toolkit.sample.dmvapp.gui.theme.ConerTheme
import tech.coner.trailer.toolkit.sample.dmvapp.gui.util.collectAsState
import tech.coner.trailer.toolkit.sample.dmvapp.gui.util.strings
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.localization.Strings

@Composable
fun SettingsScreen(openNavigationDrawer: () -> Unit) {
    val strings: Strings by rememberInstance()
    val presenter: SettingsPresenter by rememberInstance()
    SettingsContent(
        openNavigationDrawer,
        themeMode = presenter.themeMode.collectAsState().value,
        setThemeMode = { presenter.themeMode.value = it }
    )
}

@Composable
fun SettingsContent(
    openNavigationDrawer: () -> Unit,
    themeMode: ThemeModePreference,
    setThemeMode: (ThemeModePreference) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Column {
        ConerTopLevelTopAppBar(
            title = { Text(text = strings.settings) },
            navigationIcon = {
                IconButton(
                    onClick = openNavigationDrawer
                ) {
                    Icon(Icons.Default.Menu, contentDescription = strings.menuContentDescription)
                }
            },
            actions = {},
        )
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(16.dp)
        ) {
            item {
                ThemeCard(
                    themeMode = themeMode,
                    setThemeMode = setThemeMode
                )
            }
        }
    }
}

@Composable
private fun ThemeCard(
    themeMode: ThemeModePreference,
    setThemeMode: (ThemeModePreference) -> Unit
) {
    @Composable
    fun ThemeModeRow(
        selected: Boolean,
        onClick: () -> Unit,
        text: String
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(onClick = onClick),
        ) {
            RadioButton(
                selected = selected,
                onClick = null
            )
            Spacer(Modifier.size(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }

    SettingsCard(
        title = strings.settingsThemeTitle
    ) {
        Text(
            text = strings.settingsThemeModeTitle,
            style = MaterialTheme.typography.titleMedium
        )
        ThemeModeRow(
            selected = themeMode == ThemeModePreference.AUTO,
            onClick = { setThemeMode(ThemeModePreference.AUTO) },
            text = strings.settingsThemeModeAuto
        )
        ThemeModeRow(
            selected = themeMode == ThemeModePreference.LIGHT,
            onClick = { setThemeMode(ThemeModePreference.LIGHT) },
            text = strings.settingsThemeModeLight
        )
        ThemeModeRow(
            selected = themeMode == ThemeModePreference.DARK,
            onClick = { setThemeMode(ThemeModePreference.DARK) },
            text = strings.settingsThemeModeDark
        )
    }
}

@Composable
private fun SettingsCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Column(
            modifier = Modifier
                .padding(16.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(16.dp))
            content()
        }
    }
}

@Preview
@Composable
fun SettingsPreview() {
    withDI(di) {
        val presenter: SettingsPresenter by rememberInstance()
        ConerTheme {
            Scaffold {
                SettingsContent(
                    openNavigationDrawer = {},
                    themeMode = presenter.themeMode.value,
                    setThemeMode = {}
                )
            }
        }
    }
}