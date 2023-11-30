package tech.coner.trailer.app.admin.command.event.run

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel

class EventRunCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "run",
    help = "Manage the runs at an event"
) {
    override suspend fun CoroutineScope.coRun() = Unit
}