package tech.coner.trailer.toolkit.sample.dmvapp.domain.service.impl

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import org.kodein.di.new
import tech.coner.trailer.toolkit.sample.dmvapp.domain.service.DriversLicenseApplicationService

val domainServiceModule by DI.Module {
    bindSingleton { new(::DriversLicenseApplicationServiceImpl) }
    bindSingleton<DriversLicenseApplicationService> { instance<DriversLicenseApplicationServiceImpl>() }
}
