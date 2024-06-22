package tech.coner.trailer.toolkit.sample.dmvapp.gui.presentation.state

import tech.coner.trailer.toolkit.presentation.state.State
import tech.coner.trailer.toolkit.sample.dmvapp.gui.presentation.entity.ThemeModePreference

data class SettingsState(
    val themeMode: ThemeModePreference = ThemeModePreference.AUTO,
) : State
