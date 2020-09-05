package org.coner.trailer.io.mapper

import org.coner.trailer.datasource.snoozle.entity.RankingSortEntity
import org.coner.trailer.seasonpoints.RankingSort

class RankingSortMapper {

    fun fromSnoozle(snoozle: RankingSortEntity): RankingSort {
        return RankingSort(
                id = snoozle.id,
                name = snoozle.name,
                steps = snoozle.steps.map { when (it.type) {
                    RankingSortEntity.Step.Type.ScoreDescending -> RankingSort.Step.ScoreDescending
                    RankingSortEntity.Step.Type.PositionFinishCountDescending -> RankingSort.Step.PositionFinishCountDescending(
                            position = requireNotNull(it.paramInt1) { "${it.type} requires paramInt1 for position" }
                    )
                    RankingSortEntity.Step.Type.AverageMarginOfVictoryDescending -> RankingSort.Step.AverageMarginOfVictoryDescending
                } }
        )
    }

    fun toSnoozle(core: RankingSort): RankingSortEntity {
        return RankingSortEntity(
                id = core.id,
                name = core.name,
                steps = core.steps.map { when (it) {
                    RankingSort.Step.ScoreDescending -> {
                        RankingSortEntity.Step(
                                type = RankingSortEntity.Step.Type.ScoreDescending
                        )
                    }
                    is RankingSort.Step.PositionFinishCountDescending -> {
                        RankingSortEntity.Step(
                                type = RankingSortEntity.Step.Type.PositionFinishCountDescending,
                                paramInt1 = it.position
                        )
                    }
                    RankingSort.Step.AverageMarginOfVictoryDescending -> {
                        RankingSortEntity.Step(
                                type = RankingSortEntity.Step.Type.AverageMarginOfVictoryDescending
                        )
                    }
                } }
        )
    }
}