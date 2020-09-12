package org.coner.trailer.cli.command.seasonpointscalculator

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.parameters.options.*
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.cli.view.SeasonPointsCalculatorConfigurationView
import org.coner.trailer.io.service.RankingSortService
import org.coner.trailer.io.service.SeasonPointsCalculatorConfigurationService
import org.coner.trailer.seasonpoints.RankingSort
import org.coner.trailer.seasonpoints.SeasonPointsCalculatorConfiguration
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.util.*

class SeasonPointsCalculatorAddCommand(
        di: DI,
        useConsole: CliktConsole
) : CliktCommand(
        name = "add",
        help = "Add a season points calculator"
), DIAware {

    init {
        context {
            console = useConsole
        }
    }

    override val di: DI by findOrSetObject { di }

    private val mapper: SeasonPointsCalculatorParameterMapper by instance()
    private val rankingSortService: RankingSortService by instance()
    private val service: SeasonPointsCalculatorConfigurationService by instance()
    private val view: SeasonPointsCalculatorConfigurationView by instance()

    private val id: UUID by option(hidden = true)
            .convert { toUuid(it) }
            .default(UUID.randomUUID())
    private val name: String by option()
            .required()
    private val resultsTypeKeyToParticipantEventResultPointsCalculatorNamed: List<Pair<String, String>> by option(
            metavar = "KEY NAME..."
    )
            .pair()
            .multiple()
    private val rankingSortNamed: RankingSort by option(metavar = "NAME")
            .convert {
                rankingSortService.findByName(it)
                        ?: fail("No ranking sort found with name: $it")
            }
            .required()

    override fun run() {
        val create = SeasonPointsCalculatorConfiguration(
                id = id,
                name = name,
                resultsTypeToParticipantEventResultPointsCalculator = mapper.fromParameter(resultsTypeKeyToParticipantEventResultPointsCalculatorNamed),
                rankingSort = rankingSortNamed
        )
        service.create(create)
        echo(view.render(create))
    }
}