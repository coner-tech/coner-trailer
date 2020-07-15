package org.coner.trailer.eventresults

object TestComprehensiveResultsReports {

    object Lscc2019 {

        val points1: ComprehensiveResultsReport
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
                    groupedResultsReports = TestGroupedResultsReports.Lscc2019.points1
            )
    }

    object LsccTieBreaking {
        val points1: ComprehensiveResultsReport
            get() = ComprehensiveResultsReport(
                    overallResultsReports = listOf(
                            OverallResultsReport(
                                    type = StandardResultsTypes.overallRawTime,
                                    participantResults = emptyList()
                            ),
                            OverallResultsReport(
                                    type = StandardResultsTypes.overallHandicapTime,
                                    participantResults = emptyList()
                            )
                    ),
                    groupedResultsReports = TestGroupedResultsReports.LsccTieBreaking.points1
            )
        val points2: ComprehensiveResultsReport get() = ComprehensiveResultsReport(
                overallResultsReports = listOf(
                        OverallResultsReport(
                                type = StandardResultsTypes.overallRawTime,
                                participantResults = emptyList()
                        ),
                        OverallResultsReport(
                                type = StandardResultsTypes.overallHandicapTime,
                                participantResults = emptyList()
                        )
                ),
                groupedResultsReports = TestGroupedResultsReports.LsccTieBreaking.points2
        )
    }

    object Olscc2019 {

        val points1: ComprehensiveResultsReport
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
                    groupedResultsReports = TestGroupedResultsReports.Olscc2019.points1
            )
    }

}