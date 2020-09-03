package org.coner.trailer.cli.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.parameters.groups.mutuallyExclusiveOptions
import com.github.ajalt.clikt.parameters.groups.required
import com.github.ajalt.clikt.parameters.groups.single
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.cli.view.ParticipantEventResultPointsCalculatorView
import org.coner.trailer.io.service.ParticipantEventResultPointsCalculatorService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.util.*

class ParticipantEventResultPointsCalculatorGetCommand(
        di: DI,
        useConsole: CliktConsole
) : CliktCommand(
        name = "get",
        help = "Get a participant event result points calculator"
), DIAware {

    init {
        context {
            console = useConsole
        }
    }

    override val di: DI by findOrSetObject { di }
    private val service: ParticipantEventResultPointsCalculatorService by instance()
    private val view: ParticipantEventResultPointsCalculatorView by instance()

    sealed class Query {
        data class ById(val id: UUID) : Query()
        data class ByName(val name: String) : Query()
    }

    private val query: Query by mutuallyExclusiveOptions<Query>(
            option("--id", help = "Get by ID")
                    .convert {
                        Query.ById(toUuid(it))
                    },
            option("--name", help = "Get by name")
                    .convert {
                        Query.ByName(it)
                    }
    ).single().required()

    override fun run() {
        val read = when (val query = query) {
            is Query.ById -> service.findById(query.id)
            is Query.ByName -> service.findByName(query.name)
        }
        echo(view.render(read ?: return))
    }
}