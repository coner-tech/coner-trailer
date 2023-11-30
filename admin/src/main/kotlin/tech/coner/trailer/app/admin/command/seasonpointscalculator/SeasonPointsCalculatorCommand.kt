package tech.coner.trailer.app.admin.command.seasonpointscalculator

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel

class SeasonPointsCalculatorCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "season-points-calculator",
    help = "Manage the season points calculators"
) {
    override suspend fun CoroutineScope.coRun() = Unit
}