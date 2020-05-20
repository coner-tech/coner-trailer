package org.coner.trailer.eventresults

class EventComprehensiveResultsReport(
        val rawTimeResults: EventOverallResultsReport,
        val handicapTimeResults: EventOverallResultsReport,
        val groupedResults: GroupedResultsReport
) {
}