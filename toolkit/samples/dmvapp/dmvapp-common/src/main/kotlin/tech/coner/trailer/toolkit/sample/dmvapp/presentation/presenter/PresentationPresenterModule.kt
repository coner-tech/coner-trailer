package tech.coner.trailer.toolkit.sample.dmvapp.presentation.presenter

import org.kodein.di.*
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.adapter.DriversLicenseApplicationModelAdapters

val presentationPresenterModule by DI.Module {
    bindSingleton {
        DriversLicenseApplicationPresenter(
            initialState = null,
            adapters = DriversLicenseApplicationModelAdapters(),
            service = instance(),
            validator = instance(),
            strings = instance()
        )
    }
}