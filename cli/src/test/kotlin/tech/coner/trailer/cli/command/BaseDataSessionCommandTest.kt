package tech.coner.trailer.cli.command

import io.mockk.mockk
import org.kodein.di.*
import tech.coner.trailer.cli.command.seasonpointscalculator.SeasonPointsCalculatorParameterMapper
import tech.coner.trailer.cli.di.*
import tech.coner.trailer.cli.di.command.commandModule
import tech.coner.trailer.cli.di.command.mockkParameterMapperModule
import tech.coner.trailer.di.*
import tech.coner.trailer.presentation.testsupport.di.adapter.mockkPresentationAdapterModule
import tech.coner.trailer.presentation.testsupport.di.presenter.mockkPresenterModule
import tech.coner.trailer.presentation.testsupport.di.view.json.mockkJsonViewModule
import tech.coner.trailer.presentation.testsupport.di.view.text.mockkTextViewModule

abstract class BaseDataSessionCommandTest<C : BaseCommand> : AbstractCommandTest<C>() {
    override val di = DI {
        fullContainerTreeOnError = true
        fullDescriptionOnError = true
        bindSingleton { di }
        importAll(
            mockkMapperModule, // TODO: eliminate, command to interact with presentation layer only
            mockkIoModule, // TODO: eliminate, command to interact with presentation layer only
            mockkConstraintModule, // TODO: eliminate, command to interact with presentation layer only
            mockkServiceModule, // TODO: eliminate, command to interact with presentation layer only
            utilityModule, // TODO: eliminate, command to interact with presentation layer only
            mockkSnoozleModule, // TODO: eliminate, command to interact with presentation layer only
            mockkMotorsportRegApiModule, // TODO: eliminate, command to interact with presentation layer only
            mockkEventResults, // TODO: eliminate, command to interact with presentation layer only
            mordantModule,
            mockkViewModule,
            mockkTextViewModule,
            mockkJsonViewModule,
            mockkPresenterModule,
            mockkPresentationAdapterModule,
            mockkCliPresentationAdapterModule,
            mockkCliPresentationViewModule,
            cliktModule,
            mockkParameterMapperModule,
            commandModule
        )
        bind<SeasonPointsCalculatorParameterMapper> { scoped(DataSessionScope).singleton { mockk() } }
    }
    override val diContext = diContext { command.diContext.value as DataSessionHolder }
    override val setupGlobal = setupGlobalWithTempEnvironment
}
