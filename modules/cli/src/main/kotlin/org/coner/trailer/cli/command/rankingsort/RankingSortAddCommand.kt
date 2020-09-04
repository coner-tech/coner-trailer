package org.coner.trailer.cli.command.rankingsort

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.parameters.groups.*
import com.github.ajalt.clikt.parameters.options.*
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.io.service.RankingSortService
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
    private val service: RankingSortService by instance()

    private val id: UUID by option(hidden = true)
            .convert { toUuid(it) }
            .default(UUID.randomUUID())
    private val name: String by option()
            .required()
    private val step: RankingSortStepOptionGroup by option()
            .groupSwitch(
                    "--score-descending" to RankingSortStepOptionGroup.ScoreDescending,
                    "--position-finish-count-descending" to RankingSortStepOptionGroup.PositionFinishCountDescending(),
                    "--average-margin-of-victory-descending" to RankingSortStepOptionGroup.AverageMarginOfVictoryDescending
            )
            .required()

    override fun run() {

    }


}