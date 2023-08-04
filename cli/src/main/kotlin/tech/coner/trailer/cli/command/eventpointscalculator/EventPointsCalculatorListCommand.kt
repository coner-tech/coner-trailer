package tech.coner.trailer.cli.command.eventpointscalculator

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.view.EventPointsCalculatorView
import tech.coner.trailer.io.service.EventPointsCalculatorService

class EventPointsCalculatorListCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
        name = "list",
        help = "List event points calculators"
) {

    override val diContext = diContextDataSession()
    private val service: EventPointsCalculatorService by instance()
    private val view: EventPointsCalculatorView by instance()

    override suspend fun CoroutineScope.coRun() = diContext.use {
        echo(view.render(service.list()))
    }
}