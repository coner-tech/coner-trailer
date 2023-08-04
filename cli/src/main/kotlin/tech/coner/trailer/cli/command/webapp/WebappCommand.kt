package tech.coner.trailer.cli.command.webapp

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel

class WebappCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "webapp",
    help = "Start a webapp"
) {
    override suspend fun CoroutineScope.coRun() = Unit
}