package tech.coner.trailer.cli.command.event.run

import org.kodein.di.DI
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel

class EventRunCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "run",
    help = "Manage the runs at an event"
) {
    override suspend fun coRun() = Unit
}