package org.coner.trailer.datasource.snoozle.entity

import org.coner.snoozle.db.entity.Entity
import java.util.*

data class RankingSortEntity(
        val id: UUID = UUID.randomUUID(),
        val name: String,
        val steps: List<Step>
) : Entity<RankingSortEntity.Key> {

    data class Step(
            val type: Type,
            val paramInt1: Int? = null
    ) {
        enum class Type {
            ScoreDescending,
            PositionFinishCountDescending
        }
    }

    data class Key(val id: UUID) : org.coner.snoozle.db.Key
}