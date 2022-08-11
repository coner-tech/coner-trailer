package tech.coner.trailer.cli.command.eventpointscalculator

import org.kodein.di.DI
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import java.util.logging.Logger.global

class EventPointsCalculatorCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
        help = "Manage the event points calculators"
) {

    override suspend fun coRun() = Unit
}