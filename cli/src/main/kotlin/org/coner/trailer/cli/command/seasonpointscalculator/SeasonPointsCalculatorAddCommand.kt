package org.coner.trailer.cli.command.seasonpointscalculator

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.*
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

class SeasonPointsCalculatorAddCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
        name = "add",
        help = "Add a season points calculator"
), DIAware {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }
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

    override fun run() = diContext.use {
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