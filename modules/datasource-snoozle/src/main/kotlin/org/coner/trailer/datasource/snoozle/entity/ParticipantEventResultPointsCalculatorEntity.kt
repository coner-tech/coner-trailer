package org.coner.trailer.datasource.snoozle.entity

import org.coner.snoozle.db.Key
import org.coner.snoozle.db.entity.Entity
import java.util.*

data class ParticipantEventResultPointsCalculatorEntity(
        val id: UUID = UUID.randomUUID(),
        val name: String,
        val positionToPoints: Map<Int, Int>,
        val didNotFinishPoints: Int?,
        val didNotStartPoints: Int?,
        val defaultPoints: Int
) : Entity<ParticipantEventResultPointsCalculatorEntity.Key> {

    data class Key(val id: UUID) : org.coner.snoozle.db.Key
}