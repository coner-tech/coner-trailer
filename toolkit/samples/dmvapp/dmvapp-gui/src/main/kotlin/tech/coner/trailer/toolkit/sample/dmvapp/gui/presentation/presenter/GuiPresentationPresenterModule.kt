package tech.coner.trailer.toolkit.sample.dmvapp.gui.presentation.presenter

import org.kodein.di.DI
import org.kodein.di.bindSingleton

val guiPresentationPresenterModule by DI.Module {
    bindSingleton { SettingsPresenter() }
}