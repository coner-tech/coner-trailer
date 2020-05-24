package org.coner.trailer.eventresults

object TestComprehensiveResultsReports {

    val THSCC_2019_POINTS_1: ComprehensiveResultsReport
        get() = ComprehensiveResultsReport(
                overallResultsReports = listOf(
                        OverallResultsReport(
                                type = StandardResultsTypes.overallRawTime,
                                participantResults = listOf()
                        ),
                        OverallResultsReport(
                                type = StandardResultsTypes.overallHandicapTime,
                                participantResults = listOf()
                        )
                ),
                groupedResultsReports = TestGroupedResultsReports.LSCC_2019_POINTS_1
        )

}