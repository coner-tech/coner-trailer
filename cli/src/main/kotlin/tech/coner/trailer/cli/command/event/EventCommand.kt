package tech.coner.trailer.cli.command.event

import org.kodein.di.DI
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import java.util.logging.Logger.global

class EventCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
        name = "event",
        help = "Manage Events"
) {

    override suspend fun coRun() = Unit
}