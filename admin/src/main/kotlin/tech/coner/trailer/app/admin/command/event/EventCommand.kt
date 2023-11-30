package tech.coner.trailer.app.admin.command.event

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel

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