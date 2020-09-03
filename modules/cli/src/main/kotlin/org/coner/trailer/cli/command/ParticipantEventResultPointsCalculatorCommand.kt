package org.coner.trailer.cli.command

import com.github.ajalt.clikt.core.CliktCommand

class ParticipantEventResultPointsCalculatorCommand : CliktCommand(
        help = """
            Manage the participant event result points calculators
            
            These assign season points according to each participant's results at an event. Mouthful, huh? Suggestions for
            a better name are welcome: https://github.com/caeos/coner-trailer/issues/new
        """.trimIndent()
) {

    override fun run() = Unit
}