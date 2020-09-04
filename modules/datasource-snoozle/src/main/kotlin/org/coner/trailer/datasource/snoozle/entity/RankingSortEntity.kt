package org.coner.trailer.datasource.snoozle.entity

import org.coner.snoozle.db.entity.Entity
import java.util.*

data class RankingSortEntity(
        val id: UUID = UUID.randomUUID(),
        val scoreDescendingSteps: List<IndexOnlyStep>,
        val positionFinishCountDescendingSteps: List<PositionFinishCountDescendingStep>,
        val averageMarginOfVictoryDescendingSteps: List<IndexOnlyStep>
) : Entity<RankingSortEntity.Key> {

    data class IndexOnlyStep(
            val index: Int
    )

    data class PositionFinishCountDescendingStep(
            val index: Int,
            val position: Int
    )

    data class Key(val id: UUID) : org.coner.snoozle.db.Key
}