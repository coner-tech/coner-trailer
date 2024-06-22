package tech.coner.trailer.toolkit.sample.dmvapp

import org.kodein.di.DI
import tech.coner.trailer.toolkit.sample.dmvapp.domain.service.impl.domainServiceModule
import tech.coner.trailer.toolkit.sample.dmvapp.domain.validation.domainValidationModule
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.adapter.presentationAdapterModule
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.localization.presentationLocalizationModule
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.presenter.presentationPresenterModule
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.presentationValidationModule

val testDi get() = DI {
    importAll(
        domainServiceModule,
        domainValidationModule,
        presentationLocalizationModule,
        presentationPresenterModule,
        presentationAdapterModule,
        presentationValidationModule
    )
}