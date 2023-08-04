package tech.coner.trailer.cli.command.rankingsort

import com.github.ajalt.clikt.parameters.groups.required
import com.github.ajalt.clikt.parameters.options.*
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.cli.view.RankingSortView
import tech.coner.trailer.io.constraint.RankingSortPersistConstraints
import tech.coner.trailer.io.service.RankingSortService
import tech.coner.trailer.seasonpoints.RankingSort
import java.util.*

class RankingSortAddCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "add"
) {

    override val diContext = diContextDataSession()
    private val constraints: RankingSortPersistConstraints by instance()
    private val service: RankingSortService by instance()
    private val view: RankingSortView by instance()

    private val id: UUID by option(hidden = true)
        .convert { toUuid(it) }
        .default(UUID.randomUUID())
    private val name: String by option()
        .required()
        .validate {
            if (!constraints.hasUniqueName(id, it))
                fail("Name must be unique")
        }
    private val step: RankingSortStepOptionGroup by rankingSortStepOptions()
        .required()

    override suspend fun CoroutineScope.coRun() = diContext.use {
        val create = RankingSort(
            id = id,
            name = name,
            steps = listOf(step.step)
        )
        service.create(create)
        echo(view.render(create))
    }


}