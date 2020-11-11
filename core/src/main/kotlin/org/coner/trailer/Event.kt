package org.coner.trailer

import java.io.File
import java.time.LocalDate
import java.util.*

class Event(
        val id: UUID = UUID.randomUUID(),
        val date: LocalDate,
        val name: String,
        val crispyFish: CrispyFishMetadata?
) {
    data class CrispyFishMetadata(
            val file: File,
            val forceRegistrationNumbersToPerson: Map<String, Person>
    )
}