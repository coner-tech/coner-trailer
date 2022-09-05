package tech.coner.trailer.eventresults

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import tech.coner.trailer.*

class RawEventResultsCalculatorTest {

    @Test
    fun `It should calculate raw results for LSCC 2019 Simplified Event 1`() {
        val eventContext = TestEventContexts.Lscc2019Simplified.points1
        val calculator = createRawEventResultsCalculator(eventContext)

        val actual = calculator.calculate()

        assertThat(actual).isEqualTo(TestOverallRawEventResults.Lscc2019Simplified.points1)
    }

    @Test
    fun `It should calculate raw results for LSCC 2019 Simplified Event 2`() {
        val eventContext = TestEventContexts.Lscc2019Simplified.points2
        val calculator = createRawEventResultsCalculator(eventContext)

        val actual = calculator.calculate()

        assertThat(actual).all {
            hasType(StandardEventResultsTypes.raw)
            participantResults().all {
                hasSize(6)
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Rebecca")
                        hasLastName("Jackson")
                        signage().isNotNull().all {
                            classing().isNotNull().all {
                                group().isNull()
                                handicap().hasAbbreviation("STR")
                            }
                            hasNumber("8")
                        }
                    }
                    score().hasValue("34.762")
                    diffFirst().isNull()
                    diffPrevious().isNull()
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Jimmy")
                        hasLastName("Mckenzie")
                        signage().isNotNull().all {
                            classing().isNotNull().all {
                                group().isNull()
                                handicap().hasAbbreviation("STR")
                            }
                            hasNumber("23")
                        }
                    }
                    score().hasValue("36.185")
                    diffFirst().isEqualTo("1.423")
                    diffPrevious().isEqualTo("1.423")
                }
                index(2).all {
                    hasPosition(3)
                    participant().all {
                        hasFirstName("Brandy")
                        hasLastName("Huff")
                        signage().isNotNull().all {
                            classing().isNotNull().all {
                                group().isNotNull().hasAbbreviation("NOV")
                                handicap().hasAbbreviation("BS")
                            }
                            hasNumber("52")
                        }
                    }
                    score().hasValue("37.058")
                    diffFirst().isEqualTo("2.296")
                    diffPrevious().isEqualTo("0.873")
                }
                index(3).all {
                    hasPosition(4)
                    participant().all {
                        hasFirstName("Dominic")
                        hasLastName("Rogers")
                        signage().isNotNull().all {
                            classing().isNotNull().all {
                                group().isNotNull().hasAbbreviation("NOV")
                                handicap().hasAbbreviation("ES")
                            }
                            hasNumber("18")
                        }
                    }
                    score().hasValue("38.698")
                    diffFirst().isEqualTo("3.936")
                    diffPrevious().isEqualTo("1.640")
                }
                index(4).all {
                    hasPosition(5)
                    participant().all {
                        hasFirstName("Anastasia")
                        hasLastName("Rigler")
                        signage().isNotNull().all {
                            classing().isNotNull().all {
                                group().isNull()
                                handicap().hasAbbreviation("HS")
                            }
                            hasNumber("130")
                        }
                    }
                    score().hasValue("38.986")
                    diffFirst().isEqualTo("4.224")
                    diffPrevious().isEqualTo("0.288")
                }
                index(5).all {
                    hasPosition(6)
                    participant().all {
                        hasFirstName("Bennett")
                        hasLastName("Pantone")
                        signage().isNotNull().all {
                            classing().isNotNull().all {
                                group().isNotNull().hasAbbreviation("NOV")
                                handicap().hasAbbreviation("CS")
                            }
                            hasNumber("20")
                        }
                    }
                    score().hasDidNotFinish()
                    diffFirst().isNull()
                    diffPrevious().isNull()
                }
            }
        }
    }

    @Test
    fun `It should calculate raw results for LSCC 2019 Simplified Event 3`() {
        val eventContext = TestEventContexts.Lscc2019Simplified.points3
        val calculator = createRawEventResultsCalculator(eventContext)

        val actual = calculator.calculate()

        assertThat(actual).all {
            hasType(StandardEventResultsTypes.raw)
            participantResults().all {
                hasSize(8)
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Rebecca")
                        hasLastName("Jackson")
                        signage().isNotNull().all {
                            classing().isNotNull().all {
                                group().isNull()
                                handicap().hasAbbreviation("STR")
                            }
                            hasNumber("8")
                        }
                    }
                    score().hasValue("80.476")
                    diffFirst().isNull()
                    diffPrevious().isNull()
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Jimmy")
                        hasLastName("Mckenzie")
                        signage().isNotNull().all {
                            classing().isNotNull().all {
                                group().isNull()
                                handicap().hasAbbreviation("STR")
                            }
                            hasNumber("23")
                        }
                    }
                    score().hasValue("83.740")
                    diffFirst().isEqualTo("3.264")
                    diffPrevious().isEqualTo("3.264")
                }
                index(2).all {
                    hasPosition(3)
                    participant().all {
                        hasFirstName("Eugene")
                        hasLastName("Drake")
                        signage().isNotNull().all {
                            classing().isNotNull().all {
                                group().isNull()
                                handicap().hasAbbreviation("STR")
                            }
                            hasNumber("1")
                        }
                    }
                    score().hasValue("87.036")
                    diffFirst().isEqualTo("6.560")
                    diffPrevious().isEqualTo("3.296")
                }
                index(3).all {
                    hasPosition(4)
                    participant().all {
                        hasFirstName("Brandy")
                        hasLastName("Huff")
                        signage().isNotNull().all {
                            classing().isNotNull().all {
                                group().isNotNull().hasAbbreviation("NOV")
                                handicap().hasAbbreviation("BS")
                            }
                            hasNumber("52")
                        }
                    }
                    score().hasValue("90.079")
                    diffFirst().isEqualTo("9.603")
                    diffPrevious().isEqualTo("3.043")
                }
                index(4).all {
                    hasPosition(5)
                    participant().all {
                        hasFirstName("Anastasia")
                        hasLastName("Rigler")
                        signage().isNotNull().all {
                            classing().isNotNull().all {
                                group().isNull()
                                handicap().hasAbbreviation("HS")
                            }
                            hasNumber("130")
                        }
                    }
                    score().hasValue("92.462")
                    diffFirst().isEqualTo("11.986")
                    diffPrevious().isEqualTo("2.383")
                }
                index(5).all {
                    hasPosition(6)
                    participant().all {
                        hasFirstName("Bennett")
                        hasLastName("Pantone")
                        signage().isNotNull().all {
                            classing().isNotNull().all {
                                group().isNotNull().hasAbbreviation("NOV")
                                handicap().hasAbbreviation("CS")
                            }
                            hasNumber("20")
                        }
                    }
                    score().hasValue("99.647")
                    diffFirst().isEqualTo("19.171")
                    diffPrevious().isEqualTo("7.185")
                }
                index(6).all {
                    hasPosition(7)
                    participant().all {
                        hasFirstName("Bryant")
                        hasLastName("Moran")
                        signage().isNotNull().all {
                            classing().isNotNull().all {
                                group().isNotNull().hasAbbreviation("NOV")
                                handicap().hasAbbreviation("GS")
                            }
                            hasNumber("58")
                        }
                    }
                    score().hasValue("100.059")
                    diffFirst().isEqualTo("19.583")
                    diffPrevious().isEqualTo("0.412")
                }
                index(7).all {
                    hasPosition(8)
                    participant().all {
                        hasFirstName("Dominic")
                        hasLastName("Rogers")
                        signage().isNotNull().all {
                            classing().isNotNull().all {
                                group().isNotNull().hasAbbreviation("NOV")
                                handicap().hasAbbreviation("ES")
                            }
                            hasNumber("18")
                        }
                    }
                    score().hasValue("100.215")
                    diffFirst().isEqualTo("19.739")
                    diffPrevious().isEqualTo("0.156")
                }
            }
        }
    }

    enum class LifecycleFixtures(
        val eventContext: EventContext,
        val expected: OverallEventResults
    ) {
        // Event.Lifecycle.CREATE cases
        CREATE_NO_PARTICIPANTS_YET(
            eventContext = TestEventContexts.Lifecycles.Create.noParticipantsYet,
            expected = TestOverallRawEventResults.Lifecycles.Create.noParticipantsYet
        ),
        CREATE_RUNS_WITHOUT_SIGNAGE(
            eventContext = TestEventContexts.Lifecycles.Create.runsWithoutSignage,
            expected = TestOverallRawEventResults.Lifecycles.Create.runsWithoutSignage
        ),
        CREATE_RUNS_WITHOUT_PARTICIPANTS(
            eventContext = TestEventContexts.Lifecycles.Create.runsWithoutParticipants,
            expected = TestOverallRawEventResults.Lifecycles.Create.runsWithoutParticipants
        ),
        CREATE_PARTICIPANTS_WITHOUT_RUNS(
            eventContext = TestEventContexts.Lifecycles.Create.participantsWithoutRuns,
            expected = TestOverallRawEventResults.Lifecycles.Create.participantsWithoutRuns
        ),
        CREATE_SOME_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.Lifecycles.Create.someParticipantsWithSomeRuns,
            expected = TestOverallRawEventResults.Lifecycles.Create.someParticipantsWithSomeRuns
        ),
        CREATE_SOME_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.Lifecycles.Create.someParticipantsWithAllRuns,
            expected = TestOverallRawEventResults.Lifecycles.Create.someParticipantsWithAllRuns
        ),
        CREATE_ALL_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.Lifecycles.Create.allParticipantsWithSomeRuns,
            expected = TestOverallRawEventResults.Lifecycles.Create.allParticipantsWithSomeRuns
        ),
        CREATE_ALL_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.Lifecycles.Create.allParticipantsWithAllRuns,
            expected = TestOverallRawEventResults.Lifecycles.Create.allParticipantsWithAllRuns
        ),
        // Event.Lifecycle.PRE cases
        PRE_NO_PARTICIPANTS_YET(
            eventContext = TestEventContexts.Lifecycles.Pre.noParticipantsYet,
            expected = TestOverallRawEventResults.Lifecycles.Pre.noParticipantsYet
        ),
        PRE_RUNS_WITHOUT_SIGNAGE(
            eventContext = TestEventContexts.Lifecycles.Pre.runsWithoutSignage,
            expected = TestOverallRawEventResults.Lifecycles.Pre.runsWithoutSignage
        ),
        PRE_RUNS_WITHOUT_PARTICIPANTS(
            eventContext = TestEventContexts.Lifecycles.Pre.runsWithoutParticipants,
            expected = TestOverallRawEventResults.Lifecycles.Pre.runsWithoutParticipants
        ),
        PRE_PARTICIPANTS_WITHOUT_RUNS(
            eventContext = TestEventContexts.Lifecycles.Pre.participantsWithoutRuns,
            expected = TestOverallRawEventResults.Lifecycles.Pre.participantsWithoutRuns
        ),
        PRE_SOME_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.Lifecycles.Pre.someParticipantsWithSomeRuns,
            expected = TestOverallRawEventResults.Lifecycles.Pre.someParticipantsWithSomeRuns
        ),
        PRE_SOME_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.Lifecycles.Pre.someParticipantsWithAllRuns,
            expected = TestOverallRawEventResults.Lifecycles.Pre.someParticipantsWithAllRuns
        ),
        PRE_ALL_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.Lifecycles.Pre.allParticipantsWithSomeRuns,
            expected = TestOverallRawEventResults.Lifecycles.Pre.allParticipantsWithSomeRuns
        ),
        PRE_ALL_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.Lifecycles.Pre.allParticipantsWithAllRuns,
            expected = TestOverallRawEventResults.Lifecycles.Pre.allParticipantsWithAllRuns
        ),
        // Event.Lifecycle.ACTIVE cases
        ACTIVE_NO_PARTICIPANTS_YET(
            eventContext = TestEventContexts.Lifecycles.Active.noParticipantsYet,
            expected = TestOverallRawEventResults.Lifecycles.Active.noParticipantsYet
        ),
        ACTIVE_RUNS_WITHOUT_SIGNAGE(
            eventContext = TestEventContexts.Lifecycles.Active.runsWithoutSignage,
            expected = TestOverallRawEventResults.Lifecycles.Active.runsWithoutSignage
        ),
        ACTIVE_RUNS_WITHOUT_PARTICIPANTS(
            eventContext = TestEventContexts.Lifecycles.Active.runsWithoutParticipants,
            expected = TestOverallRawEventResults.Lifecycles.Active.runsWithoutParticipants
        ),
        ACTIVE_PARTICIPANTS_WITHOUT_RUNS(
            eventContext = TestEventContexts.Lifecycles.Active.participantsWithoutRuns,
            expected = TestOverallRawEventResults.Lifecycles.Active.participantsWithoutRuns
        ),
        ACTIVE_SOME_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.Lifecycles.Active.someParticipantsWithSomeRuns,
            expected = TestOverallRawEventResults.Lifecycles.Active.someParticipantsWithSomeRuns
        ),
        ACTIVE_SOME_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.Lifecycles.Active.someParticipantsWithAllRuns,
            expected = TestOverallRawEventResults.Lifecycles.Active.someParticipantsWithAllRuns
        ),
        ACTIVE_ALL_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.Lifecycles.Active.allParticipantsWithSomeRuns,
            expected = TestOverallRawEventResults.Lifecycles.Active.allParticipantsWithSomeRuns
        ),
        ACTIVE_ALL_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.Lifecycles.Active.allParticipantsWithAllRuns,
            expected = TestOverallRawEventResults.Lifecycles.Active.allParticipantsWithAllRuns
        ),
        // Event.Lifecycle.POST cases
        POST_NO_PARTICIPANTS_YET(
            eventContext = TestEventContexts.Lifecycles.Post.noParticipantsYet,
            expected = TestOverallRawEventResults.Lifecycles.Post.noParticipantsYet
        ),
        POST_RUNS_WITHOUT_SIGNAGE(
            eventContext = TestEventContexts.Lifecycles.Post.runsWithoutSignage,
            expected = TestOverallRawEventResults.Lifecycles.Post.runsWithoutSignage
        ),
        POST_RUNS_WITHOUT_PARTICIPANTS(
            eventContext = TestEventContexts.Lifecycles.Post.runsWithoutParticipants,
            expected = TestOverallRawEventResults.Lifecycles.Post.runsWithoutParticipants
        ),
        POST_PARTICIPANTS_WITHOUT_RUNS(
            eventContext = TestEventContexts.Lifecycles.Post.participantsWithoutRuns,
            expected = TestOverallRawEventResults.Lifecycles.Post.participantsWithoutRuns
        ),
        POST_SOME_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.Lifecycles.Post.someParticipantsWithSomeRuns,
            expected = TestOverallRawEventResults.Lifecycles.Post.someParticipantsWithSomeRuns
        ),
        POST_SOME_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.Lifecycles.Post.someParticipantsWithAllRuns,
            expected = TestOverallRawEventResults.Lifecycles.Post.someParticipantsWithAllRuns
        ),
        POST_ALL_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.Lifecycles.Post.allParticipantsWithSomeRuns,
            expected = TestOverallRawEventResults.Lifecycles.Post.allParticipantsWithSomeRuns
        ),
        POST_ALL_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.Lifecycles.Post.allParticipantsWithAllRuns,
            expected = TestOverallRawEventResults.Lifecycles.Post.allParticipantsWithAllRuns
        ),
        // Event.Lifecycle.FINAL cases
        FINAL_NO_PARTICIPANTS_YET(
            eventContext = TestEventContexts.Lifecycles.Final.noParticipantsYet,
            expected = TestOverallRawEventResults.Lifecycles.Final.noParticipantsYet
        ),
        FINAL_RUNS_WITHOUT_SIGNAGE(
            eventContext = TestEventContexts.Lifecycles.Final.runsWithoutSignage,
            expected = TestOverallRawEventResults.Lifecycles.Final.runsWithoutSignage
        ),
        FINAL_RUNS_WITHOUT_PARTICIPANTS(
            eventContext = TestEventContexts.Lifecycles.Final.runsWithoutParticipants,
            expected = TestOverallRawEventResults.Lifecycles.Final.runsWithoutParticipants
        ),
        FINAL_PARTICIPANTS_WITHOUT_RUNS(
            eventContext = TestEventContexts.Lifecycles.Final.participantsWithoutRuns,
            expected = TestOverallRawEventResults.Lifecycles.Final.participantsWithoutRuns
        ),
        FINAL_SOME_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.Lifecycles.Final.someParticipantsWithSomeRuns,
            expected = TestOverallRawEventResults.Lifecycles.Final.someParticipantsWithSomeRuns
        ),
        FINAL_SOME_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.Lifecycles.Final.someParticipantsWithAllRuns,
            expected = TestOverallRawEventResults.Lifecycles.Final.someParticipantsWithAllRuns
        ),
        FINAL_ALL_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.Lifecycles.Final.allParticipantsWithSomeRuns,
            expected = TestOverallRawEventResults.Lifecycles.Final.allParticipantsWithSomeRuns
        ),
        FINAL_ALL_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.Lifecycles.Final.allParticipantsWithAllRuns,
            expected = TestOverallRawEventResults.Lifecycles.Final.allParticipantsWithAllRuns
        ),
    }

    @ParameterizedTest
    @EnumSource
    fun `It should calculate expected results for events in various states`(params: LifecycleFixtures) {
        val subject = createRawEventResultsCalculator(params.eventContext)

        val actual = subject.calculate()

        assertThat(actual).isEqualTo(params.expected)
    }

    private fun createRawEventResultsCalculator(eventContext: EventContext): RawEventResultsCalculator {
        return RawEventResultsCalculator(
            eventContext = eventContext,
            scoredRunsComparator = ParticipantResult.ScoredRunsComparator(eventContext.extendedParameters.runsPerParticipant),
            runEligibilityQualifier = RunEligibilityQualifier(),
            runScoreFactory = RawTimeRunScoreFactory(StandardPenaltyFactory(eventContext.event.policy)),
            finalScoreFactory = when (eventContext.event.policy.finalScoreStyle) {
                FinalScoreStyle.AUTOCROSS -> AutocrossFinalScoreFactory()
                FinalScoreStyle.RALLYCROSS -> RallycrossFinalScoreFactory()
            }
        )
    }
}