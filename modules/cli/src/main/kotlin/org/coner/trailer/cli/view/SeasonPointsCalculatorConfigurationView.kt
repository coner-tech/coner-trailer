package org.coner.trailer.cli.view

import com.github.ajalt.clikt.output.CliktConsole
import org.coner.trailer.eventresults.ResultsType
import org.coner.trailer.seasonpoints.ParticipantEventResultPointsCalculator
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
            |${renderResultsTypeToParticipantEventResultsCalculator(model.resultsTypeToParticipantEventResultPointsCalculator)}
            |   Ranking Sort:
            |       ${model.rankingSort.name}
        """.trimMargin()
    }

    private fun renderResultsTypeToParticipantEventResultsCalculator(model: Map<ResultsType, ParticipantEventResultPointsCalculator>): String {
        return model.map { """ 
            |       ${it.key.key} => ${it.value.name}
        """.trimMargin()
        }.joinToString()
    }
}