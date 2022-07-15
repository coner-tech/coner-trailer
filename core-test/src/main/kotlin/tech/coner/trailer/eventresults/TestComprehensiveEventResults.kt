package tech.coner.trailer.eventresults

import tech.coner.trailer.TestEventContexts

object TestComprehensiveEventResults {

    object Lscc2019 {

        val points1: ComprehensiveEventResults
            get() = ComprehensiveEventResults(
                eventContext = TestEventContexts.Lscc2019.points1,
                runCount = 5,
                overallEventResults = listOf(
                    OverallEventResults(
                        eventContext = TestEventContexts.Lscc2019.points1,
                        type = StandardEventResultsTypes.raw,
                        participantResults = listOf(),
                        runCount = 5
                    ),
                    OverallEventResults(
                        eventContext = TestEventContexts.Lscc2019.points1,
                        type = StandardEventResultsTypes.pax,
                        participantResults = listOf(),
                        runCount = 5
                    )
                ),
                clazzEventResults = TestClazzEventResults.Lscc2019.points1
            )
    }

    object LsccTieBreaking {
        val points1: ComprehensiveEventResults
            get() = ComprehensiveEventResults(
                eventContext = TestEventContexts.LsccTieBreaking.points1,
                runCount = 1,
                overallEventResults = listOf(
                    OverallEventResults(
                        eventContext = TestEventContexts.LsccTieBreaking.points1,
                        type = StandardEventResultsTypes.raw,
                        participantResults = emptyList(),
                        runCount = 1
                    ),
                    OverallEventResults(
                        eventContext = TestEventContexts.LsccTieBreaking.points1,
                        type = StandardEventResultsTypes.pax,
                        participantResults = emptyList(),
                        runCount = 1
                    )
                ),
                clazzEventResults = TestClazzEventResults.LsccTieBreaking.points1
            )
        val points2: ComprehensiveEventResults get() = ComprehensiveEventResults(
            eventContext = TestEventContexts.Lscc2019Simplified.points2,
            runCount = 1,
            overallEventResults = listOf(
                OverallEventResults(
                    eventContext = TestEventContexts.Lscc2019Simplified.points2,
                    type = StandardEventResultsTypes.raw,
                    participantResults = emptyList(),
                    runCount = 1
                ),
                OverallEventResults(
                    eventContext = TestEventContexts.Lscc2019Simplified.points2,
                    type = StandardEventResultsTypes.pax,
                    participantResults = emptyList(),
                    runCount = 1
                )
            ),
            clazzEventResults = TestClazzEventResults.LsccTieBreaking.points2
        )
    }
}