package org.coner.trailer.cli.view

import com.github.ajalt.clikt.output.CliktConsole
import org.coner.trailer.eventresults.ResultsType
import org.coner.trailer.seasonpoints.EventPointsCalculator
import org.coner.trailer.seasonpoints.SeasonPointsCalculatorConfiguration

class SeasonPointsCalculatorConfigurationView(
        override val console: CliktConsole
) : CollectionView<SeasonPointsCalculatorConfiguration> {

    override fun render(model: SeasonPointsCalculatorConfiguration): String {
        return """
            |${model.name}
            |   ID:
            |       ${model.id}
            |   Results Type Calculators:
            |${renderResultsTypeToEventPointsCalculator(model.resultsTypeToEventPointsCalculator)}
            |   Ranking Sort:
            |       ${model.rankingSort.name}
        """.trimMargin()
    }

    private fun renderResultsTypeToEventPointsCalculator(model: Map<ResultsType, EventPointsCalculator>): String {
        return model.map { """ 
            |       ${it.key.key} => ${it.value.name}
        """.trimMargin()
        }.joinToString()
    }
}