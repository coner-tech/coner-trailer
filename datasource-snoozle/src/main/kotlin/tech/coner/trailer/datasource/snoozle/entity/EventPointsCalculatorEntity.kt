package tech.coner.trailer.datasource.snoozle.entity

import tech.coner.snoozle.db.entity.Entity
import java.util.*

data class EventPointsCalculatorEntity(
        val id: UUID = UUID.randomUUID(),
        val name: String,
        val positionToPoints: Map<Int, Int>,
        val didNotFinishPoints: Int?,
        val didNotStartPoints: Int?,
        val defaultPoints: Int
) : Entity<EventPointsCalculatorEntity.Key> {

    data class Key(val id: UUID) : tech.coner.snoozle.db.Key
}