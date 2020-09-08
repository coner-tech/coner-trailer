package org.coner.trailer.cli.view

import com.github.ajalt.clikt.output.CliktConsole
import org.coner.trailer.eventresults.ResultsType
import org.coner.trailer.seasonpoints.ParticipantEventResultPointsCalculator
import org.coner.trailer.seasonpoints.SeasonPointsCalculatorConfiguration

class SeasonPointsCalculatorConfigurationView(
        override val console: CliktConsole,
        private val participantEventResultPointsCalculatorView: ParticipantEventResultPointsCalculatorView
) : CollectionView<SeasonPointsCalculatorConfiguration> {

    override fun render(model: SeasonPointsCalculatorConfiguration): String {
        return """
            |${model.name}
            |   ID:     ${model.name}
            |   Results Type Calculators:
            |${renderResultsTypeToCalculatorMap(model.resultsTypeToCalculatorMap)}
        """.trimMargin()
    }

    private fun renderResultsTypeToCalculatorMap(model: Map<ResultsType, ParticipantEventResultPointsCalculator>): String {
        return model.map(::render)
                .joinToString(prefix = " ".repeat(12))
    }

    private fun render(model: Map.Entry<ResultsType, ParticipantEventResultPointsCalculator>): String {
        return """
            |${model.key.title} (${model.key.key}) =>
            | ${participantEventResultPointsCalculatorView.render(model.value)}
            | 
        """.trimMargin()
    }

}