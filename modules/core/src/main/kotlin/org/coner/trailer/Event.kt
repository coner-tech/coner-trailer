package org.coner.trailer

import java.time.LocalDate
import java.util.*

class Event(
        val id: UUID = UUID.randomUUID(),
        val date: LocalDate,
        val name: String
) {
}