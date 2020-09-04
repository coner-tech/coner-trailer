package org.coner.trailer.cli.command.rankingsort

import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.int

sealed class RankingSortStepOptionGroup : OptionGroup() {
    object ScoreDescending : RankingSortStepOptionGroup()
    class PositionFinishCountDescending : RankingSortStepOptionGroup() {
        val position: Int by option()
                .int()
                .required()
    }
    object AverageMarginOfVictoryDescending : RankingSortStepOptionGroup()
}