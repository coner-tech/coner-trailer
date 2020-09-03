package org.coner.trailer.cli.view

import com.github.ajalt.clikt.output.CliktConsole
import org.coner.trailer.seasonpoints.ParticipantEventResultPointsCalculator

class ParticipantEventResultPointsCalculatorView(
        override val console: CliktConsole
) : CollectionView<ParticipantEventResultPointsCalculator> {

    override fun render(model: ParticipantEventResultPointsCalculator) = """
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