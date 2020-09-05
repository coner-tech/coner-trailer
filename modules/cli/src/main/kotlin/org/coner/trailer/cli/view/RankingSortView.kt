package org.coner.trailer.cli.view

import com.github.ajalt.clikt.output.CliktConsole
import org.coner.trailer.seasonpoints.RankingSort

class RankingSortView(override val console: CliktConsole) : CollectionView<RankingSort> {
    override fun render(model: RankingSort) = """
        ${model.name}
            ID:     ${model.id}
            Steps:  
                    ${render(model.steps)}
    """.trimIndent()

    private fun render(steps: List<RankingSort.Step>): String {
        return steps.map {
            when (it) {
                RankingSort.Step.ScoreDescending -> "Score Descending"
                is RankingSort.Step.PositionFinishCountDescending -> "Position (${it.position}) Finish Count"
                RankingSort.Step.AverageMarginOfVictoryDescending -> "Average Margin of Victory Descending"
            }
        }.joinToString(separator = console.lineSeparator) { it.prependIndent(" ".repeat(8)) }
    }
}