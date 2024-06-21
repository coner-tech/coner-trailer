package tech.coner.trailer.toolkit.sample.dmvapp.domain.validation

import org.kodein.di.DI
import org.kodein.di.bindSingleton

val domainValidationModule by DI.Module {
    bindSingleton { DriversLicenseClerk() }
}