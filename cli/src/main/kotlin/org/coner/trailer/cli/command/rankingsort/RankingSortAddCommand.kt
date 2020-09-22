package org.coner.trailer.cli.command.rankingsort

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.parameters.groups.required
import com.github.ajalt.clikt.parameters.options.*
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.cli.view.RankingSortView
import org.coner.trailer.io.constraint.RankingSortPersistConstraints
import org.coner.trailer.io.service.RankingSortService
import org.coner.trailer.seasonpoints.RankingSort
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.util.*

class RankingSortAddCommand(
        di: DI,
        useConsole: CliktConsole
) : CliktCommand(
        name = "add"
), DIAware {

    init {
        context {
            console = useConsole
        }
    }

    override val di: DI by findOrSetObject { di }
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

    override fun run() {
        val create = RankingSort(
                id = id,
                name = name,
                steps = listOf(step.step)
        )
        service.create(create)
        echo(view.render(create))
    }


}