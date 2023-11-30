package tech.coner.trailer.app.admin.command

import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.bindSingleton
import org.kodein.di.diContext
import org.kodein.di.scoped
import org.kodein.di.singleton
import tech.coner.trailer.app.admin.command.seasonpointscalculator.SeasonPointsCalculatorParameterMapper
import tech.coner.trailer.app.admin.di.cliktModule
import tech.coner.trailer.app.admin.di.command.commandModule
import tech.coner.trailer.app.admin.di.command.mockkParameterMapperModule
import tech.coner.trailer.app.admin.di.mockkCliPresentationAdapterModule
import tech.coner.trailer.app.admin.di.mockkCliPresentationViewModule
import tech.coner.trailer.app.admin.di.mockkViewModule
import tech.coner.trailer.app.admin.di.mordantModule
import tech.coner.trailer.app.admin.di.utilityModule
import tech.coner.trailer.di.DataSessionHolder
import tech.coner.trailer.di.DataSessionScope
import tech.coner.trailer.di.mockkConstraintModule
import tech.coner.trailer.di.mockkEventResults
import tech.coner.trailer.di.mockkIoModule
import tech.coner.trailer.di.mockkMapperModule
import tech.coner.trailer.di.mockkMotorsportRegApiModule
import tech.coner.trailer.di.mockkServiceModule
import tech.coner.trailer.di.mockkSnoozleModule
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
