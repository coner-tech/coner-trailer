package tech.coner.trailer.eventresults

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import tech.coner.trailer.*

class ClazzEventResultsCalculatorTest {

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

    enum class LifecycleCaseFixtures(
        val eventContext: EventContext,
        val expected: ClazzEventResults
    ) {
        // Event.Lifecycle.CREATE cases
        CREATE_NO_PARTICIPANTS_YET(
            eventContext = TestEventContexts.LifecycleCases.Create.noParticipantsYet,
            expected = TestClazzEventResults.LifecyclePhases.Create.noParticipantsYet
        ),
        CREATE_RUNS_WITHOUT_SIGNAGE(
            eventContext = TestEventContexts.LifecycleCases.Create.runsWithoutSignage,
            expected = TestClazzEventResults.LifecyclePhases.Create.runsWithoutSignage
        ),
        CREATE_RUNS_WITHOUT_PARTICIPANTS(
            eventContext = TestEventContexts.LifecycleCases.Create.runsWithoutParticipants,
            expected = TestClazzEventResults.LifecyclePhases.Create.runsWithoutParticipants
        ),
        CREATE_PARTICIPANTS_WITHOUT_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Create.participantsWithoutRuns,
            expected = TestClazzEventResults.LifecyclePhases.Create.participantsWithoutRuns
        ),
        CREATE_SOME_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Create.someParticipantsWithSomeRuns,
            expected = TestClazzEventResults.LifecyclePhases.Create.someParticipantsWithSomeRuns
        ),
        CREATE_SOME_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Create.someParticipantsWithAllRuns,
            expected = TestClazzEventResults.LifecyclePhases.Create.someParticipantsWithAllRuns
        ),
        CREATE_ALL_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Create.allParticipantsWithSomeRuns,
            expected = TestClazzEventResults.LifecyclePhases.Create.allParticipantsWithSomeRuns
        ),
        CREATE_ALL_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Create.allParticipantsWithAllRuns,
            expected = TestClazzEventResults.LifecyclePhases.Create.allParticipantsWithAllRuns
        ),
        // Event.Lifecycle.PRE cases
        PRE_NO_PARTICIPANTS_YET(
            eventContext = TestEventContexts.LifecycleCases.Pre.noParticipantsYet,
            expected = TestClazzEventResults.LifecyclePhases.Pre.noParticipantsYet
        ),
        PRE_RUNS_WITHOUT_SIGNAGE(
            eventContext = TestEventContexts.LifecycleCases.Pre.runsWithoutSignage,
            expected = TestClazzEventResults.LifecyclePhases.Pre.runsWithoutSignage
        ),
        PRE_RUNS_WITHOUT_PARTICIPANTS(
            eventContext = TestEventContexts.LifecycleCases.Pre.runsWithoutParticipants,
            expected = TestClazzEventResults.LifecyclePhases.Pre.runsWithoutParticipants
        ),
        PRE_PARTICIPANTS_WITHOUT_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Pre.participantsWithoutRuns,
            expected = TestClazzEventResults.LifecyclePhases.Pre.participantsWithoutRuns
        ),
        PRE_SOME_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Pre.someParticipantsWithSomeRuns,
            expected = TestClazzEventResults.LifecyclePhases.Pre.someParticipantsWithSomeRuns
        ),
        PRE_SOME_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Pre.someParticipantsWithAllRuns,
            expected = TestClazzEventResults.LifecyclePhases.Pre.someParticipantsWithAllRuns
        ),
        PRE_ALL_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Pre.allParticipantsWithSomeRuns,
            expected = TestClazzEventResults.LifecyclePhases.Pre.allParticipantsWithSomeRuns
        ),
        PRE_ALL_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Pre.allParticipantsWithAllRuns,
            expected = TestClazzEventResults.LifecyclePhases.Pre.allParticipantsWithAllRuns
        ),
        // Event.Lifecycle.ACTIVE cases
        ACTIVE_NO_PARTICIPANTS_YET(
            eventContext = TestEventContexts.LifecycleCases.Active.noParticipantsYet,
            expected = TestClazzEventResults.LifecyclePhases.Active.noParticipantsYet
        ),
        ACTIVE_RUNS_WITHOUT_SIGNAGE(
            eventContext = TestEventContexts.LifecycleCases.Active.runsWithoutSignage,
            expected = TestClazzEventResults.LifecyclePhases.Active.runsWithoutSignage
        ),
        ACTIVE_RUNS_WITHOUT_PARTICIPANTS(
            eventContext = TestEventContexts.LifecycleCases.Active.runsWithoutParticipants,
            expected = TestClazzEventResults.LifecyclePhases.Active.runsWithoutParticipants
        ),
        ACTIVE_PARTICIPANTS_WITHOUT_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Active.participantsWithoutRuns,
            expected = TestClazzEventResults.LifecyclePhases.Active.participantsWithoutRuns
        ),
        ACTIVE_SOME_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Active.someParticipantsWithSomeRuns,
            expected = TestClazzEventResults.LifecyclePhases.Active.someParticipantsWithSomeRuns
        ),
        ACTIVE_SOME_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Active.someParticipantsWithAllRuns,
            expected = TestClazzEventResults.LifecyclePhases.Active.someParticipantsWithAllRuns
        ),
        ACTIVE_ALL_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Active.allParticipantsWithSomeRuns,
            expected = TestClazzEventResults.LifecyclePhases.Active.allParticipantsWithSomeRuns
        ),
        ACTIVE_ALL_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Active.allParticipantsWithAllRuns,
            expected = TestClazzEventResults.LifecyclePhases.Active.allParticipantsWithAllRuns
        ),
        // Event.Lifecycle.POST cases
        POST_NO_PARTICIPANTS_YET(
            eventContext = TestEventContexts.LifecycleCases.Post.noParticipantsYet,
            expected = TestClazzEventResults.LifecyclePhases.Post.noParticipantsYet
        ),
        POST_RUNS_WITHOUT_SIGNAGE(
            eventContext = TestEventContexts.LifecycleCases.Post.runsWithoutSignage,
            expected = TestClazzEventResults.LifecyclePhases.Post.runsWithoutSignage
        ),
        POST_RUNS_WITHOUT_PARTICIPANTS(
            eventContext = TestEventContexts.LifecycleCases.Post.runsWithoutParticipants,
            expected = TestClazzEventResults.LifecyclePhases.Post.runsWithoutParticipants
        ),
        POST_PARTICIPANTS_WITHOUT_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Post.participantsWithoutRuns,
            expected = TestClazzEventResults.LifecyclePhases.Post.participantsWithoutRuns
        ),
        POST_SOME_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Post.someParticipantsWithSomeRuns,
            expected = TestClazzEventResults.LifecyclePhases.Post.someParticipantsWithSomeRuns
        ),
        POST_SOME_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Post.someParticipantsWithAllRuns,
            expected = TestClazzEventResults.LifecyclePhases.Post.someParticipantsWithAllRuns
        ),
        POST_ALL_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Post.allParticipantsWithSomeRuns,
            expected = TestClazzEventResults.LifecyclePhases.Post.allParticipantsWithSomeRuns
        ),
        POST_ALL_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Post.allParticipantsWithAllRuns,
            expected = TestClazzEventResults.LifecyclePhases.Post.allParticipantsWithAllRuns
        ),
        // Event.Lifecycle.FINAL cases
        FINAL_NO_PARTICIPANTS_YET(
            eventContext = TestEventContexts.LifecycleCases.Final.noParticipantsYet,
            expected = TestClazzEventResults.LifecyclePhases.Final.noParticipantsYet
        ),
        FINAL_RUNS_WITHOUT_SIGNAGE(
            eventContext = TestEventContexts.LifecycleCases.Final.runsWithoutSignage,
            expected = TestClazzEventResults.LifecyclePhases.Final.runsWithoutSignage
        ),
        FINAL_RUNS_WITHOUT_PARTICIPANTS(
            eventContext = TestEventContexts.LifecycleCases.Final.runsWithoutParticipants,
            expected = TestClazzEventResults.LifecyclePhases.Final.runsWithoutParticipants
        ),
        FINAL_PARTICIPANTS_WITHOUT_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Final.participantsWithoutRuns,
            expected = TestClazzEventResults.LifecyclePhases.Final.participantsWithoutRuns
        ),
        FINAL_SOME_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Final.someParticipantsWithSomeRuns,
            expected = TestClazzEventResults.LifecyclePhases.Final.someParticipantsWithSomeRuns
        ),
        FINAL_SOME_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Final.someParticipantsWithAllRuns,
            expected = TestClazzEventResults.LifecyclePhases.Final.someParticipantsWithAllRuns
        ),
        FINAL_ALL_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Final.allParticipantsWithSomeRuns,
            expected = TestClazzEventResults.LifecyclePhases.Final.allParticipantsWithSomeRuns
        ),
        FINAL_ALL_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Final.allParticipantsWithAllRuns,
            expected = TestClazzEventResults.LifecyclePhases.Final.allParticipantsWithAllRuns
        ),
        
    }

    @ParameterizedTest
    @EnumSource
    fun `It should calculate expected results for events in various states`(params: LifecycleCaseFixtures) {
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