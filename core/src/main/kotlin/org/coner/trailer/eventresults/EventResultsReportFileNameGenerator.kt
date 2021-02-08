package org.coner.trailer.eventresults

import org.coner.trailer.Event

class EventResultsReportFileNameGenerator {

    fun build(event: Event, type: ResultsType, extension: String): String {
        return "${event.date} ${event.name} ${type.key}.${extension}".toLowerCase()
    }
}