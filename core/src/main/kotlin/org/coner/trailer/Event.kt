package org.coner.trailer

import java.time.LocalDate
import java.util.*

data class Event(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val date: LocalDate,
    val lifecycle: Lifecycle,
    val crispyFish: CrispyFishMetadata?,
    val motorsportReg: MotorsportRegMetadata?,
    val policy: Policy,
) {

    init {
        if (policy.authoritativeRunSource == Policy.RunSource.CrispyFish) {
            checkNotNull(crispyFish) {
                "Event policy defines authoritative run source as crispy fish, but event lacks crispy fish metadata"
            }
        }
    }

    data class CrispyFishMetadata(
            val eventControlFile: String,
            val classDefinitionFile: String,
            val peopleMap: Map<PeopleMapKey, Person>
    ) {

        data class PeopleMapKey(
            val grouping: Grouping,
            val number: String,
            val firstName: String,
            val lastName: String
        )
    }

    data class MotorsportRegMetadata(
        val id: String
    )

    enum class Lifecycle {
        CREATE,
        PRE,
        ACTIVE,
        POST,
        FINAL
    }
}