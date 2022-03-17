package tech.coner.trailer.eventresults

import tech.coner.trailer.Event

class EventResultsFileNameGenerator {

    fun build(event: Event, type: EventResultsType, extension: String): String {
        return "${event.date} ${event.name} ${type.key}.${extension}".toLowerCase()
    }
}