package tech.coner.trailer.eventresults

import tech.coner.trailer.TestEventContexts

object TestComprehensiveEventResults {

    object Lscc2019 {

        val points1: ComprehensiveEventResults
            get() = ComprehensiveEventResults(
                eventContext = TestEventContexts.Lscc2019.points1,
                overallEventResults = listOf(
                    OverallEventResults(
                        eventContext = TestEventContexts.Lscc2019.points1,
                        type = StandardEventResultsTypes.raw,
                        participantResults = listOf(),
                    ),
                    OverallEventResults(
                        eventContext = TestEventContexts.Lscc2019.points1,
                        type = StandardEventResultsTypes.pax,
                        participantResults = listOf(),
                    )
                ),
                clazzEventResults = TestClazzEventResults.Lscc2019.points1,
                topTimesEventResults = TestTopTimesEventResults.Lscc2019.points1
            )
    }

    object Lscc2019Simplified {
        val points1: ComprehensiveEventResults
            get() = ComprehensiveEventResults(
                eventContext = TestEventContexts.Lscc2019Simplified.points1,
                overallEventResults = listOf(
                    OverallEventResults(
                        eventContext = TestEventContexts.Lscc2019Simplified.points1,
                        type = StandardEventResultsTypes.raw,
                        participantResults = emptyList(),
                    ),
                    OverallEventResults(
                        eventContext = TestEventContexts.Lscc2019Simplified.points1,
                        type = StandardEventResultsTypes.pax,
                        participantResults = emptyList()
                    )
                ),
                clazzEventResults = TestClazzEventResults.Lscc2019Simplified.points1,
                topTimesEventResults = TestTopTimesEventResults.Lscc2019Simplified.points1
            )
    }

    object LsccTieBreaking {
        val points1: ComprehensiveEventResults
            get() = ComprehensiveEventResults(
                eventContext = TestEventContexts.LsccTieBreaking.points1,
                overallEventResults = listOf(
                    OverallEventResults(
                        eventContext = TestEventContexts.LsccTieBreaking.points1,
                        type = StandardEventResultsTypes.raw,
                        participantResults = emptyList(),
                    ),
                    OverallEventResults(
                        eventContext = TestEventContexts.LsccTieBreaking.points1,
                        type = StandardEventResultsTypes.pax,
                        participantResults = emptyList(),
                    )
                ),
                clazzEventResults = TestClazzEventResults.LsccTieBreaking.points1,
                topTimesEventResults = TestTopTimesEventResults.LsccTieBreaking.points1
            )
        val points2: ComprehensiveEventResults get() = ComprehensiveEventResults(
            eventContext = TestEventContexts.LsccTieBreaking.points2,
            overallEventResults = listOf(
                OverallEventResults(
                    eventContext = TestEventContexts.LsccTieBreaking.points2,
                    type = StandardEventResultsTypes.raw,
                    participantResults = emptyList(),
                ),
                OverallEventResults(
                    eventContext = TestEventContexts.LsccTieBreaking.points2,
                    type = StandardEventResultsTypes.pax,
                    participantResults = emptyList(),
                )
            ),
            clazzEventResults = TestClazzEventResults.LsccTieBreaking.points2,
            topTimesEventResults = TestTopTimesEventResults.LsccTieBreaking.points2
        )
    }
}