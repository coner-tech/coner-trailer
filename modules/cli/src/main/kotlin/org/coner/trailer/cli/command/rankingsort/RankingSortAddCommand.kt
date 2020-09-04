package org.coner.trailer.cli.command.rankingsort

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.parameters.groups.*
import com.github.ajalt.clikt.parameters.options.*
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.seasonpoints.RankingSort
import org.kodein.di.DI
import org.kodein.di.DIAware
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

    private val id: UUID by option(hidden = true)
            .convert { toUuid(it) }
            .default(UUID.randomUUID())
    private val name: String by option()
            .required()
    sealed class StepOptionGroup : OptionGroup() {
        object ScoreDescending : StepOptionGroup()
    }
    private val step0: StepOptionGroup by option()
            .groupChoice(
                    "--score-descending" to StepOptionGroup.ScoreDescending
            )
            .required()

    override fun run() {
        TODO("Not yet implemented")
    }


}