package tech.coner.trailer.app.admin.view

import com.github.ajalt.clikt.output.CliktConsole
import tech.coner.trailer.seasonpoints.RankingSort

class RankingSortView(override val console: CliktConsole) : BaseCollectionView<RankingSort>() {
    override fun render(model: RankingSort) = """
        |${model.name}
        |    ID:     ${model.id}
        |    Steps:
        |${render(model.steps)}
    """.trimMargin()

    private fun render(steps: List<RankingSort.Step>): String {
        return steps.map {
            when (it) {
                is RankingSort.Step.ScoreDescending -> "Score Descending"
                is RankingSort.Step.PositionFinishCountDescending -> "Position Finish Count Descending: ${it.position}"
            }
        }.joinToString(separator = console.lineSeparator) {
            "- $it".prependIndent(" ".repeat(12))
        }
    }
}