package org.coner.trailer.cli.command.seasonpointscalculator

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
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

class SeasonPointsCalculatorSetCommand(
        di: DI,
        useConsole: CliktConsole
) : CliktCommand(
        name = "set",
        help = "Set a season points calculator"
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

    private val id: UUID by argument()
            .convert { toUuid(it) }

    private val name: String? by option()
    private val resultsTypeKeyToEventPointsCalculatorNamed: List<Pair<String, String>> by option(
            metavar = "KEY NAME..."
    ).pair().multiple()
    private val rankingSortNamed: RankingSort? by option(metavar = "NAME")
            .convert {
                rankingSortService.findByName(it)
                        ?: fail("No ranking sort found with name: $it")
            }

    override fun run() {
        val current = service.findById(id)
        val update = SeasonPointsCalculatorConfiguration(
                id = current.id,
                name = name ?: current.name,
                resultsTypeToEventPointsCalculator = when {
                    resultsTypeKeyToEventPointsCalculatorNamed.isNotEmpty() -> mapper.fromParameter(resultsTypeKeyToEventPointsCalculatorNamed)
                    else -> current.resultsTypeToEventPointsCalculator
                },
                rankingSort = rankingSortNamed ?: current.rankingSort
        )
        service.update(update)
        echo(view.render(update))
    }
}