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

class OverallPaxTimeEventResultsCreatorTest {

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
        val subject = OverallPaxEventResultsFactory(
            participantResultMapper = event.paxTimeParticipantResultMapper,
            scoredRunsComparatorProvider = { scoredRunsComparator }
        )

        val actual = subject.factory(
            eventCrispyFishMetadata = event.coreSeasonEvent.event.crispyFish!!,
            allClassesByAbbreviation = TestClasses.Lscc2019.allByAbbreviation,
            context = context
        )

        assertThat(actual).isEqualTo(TestOverallPaxEventResults.Lscc2019Simplified.points1)
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
            runCount = event.runCount
        )
        val subject = OverallPaxEventResultsFactory(
            participantResultMapper = event.paxTimeParticipantResultMapper,
            scoredRunsComparatorProvider = { scoredRunsComparator }
        )

        val actual = subject.factory(
            eventCrispyFishMetadata = event.coreSeasonEvent.event.crispyFish!!,
            allClassesByAbbreviation = TestClasses.Lscc2019.allByAbbreviation,
            context = context
        )

        assertThat(actual).all {
            hasType(StandardEventResultsTypes.pax)
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
                    score().hasValue("28.748")
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
                    score().hasValue("29.925")
                    diffFirst().isEqualTo("1.177")
                    diffPrevious().isEqualTo("1.177")
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
                    score().hasValue("30.017")
                    diffFirst().isEqualTo("1.269")
                    diffPrevious().isEqualTo("0.092")
                }
                index(3).all {
                    hasPosition(4)
                    participant().all {
                        hasFirstName("Anastasia")
                        hasLastName("Rigler")
                        classing().isNotNull().all {
                            group().isNull()
                            handicap().hasAbbreviation("HS")
                        }
                        hasNumber("130")
                    }
                    score().hasValue("30.409")
                    diffFirst().isEqualTo("1.661")
                    diffPrevious().isEqualTo("0.392")
                }
                index(4).all {
                    hasPosition(5)
                    participant().all {
                        hasFirstName("Dominic")
                        hasLastName("Rogers")
                        classing().isNotNull().all {
                            group().isNotNull().hasAbbreviation("NOV")
                            handicap().hasAbbreviation("ES")
                        }
                        hasNumber("18")
                    }
                    score().hasValue("30.532")
                    diffFirst().isEqualTo("1.784")
                    diffPrevious().isEqualTo("0.123")
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
        val subject = OverallPaxEventResultsFactory(
            participantResultMapper = event.paxTimeParticipantResultMapper,
            scoredRunsComparatorProvider = { scoredRunsComparator }
        )

        val actual = subject.factory(
            eventCrispyFishMetadata = event.coreSeasonEvent.event.crispyFish!!,
            allClassesByAbbreviation = TestClasses.Lscc2019.allByAbbreviation,
            context = context
        )

        assertThat(actual).all {
            hasType(StandardEventResultsTypes.pax)
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
                    score().hasValue("66.553")
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
                    score().hasValue("69.253")
                    diffFirst().isEqualTo("2.700")
                    diffPrevious().isEqualTo("2.700")
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
                    score().hasValue("71.978")
                    diffFirst().isEqualTo("5.425")
                    diffPrevious().isEqualTo("2.725")
                }
                index(3).all {
                    hasPosition(4)
                    participant().all {
                        hasFirstName("Anastasia")
                        hasLastName("Rigler")
                        classing().isNotNull().all {
                            group().isNull()
                            handicap().hasAbbreviation("HS")
                        }
                        hasNumber("130")
                    }
                    score().hasValue("72.120")
                    diffFirst().isEqualTo("5.567")
                    diffPrevious().isEqualTo("0.142")
                }
                index(4).all {
                    hasPosition(5)
                    participant().all {
                        hasFirstName("Brandy")
                        hasLastName("Huff")
                        classing().isNotNull().all {
                            group().isNotNull().hasAbbreviation("NOV")
                            handicap().hasAbbreviation("BS")
                        }
                        hasNumber("52")
                    }
                    score().hasValue("72.964")
                    diffFirst().isEqualTo("6.411")
                    diffPrevious().isEqualTo("0.844")
                }
                index(5).all {
                    hasPosition(6)
                    participant().all {
                        hasFirstName("Bryant")
                        hasLastName("Moran")
                        classing().isNotNull().all {
                            group().isNotNull().hasAbbreviation("NOV")
                            handicap().hasAbbreviation("GS")
                        }
                        hasNumber("58")
                    }
                    score().hasValue("78.846")
                    diffFirst().isEqualTo("12.293")
                    diffPrevious().isEqualTo("5.882")
                }
                index(6).all {
                    hasPosition(7)
                    participant().all {
                        hasFirstName("Dominic")
                        hasLastName("Rogers")
                        classing().isNotNull().all {
                            group().isNotNull().hasAbbreviation("NOV")
                            handicap().hasAbbreviation("ES")
                        }
                        hasNumber("18")
                    }
                    score().hasValue("79.069")
                    diffFirst().isEqualTo("12.516")
                    diffPrevious().isEqualTo("0.223")
                }
                index(7).all {
                    hasPosition(8)
                    participant().all {
                        hasFirstName("Bennett")
                        hasLastName("Pantone")
                        classing().isNotNull().all {
                            group().isNotNull().hasAbbreviation("NOV")
                            handicap().hasAbbreviation("CS")
                        }
                        hasNumber("20")
                    }
                    score().hasValue("80.614")
                    diffFirst().isEqualTo("14.061")
                    diffPrevious().isEqualTo("1.545")
                }
            }
        }
    }
}