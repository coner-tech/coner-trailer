package tech.coner.trailer.toolkit.sample.dmvapp.gui

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.window.application
import org.kodein.di.DI
import org.kodein.di.compose.rememberInstance
import org.kodein.di.compose.withDI
import tech.coner.trailer.toolkit.sample.dmvapp.domain.service.impl.domainServiceModule
import tech.coner.trailer.toolkit.sample.dmvapp.domain.validation.domainValidationModule
import tech.coner.trailer.toolkit.sample.dmvapp.gui.presentation.entity.ThemeModePreference
import tech.coner.trailer.toolkit.sample.dmvapp.gui.presentation.presenter.SettingsPresenter
import tech.coner.trailer.toolkit.sample.dmvapp.gui.presentation.presenter.guiPresentationPresenterModule
import tech.coner.trailer.toolkit.sample.dmvapp.gui.theme.ConerTheme
import tech.coner.trailer.toolkit.sample.dmvapp.gui.window.DmvAppMainWindow
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.adapter.presentationAdapterModule
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.localization.presentationLocalizationModule
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.presenter.presentationPresenterModule
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.presentationValidationModule

fun main() = application {
    withDI(di) {
        val settingsPresenter: SettingsPresenter by rememberInstance()
        val themeModePreference = settingsPresenter.themeMode.flow.collectAsState(ThemeModePreference.AUTO).value
        ConerTheme(
            themeModePreference = themeModePreference
        ) {
            DmvAppMainWindow()
        }
    }
}

val di = DI {
    importAll(
        domainServiceModule,
        domainValidationModule,
        presentationLocalizationModule,
        presentationPresenterModule,
        presentationValidationModule,
        presentationAdapterModule,
        guiPresentationPresenterModule,
    )
}
