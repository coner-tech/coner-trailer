package tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val presentationValidationModule by DI.Module {
    bindSingleton { DriversLicenseApplicationModelValidator(instance()) }
}