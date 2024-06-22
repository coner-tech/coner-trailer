package tech.coner.trailer.toolkit.sample.dmvapp.gui.presentation.presenter

import kotlinx.coroutines.flow.StateFlow
import tech.coner.trailer.toolkit.presentation.presenter.StatefulPresenter
import tech.coner.trailer.toolkit.presentation.state.StateContainer
import tech.coner.trailer.toolkit.sample.dmvapp.gui.presentation.state.SettingsState

class SettingsPresenter(
    initialState: SettingsState = SettingsState()
) : StatefulPresenter<SettingsState> {

    private val stateContainer = StateContainer(initialState)
    override val state: SettingsState get() = stateContainer.state
    override val stateFlow: StateFlow<SettingsState> get() = stateContainer.stateFlow

    val themeMode = stateContainer.mutableStateProperty(
        { themeMode },
        { newValue -> copy(themeMode = newValue) }
    )
}
