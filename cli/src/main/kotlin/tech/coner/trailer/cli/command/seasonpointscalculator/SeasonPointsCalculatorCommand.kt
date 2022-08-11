package tech.coner.trailer.cli.command.seasonpointscalculator

import org.kodein.di.DI
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel

class SeasonPointsCalculatorCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "season-points-calculator",
    help = "Manage the season points calculators"
) {
    override suspend fun coRun() = Unit
}