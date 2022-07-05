package tech.coner.trailer.cli.command.seasonpointscalculator

import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.view.SeasonPointsCalculatorConfigurationView
import tech.coner.trailer.io.service.SeasonPointsCalculatorConfigurationService

class SeasonPointsCalculatorListCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "list",
    help = "List season points calculators"
) {

    override val diContext = diContextDataSession()
    private val service: SeasonPointsCalculatorConfigurationService by instance()
    private val view: SeasonPointsCalculatorConfigurationView by instance()

    override suspend fun coRun() = diContext.use {
        echo(view.render(service.list()))
    }
}