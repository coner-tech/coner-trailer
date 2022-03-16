package org.coner.trailer.cli.command.rankingsort

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.groups.mutuallyExclusiveOptions
import com.github.ajalt.clikt.parameters.groups.required
import com.github.ajalt.clikt.parameters.groups.single
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.cli.di.use
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.cli.view.RankingSortView
import org.coner.trailer.io.service.RankingSortService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance
import java.util.*

class RankingSortGetCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
        name = "get",
        help = "Get a ranking sort"
), DIAware {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }
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

    override fun run() = diContext.use {
        val get = when(val query = this.query) {
            is Query.ById -> service.findById(query.id)
            is Query.ByName -> service.findByName(query.name)
        }
        echo(view.render(get ?: return@use))
    }
}