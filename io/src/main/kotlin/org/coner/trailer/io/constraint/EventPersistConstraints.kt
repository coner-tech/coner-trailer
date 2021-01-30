package org.coner.trailer.io.constraint

import org.coner.trailer.Event
import org.coner.trailer.datasource.snoozle.EventResource
import java.util.*

class EventPersistConstraints(
    private val resource: EventResource,
) : Constraint<Event>() {

    override fun assess(candidate: Event) {
        constrain(hasUniqueName(candidate.id, candidate.name)) { "Name is not unique" }
    }

    fun hasUniqueName(id: UUID, name: String) = resource.stream { it.id != id }
        .noneMatch { it.name == name }

}
