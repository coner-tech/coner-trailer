package tech.coner.trailer.cli.command.seasonpointscalculator

import com.github.ajalt.clikt.parameters.groups.mutuallyExclusiveOptions
import com.github.ajalt.clikt.parameters.groups.required
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.cli.view.SeasonPointsCalculatorConfigurationView
import tech.coner.trailer.io.service.SeasonPointsCalculatorConfigurationService
import java.util.*

class SeasonPointsCalculatorGetCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "get",
    help = "Get a season points calculator"
) {

    override val diContext = diContextDataSession()
    private val service: SeasonPointsCalculatorConfigurationService by instance()
    private val view: SeasonPointsCalculatorConfigurationView by instance()

    sealed class Query {
        data class ById(val id: UUID) : Query()
        data class ByName(val name: String) : Query()
    }
    private val query: Query by mutuallyExclusiveOptions(
        option("--id")
            .convert { Query.ById(toUuid(it)) },
        option("--name")
            .convert { Query.ByName(it) }
    ).required()

    override suspend fun CoroutineScope.coRun() = diContext.use {
        val get = when(val query = query) {
            is Query.ById -> service.findById(query.id)
            is Query.ByName -> service.findByName(query.name)
        }
        echo(view.render(get))
    }
}