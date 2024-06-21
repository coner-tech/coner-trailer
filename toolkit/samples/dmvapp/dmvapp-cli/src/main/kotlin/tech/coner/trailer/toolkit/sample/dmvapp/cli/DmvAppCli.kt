package tech.coner.trailer.toolkit.sample.dmvapp.cli

import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.toolkit.sample.dmvapp.cli.command.RootCommand
import tech.coner.trailer.toolkit.sample.dmvapp.cli.command.cliCommandModule
import tech.coner.trailer.toolkit.sample.dmvapp.cli.view.cliViewModule
import tech.coner.trailer.toolkit.sample.dmvapp.domain.service.impl.domainServiceModule
import tech.coner.trailer.toolkit.sample.dmvapp.domain.validation.domainValidationModule
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.localization.presentationLocalizationModule
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.presenter.presentationPresenterModule
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.presentationValidationModule

fun main(args: Array<String>) {
    val command: RootCommand by di.instance()
    command.main(args)
}

private val di = DI {
    importAll(
        cliCommandModule,
        cliViewModule,
        domainServiceModule,
        domainValidationModule,
        presentationLocalizationModule,
        presentationPresenterModule,
        presentationValidationModule
    )
}