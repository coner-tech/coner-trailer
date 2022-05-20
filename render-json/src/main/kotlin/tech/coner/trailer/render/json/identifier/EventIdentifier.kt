package tech.coner.trailer.render.json.identifier

import tech.coner.trailer.Event
import java.time.LocalDate
import java.util.*

class EventIdentifier(val id: UUID, val name: String, val date: LocalDate) {
    constructor(event: Event) : this(id = event.id, name = event.name, date = event.date)
}