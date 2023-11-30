package tech.coner.trailer.app.admin.command.eventpointscalculator

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel
import tech.coner.trailer.app.admin.di.use
import tech.coner.trailer.app.admin.view.EventPointsCalculatorView
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