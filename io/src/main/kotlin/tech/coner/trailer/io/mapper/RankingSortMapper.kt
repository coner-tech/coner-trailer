package tech.coner.trailer.io.mapper

import tech.coner.trailer.datasource.snoozle.entity.RankingSortEntity
import tech.coner.trailer.seasonpoints.RankingSort

class RankingSortMapper {

    fun fromSnoozle(snoozle: RankingSortEntity): RankingSort {
        return RankingSort(
                id = snoozle.id,
                name = snoozle.name,
                steps = snoozle.steps.map { step -> when (step.type) {
                    RankingSortEntity.Step.Type.ScoreDescending -> {
                        RankingSort.Step.ScoreDescending()
                    }
                    RankingSortEntity.Step.Type.PositionFinishCountDescending -> {
                        RankingSort.Step.PositionFinishCountDescending(
                                position = requireNotNull(step.paramInt1) { "${step.type} requires paramInt1 for position" }
                        )
                    }
                } }
        )
    }

    fun toSnoozle(core: RankingSort): RankingSortEntity {
        return RankingSortEntity(
                id = core.id,
                name = core.name,
                steps = core.steps.map { when (it) {
                    is RankingSort.Step.ScoreDescending -> {
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
                } }
        )
    }
}