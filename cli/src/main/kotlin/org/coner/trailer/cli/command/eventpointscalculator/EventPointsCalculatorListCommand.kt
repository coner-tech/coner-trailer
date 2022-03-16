package org.coner.trailer.cli.command.eventpointscalculator

import com.github.ajalt.clikt.core.CliktCommand
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.cli.di.use
import org.coner.trailer.cli.view.EventPointsCalculatorView
import org.coner.trailer.io.service.EventPointsCalculatorService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance

class EventPointsCalculatorListCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
        name = "list",
        help = "List event points calculators"
), DIAware {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }
    private val service: EventPointsCalculatorService by instance()
    private val view: EventPointsCalculatorView by instance()

    override fun run() = diContext.use {
        echo(view.render(service.list()))
    }
}