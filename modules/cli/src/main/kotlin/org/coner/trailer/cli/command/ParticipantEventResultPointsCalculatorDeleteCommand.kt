package org.coner.trailer.cli.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.groups.mutuallyExclusiveOptions
import com.github.ajalt.clikt.parameters.groups.required
import com.github.ajalt.clikt.parameters.groups.single
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import org.coner.snoozle.util.isUuidPattern
import org.coner.trailer.cli.view.ParticipantEventResultPointsCalculatorView
import org.coner.trailer.io.service.ParticipantEventResultPointsCalculatorService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.util.*

class ParticipantEventResultPointsCalculatorDeleteCommand(
        di: DI,
        useConsole: CliktConsole
) : CliktCommand(
        name = "delete",
        help = "Delete a participant event result points calculator"
), DIAware {

    init {
        context {
            console = useConsole
        }
    }

    override val di: DI by findOrSetObject { di }
    private val service: ParticipantEventResultPointsCalculatorService by instance()

    private val id: UUID by argument()
            .convert {
                if (!isUuidPattern.matcher(it).matches())
                    fail("Not a UUID")
                UUID.fromString(it)
            }

    override fun run() {
        val delete = service.findById(id)
        service.delete(delete)
    }
}