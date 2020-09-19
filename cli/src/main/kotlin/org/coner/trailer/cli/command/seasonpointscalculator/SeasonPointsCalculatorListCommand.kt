package org.coner.trailer.cli.command.seasonpointscalculator

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.output.CliktConsole
import org.coner.trailer.cli.view.SeasonPointsCalculatorConfigurationView
import org.coner.trailer.io.service.SeasonPointsCalculatorConfigurationService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class SeasonPointsCalculatorListCommand(
        di: DI,
        useConsole: CliktConsole
) : CliktCommand(
        name = "list",
        help = "List season points calculators"
), DIAware {
    init {
        context {
            console = useConsole
        }
    }

    override val di: DI by findOrSetObject { di }

    private val service: SeasonPointsCalculatorConfigurationService by instance()
    private val view: SeasonPointsCalculatorConfigurationView by instance()

    override fun run() {
        echo(view.render(service.list()))
    }
}