package org.coner.trailer.cli.command.seasonpointscalculator

import com.github.ajalt.clikt.core.CliktCommand
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.cli.di.use
import org.coner.trailer.cli.view.SeasonPointsCalculatorConfigurationView
import org.coner.trailer.io.service.SeasonPointsCalculatorConfigurationService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance

class SeasonPointsCalculatorListCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
        name = "list",
        help = "List season points calculators"
), DIAware {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }
    private val service: SeasonPointsCalculatorConfigurationService by instance()
    private val view: SeasonPointsCalculatorConfigurationView by instance()

    override fun run() = diContext.use {
        echo(view.render(service.list()))
    }
}