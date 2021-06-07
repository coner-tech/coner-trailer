package org.coner.trailer.eventresults

object TestComprehensiveResultsReports {

    object Lscc2019 {

        val points1: ComprehensiveResultsReport
            get() = ComprehensiveResultsReport(
                overallResultsReports = listOf(
                    OverallResultsReport(
                        type = StandardResultsTypes.raw,
                        participantResults = listOf(),
                        runCount = 5
                    ),
                    OverallResultsReport(
                        type = StandardResultsTypes.pax,
                        participantResults = listOf(),
                        runCount = 5
                    )
                ),
                groupedResultsReports = TestGroupedResultsReports.Lscc2019.points1
            )
    }

    object LsccTieBreaking {
        val points1: ComprehensiveResultsReport
            get() = ComprehensiveResultsReport(
                overallResultsReports = listOf(
                    OverallResultsReport(
                        type = StandardResultsTypes.raw,
                        participantResults = emptyList(),
                        runCount = 1
                    ),
                    OverallResultsReport(
                        type = StandardResultsTypes.pax,
                        participantResults = emptyList(),
                        runCount = 1
                    )
                ),
                groupedResultsReports = TestGroupedResultsReports.LsccTieBreaking.points1
            )
        val points2: ComprehensiveResultsReport get() = ComprehensiveResultsReport(
            overallResultsReports = listOf(
                OverallResultsReport(
                    type = StandardResultsTypes.raw,
                    participantResults = emptyList(),
                    runCount = 1
                ),
                OverallResultsReport(
                    type = StandardResultsTypes.pax,
                    participantResults = emptyList(),
                    runCount = 1
                )
            ),
            groupedResultsReports = TestGroupedResultsReports.LsccTieBreaking.points2
        )
    }
}