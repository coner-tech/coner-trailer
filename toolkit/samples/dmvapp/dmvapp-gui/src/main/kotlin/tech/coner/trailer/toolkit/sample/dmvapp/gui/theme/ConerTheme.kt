package tech.coner.trailer.toolkit.sample.dmvapp.gui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import tech.coner.trailer.toolkit.sample.dmvapp.gui.presentation.entity.ThemeModePreference

@Composable
fun ConerTheme(
    themeModePreference: ThemeModePreference = ThemeModePreference.AUTO,
    content: @Composable () -> Unit
) {
    val colors = ConerThemeColorsGenerated20240628_2
    MaterialTheme(
        colorScheme = with(colors) {
            when (themeModePreference) {
                ThemeModePreference.AUTO -> if (isSystemInDarkTheme())
                    darkColorScheme()
                else
                    lightColorScheme()
                ThemeModePreference.LIGHT -> lightColorScheme()
                ThemeModePreference.DARK -> darkColorScheme()
            }
        },
        content = content
    )
}
