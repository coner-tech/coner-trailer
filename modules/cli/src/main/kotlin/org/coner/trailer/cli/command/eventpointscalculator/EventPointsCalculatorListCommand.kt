package org.coner.trailer.cli.command.eventpointscalculator

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.output.CliktConsole
import org.coner.trailer.cli.view.EventPointsCalculatorView
import org.coner.trailer.io.service.EventPointsCalculatorService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class EventPointsCalculatorListCommand(
        di: DI,
        useConsole: CliktConsole,
        private val view: EventPointsCalculatorView
) : CliktCommand(
        name = "list",
        help = "List event points calculators"
), DIAware {

    init {
        context {
            console = useConsole
        }
    }

    override val di: DI by findOrSetObject { di }
    private val service: EventPointsCalculatorService by instance()

    override fun run() {
        echo(view.render(service.list()))
    }
}