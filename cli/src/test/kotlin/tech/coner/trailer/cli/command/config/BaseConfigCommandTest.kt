package tech.coner.trailer.cli.command.config

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.diContext
import tech.coner.trailer.cli.command.AbstractCommandTest
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.di.cliktModule
import tech.coner.trailer.cli.di.command.commandModule
import tech.coner.trailer.cli.di.command.mockkParameterMapperModule
import tech.coner.trailer.cli.di.mockkCliPresentationAdapterModule
import tech.coner.trailer.cli.di.mockkCliPresentationViewModule
import tech.coner.trailer.cli.di.mockkCliServiceModule
import tech.coner.trailer.cli.di.mockkViewModule
import tech.coner.trailer.cli.di.utilityModule
import tech.coner.trailer.di.mockkConstraintModule
import tech.coner.trailer.di.mockkIoModule
import tech.coner.trailer.di.mockkServiceModule
import tech.coner.trailer.presentation.di.presenter.presenterModule

abstract class BaseConfigCommandTest<C : BaseCommand> : AbstractCommandTest<C>() {

    override val di = DI {
        fullContainerTreeOnError = true
        fullDescriptionOnError = true
        bindSingleton { di }
        importAll(
            mockkIoModule,
            mockkConstraintModule, // considering an exception to use constraints to drive clikt param validation (maybe these should move to presenter though?)
            mockkServiceModule, // TODO: eliminate, command to interact with presenter only
            mockkCliServiceModule, // TODO: eliminate, command to interact with presenter only
            utilityModule,
            mockkViewModule, // TODO: eliminate, command to interact with presenter only
            presenterModule,
            mockkCliPresentationViewModule,
            mockkCliPresentationAdapterModule,
            mockkParameterMapperModule,
            cliktModule,
            commandModule
        )
    }
    override val diContext = diContext { global.requireEnvironment() }
    override val setupGlobal = setupGlobalWithTempEnvironment


}