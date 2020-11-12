package org.coner.trailer.datasource.snoozle.entity

import org.coner.snoozle.db.entity.Entity
import org.coner.trailer.Event
import java.time.LocalDate
import java.util.*

data class EventEntity(
        val id: UUID,
        val name: String,
        val date: LocalDate,
        val crispyFish: CrispyFishMetadata?
) : Entity<EventEntity.Key> {

    data class CrispyFishMetadata(
            val eventControlFile: String,
            val forceParticipantSignageToPersonId: Map<Particip, UUID>
    )

    data class Key(val id: UUID) : org.coner.snoozle.db.Key
}