package tech.coner.trailer.app.admin.view

import com.github.ajalt.clikt.output.CliktConsole
import tech.coner.trailer.seasonpoints.EventPointsCalculator

class EventPointsCalculatorView(
        override val console: CliktConsole
) : BaseCollectionView<EventPointsCalculator>() {

    override fun render(model: EventPointsCalculator) = """
        |${model.name}
        |    ID: ${model.id}
        |    Did Not Finish Points: ${model.didNotFinishPoints}
        |    Did Not Start Points: ${model.didNotStartPoints}
        |    Position to Points: 
        |${render(model.positionToPoints)}
        |    Default Points: ${model.defaultPoints}
    """.trimMargin()

    private fun render(positionToPoints: Map<Int, Int>): String {
        return positionToPoints
                .toSortedMap()
                .map {
                    """
                        |        ${it.key} => ${it.value}
                    """.trimMargin()
                }
                .joinToString(separator = console.lineSeparator)
    }
}