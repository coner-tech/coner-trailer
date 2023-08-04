package tech.coner.trailer.cli.command.event.participant

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel

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