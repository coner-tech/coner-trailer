package org.coner.trailer.io.mapper

import org.coner.trailer.datasource.snoozle.entity.RankingSortEntity
import org.coner.trailer.seasonpoints.RankingSort

class RankingSortMapper {

    fun fromSnoozle(snoozle: RankingSortEntity): RankingSort {
        return RankingSort(
                id = snoozle.id,
                steps = mutableListOf<RankingSort.Step>().apply {
                    snoozle.scoreDescendingSteps.forEach {
                        set(it.index, RankingSort.Step.ScoreDescending)
                    }
                    snoozle.positionFinishCountDescendingSteps.forEach {
                        set(it.index, RankingSort.Step.PositionFinishCountDescending(it.position))
                    }
                    snoozle.averageMarginOfVictoryDescendingSteps.forEach {
                        set(it.index, RankingSort.Step.AverageMarginOfVictoryDescending)
                    }
                }
        )
    }

    fun toSnoozle(core: RankingSort): RankingSortEntity {
        return RankingSortEntity(
                id = core.id,
                scoreDescendingSteps = core.steps.mapIndexedNotNull { index, step ->
                    when (step) {
                        is RankingSort.Step.ScoreDescending -> RankingSortEntity.IndexOnlyStep(index)
                        else -> null
                    }
                },
                positionFinishCountDescendingSteps = core.steps.mapIndexedNotNull { index, step ->
                    when (step) {
                        is RankingSort.Step.PositionFinishCountDescending -> {
                            RankingSortEntity.PositionFinishCountDescendingStep(
                                    index = index,
                                    position = step.position
                            )
                        }
                        else -> null
                    }
                },
                averageMarginOfVictoryDescendingSteps = core.steps.mapIndexedNotNull { index, step ->
                    when (step) {
                        is RankingSort.Step.AverageMarginOfVictoryDescending -> RankingSortEntity.IndexOnlyStep(index)
                        else -> null
                    }
                }
        )
    }
}