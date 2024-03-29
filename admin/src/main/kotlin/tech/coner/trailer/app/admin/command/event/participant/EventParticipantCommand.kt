package tech.coner.trailer.app.admin.command.event.participant

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel

class EventParticipantCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "participant",
    help = "Manage the participants at an event"
) {
    override suspend fun CoroutineScope.coRun() = Unit
}