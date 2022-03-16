package org.coner.trailer.datasource.snoozle.entity

import tech.coner.snoozle.db.entity.Entity
import java.time.LocalDate
import java.util.*

data class EventEntity(
    val id: UUID,
    val name: String,
    val date: LocalDate,
    val lifecycle: String,
    val crispyFish: CrispyFishMetadata?,
    val motorsportReg: MotorsportRegMetadata?,
    val policyId: UUID,
) : Entity<EventEntity.Key> {

    data class CrispyFishMetadata(
        val eventControlFile: String,
        val classDefinitionFile: String,
        val peopleMap: List<PersonMapEntry>
    )

    data class PersonMapEntry(
        val classing: ParticipantEntity.Classing,
        val number: String,
        val firstName: String,
        val lastName: String,
        val personId: UUID
    )

    data class MotorsportRegMetadata(
        val id: String
    )

    data class Key(val id: UUID) : tech.coner.snoozle.db.Key

}