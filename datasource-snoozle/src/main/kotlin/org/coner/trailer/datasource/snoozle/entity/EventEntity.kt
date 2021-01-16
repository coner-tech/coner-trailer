package org.coner.trailer.datasource.snoozle.entity

import org.coner.snoozle.db.entity.Entity
import java.time.LocalDate
import java.util.*

data class EventEntity(
        val id: UUID,
        val name: String,
        val date: LocalDate,
        val lifecycle: String,
        val crispyFish: CrispyFishMetadata?
) : Entity<EventEntity.Key> {

    data class CrispyFishMetadata(
            val eventControlFile: String,
            val classDefinitionFile: String,
            val peopleMap: List<PersonMapEntry>
    )

    data class PersonMapEntry(
        val signage: ParticipantEntity.Signage,
        val firstName: String,
        val lastName: String,
        val personId: UUID
    )

    data class Key(val id: UUID) : org.coner.snoozle.db.Key
}