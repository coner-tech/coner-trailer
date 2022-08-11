package tech.coner.trailer.eventresults

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import org.junit.jupiter.api.Test
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