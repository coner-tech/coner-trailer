package tech.coner.trailer.cli.command

import org.kodein.di.DI
import org.kodein.di.diContext
import tech.coner.trailer.di.DataSessionHolder
import tech.coner.trailer.di.mockkMotorsportRegApiModule
import tech.coner.trailer.di.mockkSnoozleModule

abstract class BaseDataSessionCommandTest<C : BaseCommand> : AbstractCommandTest<C>() {
    override val di = DI.lazy {
        fullContainerTreeOnError = true
        fullDescriptionOnError = true
        extend(super.di)
        import(mockkSnoozleModule)
        import(mockkMotorsportRegApiModule)
    }
    override val diContext = diContext { command.diContext.value as DataSessionHolder }
    override val setupGlobal = setupGlobalWithTempEnvironment
}