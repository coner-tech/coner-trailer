package org.coner.trailer.io.mapper

import org.coner.trailer.datasource.snoozle.entity.RankingSortEntity
import org.coner.trailer.seasonpoints.RankingSort

class RankingSortMapper {

    fun fromSnoozle(snoozle: RankingSortEntity): RankingSort {
        return RankingSort(
                id = snoozle.id,
                steps = snoozle.steps.map(::fromSnoozleStep)
        )
    }

    fun toSnoozle(core: RankingSort): RankingSortEntity {
        return RankingSortEntity(
                id = core.id,
                steps = core.steps.map(::toSnoozleStep)
        )
    }

    private fun fromSnoozleStep(step: RankingSortEntity.Step): RankingSort.Step {
        return when (step.type) {
            "ScoreDescending" -> RankingSort.Step.ScoreDescending
            "PositionFinishCountDescending" -> {
                val position = requireNotNull(step.p1).toInt()
                RankingSort.Step.PositionFinishCountDescending(position)
            }
            "AverageMarginOfVictoryDescending" -> RankingSort.Step.AverageMarginOfVictoryDescending
            else -> throw IllegalArgumentException("Unrecognized step type: ${step.type}")
        }
    }

    private fun toSnoozleStep(step: RankingSort.Step): RankingSortEntity.Step {
        return when (step) {
            RankingSort.Step.ScoreDescending -> RankingSortEntity.Step(
                    type = "ScoreDescending"
            )
            is RankingSort.Step.PositionFinishCountDescending -> RankingSortEntity.Step(
                    type = "PositionFinishCountDescending",
                    p1 = step.position.toString()
            )
            RankingSort.Step.AverageMarginOfVictoryDescending -> RankingSortEntity.Step(
                    type = "AverageMarginOfVictoryDescending"
            )
        }
    }

}