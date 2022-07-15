package tech.coner.trailer.eventresults

import tech.coner.trailer.TestClasses
import tech.coner.trailer.TestEventContexts
import tech.coner.trailer.TestParticipants
import tech.coner.trailer.Time

object TestTopTimesEventResults {

    object Lscc2019 {

        val points1 by lazy { TestEventContexts.lscc2019SimplifiedPoints1.let { eventContext ->
            TopTimesEventResults(
                eventContext = eventContext,
                runCount = eventContext.extendedParameters.runsPerParticipant,
                topTimes = sortedMapOf(
                    TestClasses.Lscc2019.STREET to testParticipantResult(
                        position = 1,
                        score = Score("48.141"),
                        participant = TestParticipants.Lscc2019Points1.TERI_POTTER,
                        runFns = listOf(
                            { participant -> testRunWithScore(
                                sequence = 26,
                                participant = participant,
                                score = Score("51.026"),
                                time = Time("51.026")
                            ) },
                            { participant -> testRunWithScore(
                                sequence = 27,
                                participant = participant,
                                score = Score("49.335"),
                                time = Time("49.335")
                            ) },
                            { participant -> testRunWithScore(
                                sequence = 28,
                                participant = participant,
                                score = Score("50.396", Score.Penalty.Cone("2.000", 1)),
                                time = Time("48.396"),
                                cones = 1
                            ) },
                            { participant -> testRunWithScore(
                                sequence = 29,
                                participant = participant,
                                score = Score("49.208"),
                                time = Time("49.208")
                            ) },
                            { participant -> testRunWithScore(
                                sequence = 30,
                                participant = participant,
                                score = Score("48.141"),
                                time = Time("48.141")
                            ) }
                        ),
                        personalBestScoredRunIndex = 4,
                        diffFirst = Time("0.379"),
                        diffPrevious = Time("0.379")
                    ),
                    TestClasses.Lscc2019.STREET_TOURING to testParticipantResult(
                        position = 2,
                        score = Score("47.544"),
                        participant = TestParticipants.Lscc2019Points1.EUGENE_DRAKE,
                        runFns = listOf(
                            { participant -> testRunWithScore(
                                sequence = 16,
                                participant = participant,
                                score = Score("49.367"),
                                time = Time("49.367")
                            ) },
                            { participant -> testRunWithScore(
                                sequence = 17,
                                participant = participant,
                                score = Score("49.230"),
                                time = Time("49.230")
                            ) },
                            { participant -> testRunWithScore(
                                sequence = 18,
                                participant = participant,
                                score = Score("48.807"),
                                time = Time("48.807")
                            ) },
                            { participant -> testRunWithScore(
                                sequence = 19,
                                participant = participant,
                                score = Score("214748413.573"),
                                time = Time("49.573"),
                                didNotFinish = true
                            ) },
                            { participant -> testRunWithScore(
                                sequence = 20,
                                participant = participant,
                                score = Score("47.544"),
                                time = Time("47.544")
                            ) }
                        ),
                        personalBestScoredRunIndex = 4,
                        diffFirst = Time("1.401"),
                        diffPrevious = Time("1.401")
                    )
                )
            )
        } }
    }
}