package tech.coner.trailer.cli.command.rankingsort

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.options.option
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.cli.view.RankingSortView
import tech.coner.trailer.io.service.RankingSortService
import tech.coner.trailer.seasonpoints.RankingSort
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance
import java.util.*

class RankingSortSetCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
        name = "set",
        help = "Set a ranking sort"
), DIAware {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }
    private val service: RankingSortService by instance()
    private val view: RankingSortView by instance()

    private val id: UUID by argument()
            .convert { toUuid(it) }
    private val name: String? by option()
    private val step: RankingSortStepOptionGroup? by rankingSortStepOptions()

    override fun run() = diContext.use {
        val old = service.findById(id)
        val update = RankingSort(
                id = old.id,
                name = name ?: old.name,
                steps = step?.let { listOf(it.step) } ?: old.steps
        )
        service.update(update)
        echo(view.render(update))
    }
}