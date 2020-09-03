package org.coner.trailer.cli.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.output.CliktConsole
import org.coner.trailer.cli.view.ParticipantEventResultPointsCalculatorView
import org.coner.trailer.io.service.ParticipantEventResultPointsCalculatorService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class ParticipantEventResultPointsCalculatorListCommand(
        di: DI,
        useConsole: CliktConsole,
        private val view: ParticipantEventResultPointsCalculatorView
) : CliktCommand(
        name = "list",
        help = "List participant event result points calculators"
), DIAware {

    init {
        context {
            console = useConsole
        }
    }

    override val di: DI by findOrSetObject { di }
    private val service: ParticipantEventResultPointsCalculatorService by instance()

    override fun run() {
        echo(view.render(service.list()))
    }
}