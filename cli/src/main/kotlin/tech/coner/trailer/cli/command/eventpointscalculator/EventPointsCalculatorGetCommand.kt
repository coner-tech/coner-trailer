package tech.coner.trailer.cli.command.eventpointscalculator

import com.github.ajalt.clikt.parameters.groups.mutuallyExclusiveOptions
import com.github.ajalt.clikt.parameters.groups.required
import com.github.ajalt.clikt.parameters.groups.single
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.cli.view.EventPointsCalculatorView
import tech.coner.trailer.io.service.EventPointsCalculatorService
import java.util.*

class EventPointsCalculatorGetCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
        name = "get",
        help = "Get an event points calculator"
) {

    override val diContext = diContextDataSession()
    private val service: EventPointsCalculatorService by instance()
    private val view: EventPointsCalculatorView by instance()

    sealed class Query {
        data class ById(val id: UUID) : Query()
        data class ByName(val name: String) : Query()
    }

    private val query: Query by mutuallyExclusiveOptions(
            option("--id", help = "Get by ID")
                    .convert {
                        Query.ById(toUuid(it))
                    },
            option("--name", help = "Get by name")
                    .convert {
                        Query.ByName(it)
                    }
    ).single().required()

    override suspend fun CoroutineScope.coRun() = diContext.use {
        val read = when (val query = query) {
            is Query.ById -> service.findById(query.id)
            is Query.ByName -> service.findByName(query.name)
        }
        echo(view.render(read))
    }
}