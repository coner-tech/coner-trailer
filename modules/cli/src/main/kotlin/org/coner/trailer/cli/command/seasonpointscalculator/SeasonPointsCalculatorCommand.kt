package org.coner.trailer.cli.command.seasonpointscalculator

import com.github.ajalt.clikt.core.CliktCommand

class SeasonPointsCalculatorCommand : CliktCommand(
        name = "season-points-calculator",
        help = "Manage the season points calculators"
) {
    override fun run() = Unit
}