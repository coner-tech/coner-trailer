package tech.coner.trailer.eventresults

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import tech.coner.trailer.*

class PaxEventResultsCalculatorTest {

    @Test
    fun `It should calculate pax results for LSCC 2019 Simplified Event 1`() {
        val eventContext = TestEventContexts.Lscc2019Simplified.points1
        val calculator = createPaxEventResultsCalculator(eventContext)

        val actual = calculator.calculate()

        assertThat(actual).isEqualTo(TestOverallPaxEventResults.Lscc2019Simplified.points1)
    }

    @Test
    fun `It should calculate pax results for LSCC 2019 Simplified Event 2`() {
        val eventContext = TestEventContexts.Lscc2019Simplified.points2
        val calculator = createPaxEventResultsCalculator(eventContext)

        val actual = calculator.calculate()

        assertThat(actual).all {
            hasType(StandardEventResultsTypes.pax)
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
                    score().hasValue("28.748")
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
                    score().hasValue("29.925")
                    diffFirst().isEqualTo(Time("1.177"))
                    diffPrevious().isEqualTo(Time("1.177"))
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
                    score().hasValue("30.017")
                    diffFirst().isEqualTo(Time("1.269"))
                    diffPrevious().isEqualTo(Time("0.092"))
                }
                index(3).all {
                    hasPosition(4)
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
                    score().hasValue("30.409")
                    diffFirst().isEqualTo(Time("1.661"))
                    diffPrevious().isEqualTo(Time("0.392"))
                }
                index(4).all {
                    hasPosition(5)
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
                    score().hasValue("30.532")
                    diffFirst().isEqualTo(Time("1.784"))
                    diffPrevious().isEqualTo(Time("0.123"))
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
    fun `It should calculate pax results for LSCC 2019 Simplified Event 3`() {
        val eventContext = TestEventContexts.Lscc2019Simplified.points3
        val calculator = createPaxEventResultsCalculator(eventContext)
        
        val actual = calculator.calculate()

        assertThat(actual).all {
            hasType(StandardEventResultsTypes.pax)
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
                    score().hasValue("66.553")
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
                    score().hasValue("69.253")
                    diffFirst().isEqualTo(Time("2.700"))
                    diffPrevious().isEqualTo(Time("2.700"))
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
                    score().hasValue("71.978")
                    diffFirst().isEqualTo(Time("5.425"))
                    diffPrevious().isEqualTo(Time("2.725"))
                }
                index(3).all {
                    hasPosition(4)
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
                    score().hasValue("72.120")
                    diffFirst().isEqualTo(Time("5.567"))
                    diffPrevious().isEqualTo(Time("0.142"))
                }
                index(4).all {
                    hasPosition(5)
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
                    score().hasValue("72.964")
                    diffFirst().isEqualTo(Time("6.411"))
                    diffPrevious().isEqualTo(Time("0.844"))
                }
                index(5).all {
                    hasPosition(6)
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
                    score().hasValue("78.846")
                    diffFirst().isEqualTo(Time("12.293"))
                    diffPrevious().isEqualTo(Time("5.882"))
                }
                index(6).all {
                    hasPosition(7)
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
                    score().hasValue("79.069")
                    diffFirst().isEqualTo(Time("12.516"))
                    diffPrevious().isEqualTo(Time("0.223"))
                }
                index(7).all {
                    hasPosition(8)
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
                    score().hasValue("80.614")
                    diffFirst().isEqualTo(Time("14.061"))
                    diffPrevious().isEqualTo(Time("1.545"))
                }
            }
        }
    }

    enum class LifecycleCaseFixtures(
        val eventContext: EventContext,
        val expected: OverallEventResults
    ) {
        CREATE_NO_PARTICIPANTS_YET(
            eventContext = TestEventContexts.LifecycleCases.Create.noParticipantsYet,
            expected = TestOverallPaxEventResults.LifecyclePhases.Create.noParticipantsYet
        ),
        CREATE_RUNS_WITHOUT_PARTICIPANTS(
            eventContext = TestEventContexts.LifecycleCases.Create.runsWithoutParticipants,
            expected = TestOverallPaxEventResults.LifecyclePhases.Create.runsWithoutParticipants
        ),
        CREATE_SOME_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Create.someParticipantsWithSomeRuns,
            expected = TestOverallPaxEventResults.LifecyclePhases.Create.someParticipantsWithSomeRuns
        ),
        CREATE_SOME_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Create.someParticipantsWithAllRuns,
            expected = TestOverallPaxEventResults.LifecyclePhases.Create.someParticipantsWithAllRuns
        ),
        CREATE_ALL_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Create.allParticipantsWithAllRuns,
            expected = TestOverallPaxEventResults.LifecyclePhases.Create.allParticipantsWithAllRuns
        )
    }

    @ParameterizedTest
    @EnumSource
    fun `It should calculate expected results for events in various states`(params: LifecycleCaseFixtures) {
        val subject = createPaxEventResultsCalculator(params.eventContext)

        val actual = subject.calculate()

        assertThat(actual).isEqualTo(params.expected)
    }

    private fun createPaxEventResultsCalculator(eventContext: EventContext): PaxEventResultsCalculator {
        val standardPenaltyFactory = StandardPenaltyFactory(eventContext.event.policy)
        return PaxEventResultsCalculator(
            eventContext = eventContext,
            scoredRunsComparator = ParticipantResult.ScoredRunsComparator(eventContext.extendedParameters.runsPerParticipant),
            runEligibilityQualifier = RunEligibilityQualifier(),
            runScoreFactory = when (eventContext.event.policy.paxTimeStyle) {
                PaxTimeStyle.FAIR -> PaxTimeRunScoreFactory(standardPenaltyFactory)
                PaxTimeStyle.LEGACY_BUGGED -> LegacyBuggedPaxTimeRunScoreFactory(standardPenaltyFactory)
            },
            finalScoreFactory = when (eventContext.event.policy.finalScoreStyle) {
                FinalScoreStyle.AUTOCROSS -> AutocrossFinalScoreFactory()
                FinalScoreStyle.RALLYCROSS -> RallycrossFinalScoreFactory()
            }
        )
    }
}