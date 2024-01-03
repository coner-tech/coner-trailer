package tech.coner.trailer.app.admin.view

import com.github.ajalt.mordant.terminal.Terminal
import tech.coner.trailer.eventresults.EventResultsType
import tech.coner.trailer.seasonpoints.EventPointsCalculator
import tech.coner.trailer.seasonpoints.SeasonPointsCalculatorConfiguration

class SeasonPointsCalculatorConfigurationView(
    override val terminal: Terminal
) : BaseCollectionView<SeasonPointsCalculatorConfiguration>() {

    override fun render(model: SeasonPointsCalculatorConfiguration): String {
        return """
            |${model.name}
            |   ID:
            |       ${model.id}
            |   Results Type Calculators:
            |${renderResultsTypeToEventPointsCalculator(model.eventResultsTypeToEventPointsCalculator)}
            |   Ranking Sort:
            |       ${model.rankingSort.name}
        """.trimMargin()
    }

    private fun renderResultsTypeToEventPointsCalculator(model: Map<EventResultsType, EventPointsCalculator>): String {
        return model.map { """ 
            |       ${it.key.key} => ${it.value.name}
        """.trimMargin()
        }.joinToString()
    }
}