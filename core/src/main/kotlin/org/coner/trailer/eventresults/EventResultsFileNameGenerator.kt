package org.coner.trailer.eventresults

import org.coner.trailer.Event

class EventResultsFileNameGenerator {

    fun build(event: Event, type: EventResultsType, extension: String): String {
        return "${event.date} ${event.name} ${type.key}.${extension}".toLowerCase()
    }
}