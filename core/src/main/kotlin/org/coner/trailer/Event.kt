package org.coner.trailer

import java.time.LocalDate
import java.util.*

class Event(
        val id: UUID = UUID.randomUUID(),
        val name: String,
        val date: LocalDate,
        val crispyFish: CrispyFishMetadata?
) {
    data class CrispyFishMetadata(
            val eventControlFile: String,
            val classDefinitionFile: String,
            val forceParticipantSignageToPerson: Map<Participant.Signage, Person>
    )
}