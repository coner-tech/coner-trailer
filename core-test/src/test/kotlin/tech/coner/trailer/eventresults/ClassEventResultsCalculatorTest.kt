package tech.coner.trailer.eventresults

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import tech.coner.trailer.*

class ClassEventResultsCalculatorTest {

    @Test
    fun `It should calculate class results for LSCC 2019 Simplified Event 1`() {
        val eventContext = TestEventContexts.Lscc2019Simplified.points1
        val calculator = createClazzEventResultsCalculator(eventContext)

        val actual = calculator.calculate()

        assertThat(actual).all {
            hasType(StandardEventResultsTypes.clazz)
            resultsForGroupAbbreviation("HS").isNotNull().all {
                hasSize(2)
                index(0).all {
                    hasPosition(1)
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

                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Rebecca")
                        hasLastName("Jackson")
                        signage().isNotNull().all {
                            classing().isNotNull().all {
                                group().isNull()
                                handicap().hasAbbreviation("HS")
                            }
                            hasNumber("1")
                        }
                    }
                }
            }
            resultsForGroupAbbreviation("STR").isNotNull().all {
                hasSize(2)
                index(0).all {
                    hasPosition(1)
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
                }
            }
            resultsForGroupAbbreviation("NOV").isNotNull().all {
                hasSize(3)
                each {
                    it.participant()
                        .signage().isNotNull()
                        .classing().isNotNull()
                        .group().isNotNull()
                        .hasAbbreviation("NOV")
                }
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Brandy")
                        hasLastName("Huff")
                        signage().isNotNull().all {
                            classing().isNotNull().handicap().hasAbbreviation("BS")
                            hasNumber("177")
                        }
                    }
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Bryant")
                        hasLastName("Moran")
                        signage().isNotNull().all {
                            classing().isNotNull().handicap().hasAbbreviation("ES")
                            hasNumber("58")
                        }
                    }
                }
                index(2).all {
                    hasPosition(3)
                    participant().all {
                        hasFirstName("Dominic")
                        hasLastName("Rogers")
                        signage().isNotNull().all {
                            classing().isNotNull().handicap().hasAbbreviation("ES")
                            hasNumber("18")
                        }
                    }
                }
            }
        }
    }

    @Test
    fun `It should calculate class results for LSCC 2019 Simplified Event 2`() {
        val eventContext = TestEventContexts.Lscc2019Simplified.points2
        val calculator = createClazzEventResultsCalculator(eventContext)

        val actual = calculator.calculate()

        assertThat(actual).all {
            hasType(StandardEventResultsTypes.clazz)
            resultsForGroupAbbreviation("HS").isNotNull().all {
                hasSize(1)
                index(0).all {
                    hasPosition(1)
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
                }
            }
            resultsForGroupAbbreviation("STR").isNotNull().all {
                hasSize(2)
                each {
                    it.participant()
                        .signage().isNotNull()
                        .classing().isNotNull().all {
                            group().isNull()
                            handicap().hasAbbreviation("STR")
                        }
                }
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Rebecca")
                        hasLastName("Jackson")
                        signage().isNotNull().all {
                            hasNumber("8")
                        }
                    }
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Jimmy")
                        hasLastName("Mckenzie")
                        signage().isNotNull().hasNumber("23")
                    }
                }
            }
            resultsForGroupAbbreviation("NOV").isNotNull().all {
                hasSize(3)
                each {
                    it.participant()
                        .signage().isNotNull()
                        .classing().isNotNull()
                        .group().isNotNull()
                        .hasAbbreviation("NOV")
                }
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Brandy")
                        hasLastName("Huff")
                        signage().isNotNull().all {
                            classing().isNotNull().handicap().hasAbbreviation("BS")
                            hasNumber("52")
                        }
                    }
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Dominic")
                        hasLastName("Rogers")
                        signage().isNotNull().all {
                            classing().isNotNull().handicap().hasAbbreviation("ES")
                            hasNumber("18")
                        }
                    }
                }
                index(2).all {
                    hasPosition(3)
                    participant().all {
                        hasFirstName("Bennett")
                        hasLastName("Pantone")
                        signage().isNotNull().all {
                            classing().isNotNull().handicap().hasAbbreviation("CS")
                            hasNumber("20")
                        }
                    }
                }
            }
        }
    }

    @Test
    fun `It should calculate class results for LSCC 2019 Simplified Event 3`() {
        val eventContext = TestEventContexts.Lscc2019Simplified.points3
        val calculator = createClazzEventResultsCalculator(eventContext)

        val actual = calculator.calculate()

        assertThat(actual).all {
            hasType(StandardEventResultsTypes.clazz)
            resultsForGroupAbbreviation("HS").isNotNull().all {
                hasSize(1)
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Anastasia")
                        hasLastName("Rigler")
                        signage().isNotNull().all {
                            classing().isNotNull().handicap().hasAbbreviation("HS")
                            hasNumber("130")
                        }
                    }
                }
            }
            resultsForGroupAbbreviation("STR").isNotNull().all {
                hasSize(3)
                each {
                    it.participant()
                        .signage().isNotNull()
                        .classing().isNotNull().all {
                            group().isNull()
                            handicap().hasAbbreviation("STR")
                        }
                }
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Rebecca")
                        hasLastName("Jackson")
                        signage().isNotNull().hasNumber("8")
                    }
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Jimmy")
                        hasLastName("Mckenzie")
                        signage().isNotNull().hasNumber("23")
                    }
                }
                index(2).all {
                    hasPosition(3)
                    participant().all {
                        hasFirstName("Eugene")
                        hasLastName("Drake")
                        signage().isNotNull().hasNumber("1")
                    }
                }
            }
            resultsForGroupAbbreviation("NOV").isNotNull().all {
                hasSize(4)
                each {
                    it.participant()
                        .signage().isNotNull()
                        .classing().isNotNull()
                        .group().isNotNull()
                        .hasAbbreviation("NOV")
                }
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Brandy")
                        hasLastName("Huff")
                        signage().isNotNull().all {
                            classing().isNotNull()
                                .handicap().hasAbbreviation("BS")
                            hasNumber("52")
                        }
                    }
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Bryant")
                        hasLastName("Moran")
                        signage().isNotNull().all {
                            classing().isNotNull()
                                .handicap().hasAbbreviation("GS")
                            hasNumber("58")
                        }
                    }
                }
                index(2).all {
                    hasPosition(3)
                    participant().all {
                        hasFirstName("Dominic")
                        hasLastName("Rogers")
                        signage().isNotNull().all {
                            classing().isNotNull().handicap().hasAbbreviation("ES")
                            hasNumber("18")
                        }
                    }
                }
                index(3).all {
                    hasPosition(4)
                    participant().all {
                        hasFirstName("Bennett")
                        hasLastName("Pantone")
                        signage().isNotNull().all {
                            classing().isNotNull().handicap().hasAbbreviation("CS")
                            hasNumber("20")
                        }
                    }
                }
            }
        }
    }

    enum class LifecycleFixtures(
        val eventContext: EventContext,
        val expected: ClassEventResults
    ) {
        // Event.Lifecycle.CREATE cases
        CREATE_NO_PARTICIPANTS_YET(
            eventContext = TestEventContexts.Lifecycles.Create.noParticipantsYet,
            expected = TestClazzEventResults.Lifecycles.Create.noParticipantsYet
        ),
        CREATE_RUNS_WITHOUT_SIGNAGE(
            eventContext = TestEventContexts.Lifecycles.Create.runsWithoutSignage,
            expected = TestClazzEventResults.Lifecycles.Create.runsWithoutSignage
        ),
        CREATE_RUNS_WITHOUT_PARTICIPANTS(
            eventContext = TestEventContexts.Lifecycles.Create.runsWithoutParticipants,
            expected = TestClazzEventResults.Lifecycles.Create.runsWithoutParticipants
        ),
        CREATE_PARTICIPANTS_WITHOUT_RUNS(
            eventContext = TestEventContexts.Lifecycles.Create.participantsWithoutRuns,
            expected = TestClazzEventResults.Lifecycles.Create.participantsWithoutRuns
        ),
        CREATE_SOME_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.Lifecycles.Create.someParticipantsWithSomeRuns,
            expected = TestClazzEventResults.Lifecycles.Create.someParticipantsWithSomeRuns
        ),
        CREATE_SOME_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.Lifecycles.Create.someParticipantsWithAllRuns,
            expected = TestClazzEventResults.Lifecycles.Create.someParticipantsWithAllRuns
        ),
        CREATE_ALL_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.Lifecycles.Create.allParticipantsWithSomeRuns,
            expected = TestClazzEventResults.Lifecycles.Create.allParticipantsWithSomeRuns
        ),
        CREATE_ALL_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.Lifecycles.Create.allParticipantsWithAllRuns,
            expected = TestClazzEventResults.Lifecycles.Create.allParticipantsWithAllRuns
        ),
        // Event.Lifecycle.PRE cases
        PRE_NO_PARTICIPANTS_YET(
            eventContext = TestEventContexts.Lifecycles.Pre.noParticipantsYet,
            expected = TestClazzEventResults.Lifecycles.Pre.noParticipantsYet
        ),
        PRE_RUNS_WITHOUT_SIGNAGE(
            eventContext = TestEventContexts.Lifecycles.Pre.runsWithoutSignage,
            expected = TestClazzEventResults.Lifecycles.Pre.runsWithoutSignage
        ),
        PRE_RUNS_WITHOUT_PARTICIPANTS(
            eventContext = TestEventContexts.Lifecycles.Pre.runsWithoutParticipants,
            expected = TestClazzEventResults.Lifecycles.Pre.runsWithoutParticipants
        ),
        PRE_PARTICIPANTS_WITHOUT_RUNS(
            eventContext = TestEventContexts.Lifecycles.Pre.participantsWithoutRuns,
            expected = TestClazzEventResults.Lifecycles.Pre.participantsWithoutRuns
        ),
        PRE_SOME_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.Lifecycles.Pre.someParticipantsWithSomeRuns,
            expected = TestClazzEventResults.Lifecycles.Pre.someParticipantsWithSomeRuns
        ),
        PRE_SOME_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.Lifecycles.Pre.someParticipantsWithAllRuns,
            expected = TestClazzEventResults.Lifecycles.Pre.someParticipantsWithAllRuns
        ),
        PRE_ALL_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.Lifecycles.Pre.allParticipantsWithSomeRuns,
            expected = TestClazzEventResults.Lifecycles.Pre.allParticipantsWithSomeRuns
        ),
        PRE_ALL_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.Lifecycles.Pre.allParticipantsWithAllRuns,
            expected = TestClazzEventResults.Lifecycles.Pre.allParticipantsWithAllRuns
        ),
        // Event.Lifecycle.ACTIVE cases
        ACTIVE_NO_PARTICIPANTS_YET(
            eventContext = TestEventContexts.Lifecycles.Active.noParticipantsYet,
            expected = TestClazzEventResults.Lifecycles.Active.noParticipantsYet
        ),
        ACTIVE_RUNS_WITHOUT_SIGNAGE(
            eventContext = TestEventContexts.Lifecycles.Active.runsWithoutSignage,
            expected = TestClazzEventResults.Lifecycles.Active.runsWithoutSignage
        ),
        ACTIVE_RUNS_WITHOUT_PARTICIPANTS(
            eventContext = TestEventContexts.Lifecycles.Active.runsWithoutParticipants,
            expected = TestClazzEventResults.Lifecycles.Active.runsWithoutParticipants
        ),
        ACTIVE_PARTICIPANTS_WITHOUT_RUNS(
            eventContext = TestEventContexts.Lifecycles.Active.participantsWithoutRuns,
            expected = TestClazzEventResults.Lifecycles.Active.participantsWithoutRuns
        ),
        ACTIVE_SOME_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.Lifecycles.Active.someParticipantsWithSomeRuns,
            expected = TestClazzEventResults.Lifecycles.Active.someParticipantsWithSomeRuns
        ),
        ACTIVE_SOME_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.Lifecycles.Active.someParticipantsWithAllRuns,
            expected = TestClazzEventResults.Lifecycles.Active.someParticipantsWithAllRuns
        ),
        ACTIVE_ALL_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.Lifecycles.Active.allParticipantsWithSomeRuns,
            expected = TestClazzEventResults.Lifecycles.Active.allParticipantsWithSomeRuns
        ),
        ACTIVE_ALL_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.Lifecycles.Active.allParticipantsWithAllRuns,
            expected = TestClazzEventResults.Lifecycles.Active.allParticipantsWithAllRuns
        ),
        // Event.Lifecycle.POST cases
        POST_NO_PARTICIPANTS_YET(
            eventContext = TestEventContexts.Lifecycles.Post.noParticipantsYet,
            expected = TestClazzEventResults.Lifecycles.Post.noParticipantsYet
        ),
        POST_RUNS_WITHOUT_SIGNAGE(
            eventContext = TestEventContexts.Lifecycles.Post.runsWithoutSignage,
            expected = TestClazzEventResults.Lifecycles.Post.runsWithoutSignage
        ),
        POST_RUNS_WITHOUT_PARTICIPANTS(
            eventContext = TestEventContexts.Lifecycles.Post.runsWithoutParticipants,
            expected = TestClazzEventResults.Lifecycles.Post.runsWithoutParticipants
        ),
        POST_PARTICIPANTS_WITHOUT_RUNS(
            eventContext = TestEventContexts.Lifecycles.Post.participantsWithoutRuns,
            expected = TestClazzEventResults.Lifecycles.Post.participantsWithoutRuns
        ),
        POST_SOME_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.Lifecycles.Post.someParticipantsWithSomeRuns,
            expected = TestClazzEventResults.Lifecycles.Post.someParticipantsWithSomeRuns
        ),
        POST_SOME_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.Lifecycles.Post.someParticipantsWithAllRuns,
            expected = TestClazzEventResults.Lifecycles.Post.someParticipantsWithAllRuns
        ),
        POST_ALL_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.Lifecycles.Post.allParticipantsWithSomeRuns,
            expected = TestClazzEventResults.Lifecycles.Post.allParticipantsWithSomeRuns
        ),
        POST_ALL_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.Lifecycles.Post.allParticipantsWithAllRuns,
            expected = TestClazzEventResults.Lifecycles.Post.allParticipantsWithAllRuns
        ),
        // Event.Lifecycle.FINAL cases
        FINAL_NO_PARTICIPANTS_YET(
            eventContext = TestEventContexts.Lifecycles.Final.noParticipantsYet,
            expected = TestClazzEventResults.Lifecycles.Final.noParticipantsYet
        ),
        FINAL_RUNS_WITHOUT_SIGNAGE(
            eventContext = TestEventContexts.Lifecycles.Final.runsWithoutSignage,
            expected = TestClazzEventResults.Lifecycles.Final.runsWithoutSignage
        ),
        FINAL_RUNS_WITHOUT_PARTICIPANTS(
            eventContext = TestEventContexts.Lifecycles.Final.runsWithoutParticipants,
            expected = TestClazzEventResults.Lifecycles.Final.runsWithoutParticipants
        ),
        FINAL_PARTICIPANTS_WITHOUT_RUNS(
            eventContext = TestEventContexts.Lifecycles.Final.participantsWithoutRuns,
            expected = TestClazzEventResults.Lifecycles.Final.participantsWithoutRuns
        ),
        FINAL_SOME_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.Lifecycles.Final.someParticipantsWithSomeRuns,
            expected = TestClazzEventResults.Lifecycles.Final.someParticipantsWithSomeRuns
        ),
        FINAL_SOME_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.Lifecycles.Final.someParticipantsWithAllRuns,
            expected = TestClazzEventResults.Lifecycles.Final.someParticipantsWithAllRuns
        ),
        FINAL_ALL_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.Lifecycles.Final.allParticipantsWithSomeRuns,
            expected = TestClazzEventResults.Lifecycles.Final.allParticipantsWithSomeRuns
        ),
        FINAL_ALL_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.Lifecycles.Final.allParticipantsWithAllRuns,
            expected = TestClazzEventResults.Lifecycles.Final.allParticipantsWithAllRuns
        ),
        
    }

    @ParameterizedTest
    @EnumSource
    fun `It should calculate expected results for events in various states`(params: LifecycleFixtures) {
        val subject = createClazzEventResultsCalculator(params.eventContext)

        val actual = subject.calculate()

        assertThat(actual).isEqualTo(params.expected)
    }

    private fun createClazzEventResultsCalculator(eventContext: EventContext): ClazzEventResultsCalculator {
        val standardPenaltyFactory = StandardPenaltyFactory(eventContext.event.policy)
        return ClazzEventResultsCalculator(
            eventContext = eventContext,
            scoredRunsComparator = ParticipantResult.ScoredRunsComparator(
                runCount = eventContext.extendedParameters.runsPerParticipant
            ),
            runEligibilityQualifier = RunEligibilityQualifier(),
            runScoreFactory = ClazzRunScoreFactory(
                rawTimes = RawTimeRunScoreFactory(standardPenaltyFactory),
                paxTimes = when (eventContext.event.policy.paxTimeStyle) {
                    PaxTimeStyle.FAIR -> PaxTimeRunScoreFactory(standardPenaltyFactory)
                    PaxTimeStyle.LEGACY_BUGGED -> LegacyBuggedPaxTimeRunScoreFactory(standardPenaltyFactory)
                }
            ),
            finalScoreFactory = AutocrossFinalScoreFactory()
        )
    }
}