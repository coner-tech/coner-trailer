package tech.coner.trailer.cli.command.rankingsort

import com.github.ajalt.clikt.parameters.groups.mutuallyExclusiveOptions
import com.github.ajalt.clikt.parameters.groups.required
import com.github.ajalt.clikt.parameters.groups.single
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.cli.view.RankingSortView
import tech.coner.trailer.io.service.RankingSortService
import java.util.*

class RankingSortGetCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "get",
    help = "Get a ranking sort"
) {

    override val diContext = diContextDataSession()
    private val service: RankingSortService by instance()
    private val view: RankingSortView by instance()

    sealed class Query {
        data class ById(val id: UUID) : Query()
        data class ByName(val name: String) : Query()
    }
    private val query: Query by mutuallyExclusiveOptions(
        option("--id", help = "Get by ID")
            .convert { Query.ById(id = toUuid(it)) },
        option("--name", help = "Get by name")
            .convert { Query.ByName(name = it) }
    ).single().required()

    override suspend fun coRun() = diContext.use {
        val get = when(val query = this.query) {
            is Query.ById -> service.findById(query.id)
            is Query.ByName -> service.findByName(query.name)
        }
        echo(view.render(get ?: return@use))
    }
}