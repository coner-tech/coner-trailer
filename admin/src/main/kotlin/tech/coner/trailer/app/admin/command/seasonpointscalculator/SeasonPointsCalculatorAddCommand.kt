package tech.coner.trailer.app.admin.command.seasonpointscalculator

import com.github.ajalt.clikt.parameters.options.*
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel
import tech.coner.trailer.app.admin.di.use
import tech.coner.trailer.app.admin.util.clikt.toUuid
import tech.coner.trailer.app.admin.view.SeasonPointsCalculatorConfigurationView
import tech.coner.trailer.io.service.RankingSortService
import tech.coner.trailer.io.service.SeasonPointsCalculatorConfigurationService
import tech.coner.trailer.seasonpoints.RankingSort
import tech.coner.trailer.seasonpoints.SeasonPointsCalculatorConfiguration
import java.util.*

class SeasonPointsCalculatorAddCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "add",
    help = "Add a season points calculator"
) {

    override val diContext = diContextDataSession()
    private val mapper: SeasonPointsCalculatorParameterMapper by instance()
    private val rankingSortService: RankingSortService by instance()
    private val service: SeasonPointsCalculatorConfigurationService by instance()
    private val view: SeasonPointsCalculatorConfigurationView by instance()

    private val id: UUID by option(hidden = true)
        .convert { toUuid(it) }
        .default(UUID.randomUUID())
    private val name: String by option()
        .required()
    private val resultsTypeKeyToEventPointsCalculatorNamed: List<Pair<String, String>> by option(
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

    override suspend fun CoroutineScope.coRun() = diContext.use {
        val create = SeasonPointsCalculatorConfiguration(
            id = id,
            name = name,
            eventResultsTypeToEventPointsCalculator = mapper.fromParameter(resultsTypeKeyToEventPointsCalculatorNamed),
            rankingSort = rankingSortNamed
        )
        service.create(create)
        echo(view.render(create))
    }
}