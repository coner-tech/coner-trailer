package org.coner.trailer.seasonpoints

import org.coner.trailer.Grouping
import org.coner.trailer.eventresults.ComprehensiveResultsReport
import org.coner.trailer.eventresults.GroupedResultsReport

class StandingsReportCreator {

    fun createComprehensiveStandingsReport(params: ComprehensiveStandingsReportParameters): StandingsReport {
        TODO()
    }

    class ComprehensiveStandingsReportParameters(
            val eventNumberToComprehensiveResultsReport: Map<Int, ComprehensiveResultsReport>
    ) {

    }

    fun createGroupedStandingsSections(eventNumberToGroupedResultsReports: Map<Int, GroupedResultsReport>): List<StandingsReport.Section> {
        val groupsToSectionTitles: Map<Grouping, String> = eventNumberToGroupedResultsReports.values
                .flatMap { it.groupingsToResultsMap.keys }
                .distinct()
                .map { it to it.abbreviation }
                .toMap()
        return groupsToSectionTitles.map {
            StandingsReport.Section(
                    title = it.value,
                    standings = emptyList()
            )
        }
    }

}