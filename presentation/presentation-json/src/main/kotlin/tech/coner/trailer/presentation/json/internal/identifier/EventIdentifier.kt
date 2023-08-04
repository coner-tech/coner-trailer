package tech.coner.trailer.presentation.json.internal.identifier

import tech.coner.trailer.Event
import java.time.LocalDate
import java.util.*

class EventIdentifier(val key: UUID, val name: String, val date: LocalDate) {
    constructor(event: Event) : this(key = event.id, name = event.name, date = event.date)
}