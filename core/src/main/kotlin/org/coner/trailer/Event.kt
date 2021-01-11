package org.coner.trailer

import java.time.LocalDate
import java.util.*

data class Event(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val date: LocalDate,
    val lifecycle: Lifecycle,
    val crispyFish: CrispyFishMetadata?
) {
    data class CrispyFishMetadata(
            val eventControlFile: String,
            val classDefinitionFile: String,
            val forcePeople: Map<Participant.Signage, Person>
    )

    enum class Lifecycle {
        CREATE,
        PRE,
        ACTIVE,
        POST,
        FINAL
    }
}