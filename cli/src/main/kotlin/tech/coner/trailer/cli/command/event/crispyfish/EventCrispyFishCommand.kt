package tech.coner.trailer.cli.command.event.crispyfish

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel

class EventCrispyFishCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "crispy-fish",
    help = "Manage crispy fish metadata for an event"
) {
    override suspend fun CoroutineScope.coRun() = Unit
}