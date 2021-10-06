package org.coner.trailer.datasource.crispyfish.eventresults

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import org.coner.trailer.*
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import org.coner.trailer.eventresults.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path

class OverallRawTimeEventResultsCreatorTest {

    @TempDir lateinit var fixtureRoot: Path

    @Test
    fun `It should create from registration data for LSCC 2019 event 1`() {
        val season = SeasonFixture.Lscc2019Simplified(fixtureRoot)
        val event = season.event1
        val allRegistrations = event.registrations()
        val context = CrispyFishEventMappingContext(
            allClassDefinitions = season.classDefinitions,
            allRegistrations = allRegistrations,
            allRuns = event.runs(allRegistrations),
            runCount = event.runCount
        )
        val scoredRunsComparator = ParticipantResult.ScoredRunsComparator(
            runCount = event.runCount
        )
        val subject = OverallRawEventResultsFactory(
            participantResultMapper = event.rawTimeParticipantResultMapper,
            scoredRunsComparatorProvider = { scoredRunsComparator }
        )

        val actual = subject.factory(
            eventCrispyFishMetadata = event.coreSeasonEvent.event.crispyFish!!,
            allClassesByAbbreviation = TestClasses.Lscc2019.allByAbbreviation,
            context = context
        )

        assertThat(actual).isEqualTo(TestOverallRawEventResults.Lscc2019Simplified.points1)
    }

    @Test
    fun `It should create from registration data for LSCC 2019 event 2`() {
        val season = SeasonFixture.Lscc2019Simplified(fixtureRoot)
        val event = season.event2
        val allRegistrations = event.registrations()
        val context = CrispyFishEventMappingContext(
            allClassDefinitions = season.classDefinitions,
            allRegistrations = allRegistrations,
            allRuns = event.runs(allRegistrations),
            runCount = event.runCount
        )
        val scoredRunsComparator = ParticipantResult.ScoredRunsComparator(
            runCount = 4
        )
        val subject = OverallRawEventResultsFactory(
            participantResultMapper = event.rawTimeParticipantResultMapper,
            scoredRunsComparatorProvider = { scoredRunsComparator }
        )

        val actual = subject.factory(
            eventCrispyFishMetadata = event.coreSeasonEvent.event.crispyFish!!,
            allClassesByAbbreviation = TestClasses.Lscc2019.allByAbbreviation,
            context = context
        )

        assertThat(actual).all {
            hasType(StandardEventResultsTypes.raw)
            participantResults().all {
                hasSize(6)
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Rebecca")
                        hasLastName("Jackson")
                        classing().isNotNull().all {
                            group().isNull()
                            handicap().hasAbbreviation("STR")
                        }
                        hasNumber("8")
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
                        classing().isNotNull().all {
                            group().isNull()
                            handicap().hasAbbreviation("STR")
                        }
                        hasNumber("23")
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
                        classing().isNotNull().all {
                            group().isNotNull().hasAbbreviation("NOV")
                            handicap().hasAbbreviation("BS")
                        }
                        hasNumber("52")
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
                        classing().isNotNull().all {
                            group().isNotNull().hasAbbreviation("NOV")
                            handicap().hasAbbreviation("ES")
                        }
                        hasNumber("18")
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
                        classing().isNotNull().all {
                            group().isNull()
                            handicap().hasAbbreviation("HS")
                        }
                        hasNumber("130")
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
                        classing().isNotNull().all {
                            group().isNotNull().hasAbbreviation("NOV")
                            handicap().hasAbbreviation("CS")
                        }
                        hasNumber("20")
                    }
                    score().hasDidNotFinish()
                    diffFirst().isNull()
                    diffPrevious().isNull()
                }
            }
        }
    }


    @Test
    fun `It should create from registration data for LSCC 2019 event 3`() {
        val season = SeasonFixture.Lscc2019Simplified(fixtureRoot)
        val event = season.event3
        val allRegistrations = event.registrations()
        val context = CrispyFishEventMappingContext(
            allClassDefinitions = season.classDefinitions,
            allRegistrations = allRegistrations,
            allRuns = event.runs(allRegistrations),
            runCount = event.runCount
        )
        val scoredRunsComparator = ParticipantResult.ScoredRunsComparator(
            runCount = event.runCount
        )
        val subject = OverallRawEventResultsFactory(
            participantResultMapper = event.rawTimeParticipantResultMapper,
            scoredRunsComparatorProvider = { scoredRunsComparator }
        )

        val actual = subject.factory(
            eventCrispyFishMetadata = event.coreSeasonEvent.event.crispyFish!!,
            allClassesByAbbreviation = TestClasses.Lscc2019.allByAbbreviation,
            context = context
        )

        assertThat(actual).all {
            hasType(StandardEventResultsTypes.raw)
            participantResults().all {
                hasSize(8)
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Rebecca")
                        hasLastName("Jackson")
                        classing().isNotNull().all {
                            group().isNull()
                            handicap().hasAbbreviation("STR")
                        }
                        hasNumber("8")
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
                        classing().isNotNull().all {
                            group().isNull()
                            handicap().hasAbbreviation("STR")
                        }
                        hasNumber("23")
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
                        classing().isNotNull().all {
                            group().isNull()
                            handicap().hasAbbreviation("STR")
                        }
                        hasNumber("1")
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
                        classing().isNotNull().all {
                            group().isNotNull().hasAbbreviation("NOV")
                            handicap().hasAbbreviation("BS")
                        }
                        hasNumber("52")
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
                        classing().isNotNull().all {
                            group().isNull()
                            handicap().hasAbbreviation("HS")
                        }
                        hasNumber("130")
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
                        classing().isNotNull().all {
                            group().isNotNull().hasAbbreviation("NOV")
                            handicap().hasAbbreviation("CS")
                        }
                        hasNumber("20")
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
                        classing().isNotNull().all {
                            group().isNotNull().hasAbbreviation("NOV")
                            handicap().hasAbbreviation("GS")
                        }
                        hasNumber("58")
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
                        classing().isNotNull().all {
                            group().isNotNull().hasAbbreviation("NOV")
                            handicap().hasAbbreviation("ES")
                        }
                        hasNumber("18")
                    }
                    score().hasValue("100.215")
                    diffFirst().isEqualTo("19.739")
                    diffPrevious().isEqualTo("0.156")
                }
            }
        }
    }
}