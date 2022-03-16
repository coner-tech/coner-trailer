package org.coner.trailer.eventresults

object TestComprehensiveEventResults {

    object Lscc2019 {

        val points1: ComprehensiveEventResults
            get() = ComprehensiveEventResults(
                runCount = 5,
                overallEventResults = listOf(
                    OverallEventResults(
                        type = StandardEventResultsTypes.raw,
                        participantResults = listOf(),
                        runCount = 5
                    ),
                    OverallEventResults(
                        type = StandardEventResultsTypes.pax,
                        participantResults = listOf(),
                        runCount = 5
                    )
                ),
                groupEventResults = TestClazzEventResults.Lscc2019.points1
            )
    }

    object LsccTieBreaking {
        val points1: ComprehensiveEventResults
            get() = ComprehensiveEventResults(
                runCount = 1,
                overallEventResults = listOf(
                    OverallEventResults(
                        type = StandardEventResultsTypes.raw,
                        participantResults = emptyList(),
                        runCount = 1
                    ),
                    OverallEventResults(
                        type = StandardEventResultsTypes.pax,
                        participantResults = emptyList(),
                        runCount = 1
                    )
                ),
                groupEventResults = TestClazzEventResults.LsccTieBreaking.points1
            )
        val points2: ComprehensiveEventResults get() = ComprehensiveEventResults(
            runCount = 1,
            overallEventResults = listOf(
                OverallEventResults(
                    type = StandardEventResultsTypes.raw,
                    participantResults = emptyList(),
                    runCount = 1
                ),
                OverallEventResults(
                    type = StandardEventResultsTypes.pax,
                    participantResults = emptyList(),
                    runCount = 1
                )
            ),
            groupEventResults = TestClazzEventResults.LsccTieBreaking.points2
        )
    }
}