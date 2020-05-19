package org.coner.trailer.eventresults

class EventComprehensiveResultsReport(
        private val rawTimeResults: EventOverallResultsReport,
        private val handicapTimeResults: EventOverallResultsReport,
        private val classedResults: GroupedResultsReport
) {
}