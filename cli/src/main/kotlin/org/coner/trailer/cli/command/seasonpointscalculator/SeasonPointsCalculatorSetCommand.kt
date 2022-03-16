package org.coner.trailer.cli.command.seasonpointscalculator

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.pair
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.cli.di.use
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.cli.view.SeasonPointsCalculatorConfigurationView
import org.coner.trailer.io.service.RankingSortService
import org.coner.trailer.io.service.SeasonPointsCalculatorConfigurationService
import org.coner.trailer.seasonpoints.RankingSort
import org.coner.trailer.seasonpoints.SeasonPointsCalculatorConfiguration
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance
import java.util.*

class SeasonPointsCalculatorSetCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
        name = "set",
        help = "Set a season points calculator"
), DIAware {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }
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

    override fun run() = diContext.use {
        val current = service.findById(id)
        val update = SeasonPointsCalculatorConfiguration(
                id = current.id,
                name = name ?: current.name,
                eventResultsTypeToEventPointsCalculator = when {
                    resultsTypeKeyToEventPointsCalculatorNamed.isNotEmpty() -> mapper.fromParameter(resultsTypeKeyToEventPointsCalculatorNamed)
                    else -> current.eventResultsTypeToEventPointsCalculator
                },
                rankingSort = rankingSortNamed ?: current.rankingSort
        )
        service.update(update)
        echo(view.render(update))
    }
}