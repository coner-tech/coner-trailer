package org.coner.trailer.eventresults

object TestComprehensiveResultsReports {

    val lscc2019Points1: ComprehensiveResultsReport
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
                groupedResultsReports = TestGroupedResultsReports.lscc2019Points1
        )

    val olscc2019Points1: ComprehensiveResultsReport
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
                groupedResultsReports = TestGroupedResultsReports.olscc2019Points1
        )

}