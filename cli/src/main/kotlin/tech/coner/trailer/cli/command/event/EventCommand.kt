package tech.coner.trailer.cli.command.event

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel

class EventCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
        name = "event",
        help = "Manage Events"
) {

    override suspend fun CoroutineScope.coRun() = Unit
}