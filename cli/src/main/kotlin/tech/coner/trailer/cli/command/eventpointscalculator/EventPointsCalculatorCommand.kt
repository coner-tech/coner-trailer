package tech.coner.trailer.cli.command.eventpointscalculator

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel

class EventPointsCalculatorCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
        help = "Manage the event points calculators"
) {

    override suspend fun CoroutineScope.coRun() = Unit
}