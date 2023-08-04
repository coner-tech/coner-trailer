package tech.coner.trailer.cli.command

import io.mockk.mockk
import org.kodein.di.*
import tech.coner.trailer.cli.command.seasonpointscalculator.SeasonPointsCalculatorParameterMapper
import tech.coner.trailer.cli.di.*
import tech.coner.trailer.cli.di.command.commandModule
import tech.coner.trailer.cli.di.command.mockkParameterMapperModule
import tech.coner.trailer.di.*
import tech.coner.trailer.presentation.testsupport.di.presenter.mockkPresenterModule
import tech.coner.trailer.presentation.testsupport.di.view.text.mockkTextViewModule

abstract class BaseDataSessionCommandTest<C : BaseCommand> : AbstractCommandTest<C>() {
    override val di = dataSessionCommandTestDi
    override val diContext = diContext { command.diContext.value as DataSessionHolder }
    override val setupGlobal = setupGlobalWithTempEnvironment
}

private val dataSessionCommandTestDi = DI {
    fullContainerTreeOnError = true
    fullDescriptionOnError = true
    bindSingleton { di }
    importAll(
        mockkIoModule, // TODO: eliminate, command to interact with presentation layer only
        mockkConstraintModule, // TODO: eliminate, command to interact with presentation layer only
        mockkServiceModule, // TODO: eliminate, command to interact with presentation layer only
        utilityModule, // TODO: eliminate, command to interact with presentation layer only
        mockkSnoozleModule, // TODO: eliminate, command to interact with presentation layer only
        mockkMotorsportRegApiModule, // TODO: eliminate, command to interact with presentation layer only
        mordantModule,
        mockkViewModule,
        cliPresentationViewModule,
        mockkTextViewModule,
        mockkPresenterModule,
        cliPresentationAdapterModule,
        cliktModule,
        mockkParameterMapperModule,
        commandModule
    )
    bind<SeasonPointsCalculatorParameterMapper> { scoped(DataSessionScope).singleton { mockk() } }
}