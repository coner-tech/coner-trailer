package tech.coner.trailer.app.admin.command.rankingsort

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.groups.required
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel
import tech.coner.trailer.app.admin.di.use
import tech.coner.trailer.app.admin.util.clikt.toUuid
import tech.coner.trailer.app.admin.view.RankingSortView
import tech.coner.trailer.io.service.RankingSortService
import tech.coner.trailer.seasonpoints.RankingSort
import java.util.*

class RankingSortStepsAppendCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "steps-append",
    help = "Append a step to a Ranking Sort"
) {

    override val diContext = diContextDataSession()
    private val service: RankingSortService by instance()
    private val view: RankingSortView by instance()

    private val id: UUID by argument()
        .convert { toUuid(it) }

    private val step: RankingSortStepOptionGroup by rankingSortStepOptions()
        .required()

    override suspend fun CoroutineScope.coRun() = diContext.use {
        val rankingSort: RankingSort = service.findById(id)
        val update = rankingSort.copy(
            steps = rankingSort.steps.toMutableList().apply {
                add(step.step)
            }
        )
        service.update(update)
        echo(view.render(update))
    }
}