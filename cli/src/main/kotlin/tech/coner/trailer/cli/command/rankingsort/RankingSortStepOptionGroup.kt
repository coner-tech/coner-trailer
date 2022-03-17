package tech.coner.trailer.cli.command.rankingsort

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.groups.groupSwitch
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.int
import tech.coner.trailer.seasonpoints.RankingSort

fun CliktCommand.rankingSortStepOptions() = option()
        .groupSwitch(
                "--score-descending" to RankingSortStepOptionGroup.ScoreDescending,
                "--position-finish-count-descending" to RankingSortStepOptionGroup.PositionFinishCountDescending(),
        )

sealed class RankingSortStepOptionGroup : OptionGroup() {

    abstract val step: RankingSort.Step
    object ScoreDescending : RankingSortStepOptionGroup() {
        override val step: RankingSort.Step
            get() = RankingSort.Step.ScoreDescending()
    }
    class PositionFinishCountDescending : RankingSortStepOptionGroup() {
        val position: Int by option()
                .int()
                .required()
        override val step: RankingSort.Step
            get() = RankingSort.Step.PositionFinishCountDescending(
                    position = position
            )
    }
}