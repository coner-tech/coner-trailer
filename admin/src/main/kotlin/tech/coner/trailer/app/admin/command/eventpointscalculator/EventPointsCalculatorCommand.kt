package tech.coner.trailer.app.admin.command.eventpointscalculator

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel

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