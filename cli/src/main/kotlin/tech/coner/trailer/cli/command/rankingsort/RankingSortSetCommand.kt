package tech.coner.trailer.cli.command.rankingsort

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.options.option
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.cli.view.RankingSortView
import tech.coner.trailer.io.service.RankingSortService
import tech.coner.trailer.seasonpoints.RankingSort
import java.util.*

class RankingSortSetCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "set",
    help = "Set a ranking sort"
) {

    override val diContext = diContextDataSession()
    private val service: RankingSortService by instance()
    private val view: RankingSortView by instance()

    private val id: UUID by argument()
        .convert { toUuid(it) }
    private val name: String? by option()
    private val step: RankingSortStepOptionGroup? by rankingSortStepOptions()

    override suspend fun CoroutineScope.coRun() = diContext.use {
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