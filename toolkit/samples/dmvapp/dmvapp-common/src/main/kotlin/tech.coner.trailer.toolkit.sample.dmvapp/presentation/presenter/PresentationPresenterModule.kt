package tech.coner.trailer.toolkit.sample.dmvapp.presentation.presenter

import org.kodein.di.DI
import org.kodein.di.bindSingleton

val presentationPresenterModule by DI.Module {
    bindSingleton { DriversLicenseApplicationPresenter(di) }
}