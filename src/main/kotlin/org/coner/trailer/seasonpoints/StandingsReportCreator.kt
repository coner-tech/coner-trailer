package org.coner.trailer.seasonpoints

import org.coner.trailer.eventresults.EventComprehensiveResultsReport

class StandingsReportCreator {
    fun create(params: CreationParameters): StandingsReport {
        TODO()
    }

    class CreationParameters(
            val eventNumberToEventComprehensiveResultsReport: Map<Int, EventComprehensiveResultsReport>
    ) {

    }
}