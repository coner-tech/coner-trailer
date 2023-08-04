package tech.coner.trailer.cli.command.seasonpointscalculator

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.pair
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.cli.view.SeasonPointsCalculatorConfigurationView
import tech.coner.trailer.io.service.RankingSortService
import tech.coner.trailer.io.service.SeasonPointsCalculatorConfigurationService
import tech.coner.trailer.seasonpoints.RankingSort
import tech.coner.trailer.seasonpoints.SeasonPointsCalculatorConfiguration
import java.util.*

class SeasonPointsCalculatorSetCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "set",
    help = "Set a season points calculator"
) {

    override val diContext = diContextDataSession()
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

    override suspend fun CoroutineScope.coRun() = diContext.use {
        val current = service.findById(id)
        val update = SeasonPointsCalculatorConfiguration(
            id = current.id,
            name = name ?: current.name,
            eventResultsTypeToEventPointsCalculator = when {
                resultsTypeKeyToEventPointsCalculatorNamed.isNotEmpty() -> mapper.fromParameter(
                    resultsTypeKeyToEventPointsCalculatorNamed
                )
                else -> current.eventResultsTypeToEventPointsCalculator
            },
            rankingSort = rankingSortNamed ?: current.rankingSort
        )
        service.update(update)
        echo(view.render(update))
    }
}