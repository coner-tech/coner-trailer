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
import kotlin.io.path.ExperimentalPathApi

@ExperimentalPathApi
class GroupEventResultsFactoryTest {

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
        val subject = GroupedEventResultsFactory(
            groupParticipantResultMapper = event.groupedParticipantResultMapper,
            rawTimeParticipantResultMapper = event.rawTimeParticipantResultMapper,
            scoredRunsComparatorProvider = { scoredRunsComparator }
        )

        val actual = subject.factory(
            eventCrispyFishMetadata = event.coreSeasonEvent.event.crispyFish!!,
            allClassesByAbbreviation = TestClasses.Lscc2019.allByAbbreviation,
            context = context
        )

        assertThat(actual).all {
            hasType(StandardEventResultsTypes.grouped)
            resultsForGroupAbbreviation("HS").isNotNull().all {
                hasSize(2)
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Anastasia")
                        hasLastName("Rigler")
                        classing().isNotNull().all {
                            group().isNull()
                            handicap().hasAbbreviation("HS")
                        }
                        hasNumber("130")
                    }

                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Rebecca")
                        hasLastName("Jackson")
                        classing().isNotNull().all {
                            group().isNull()
                            handicap().hasAbbreviation("HS")
                        }
                        hasNumber("1")
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
                        classing().isNotNull().all {
                            group().isNull()
                            handicap().hasAbbreviation("STR")
                        }
                        hasNumber("1")
                    }
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
                }
            }
            resultsForGroupAbbreviation("NOV").isNotNull().all {
                hasSize(3)
                each {
                    it.participant()
                        .classing().isNotNull()
                        .group().isNotNull().hasAbbreviation("NOV")
                }
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Brandy")
                        hasLastName("Huff")
                        classing().isNotNull().handicap().hasAbbreviation("BS")
                        hasNumber("177")
                    }
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Bryant")
                        hasLastName("Moran")
                        classing().isNotNull().handicap().hasAbbreviation("ES")
                        hasNumber("58")
                    }
                }
                index(2).all {
                    hasPosition(3)
                    participant().all {
                        hasFirstName("Dominic")
                        hasLastName("Rogers")
                        classing().isNotNull().handicap().hasAbbreviation("ES")
                        hasNumber("18")
                    }
                }
            }
            parentClassTopTimes().all {
                hasSize(2)
                index(0).all {
                    parent().isSameAs(TestClasses.Lscc2019.STREET_TOURING)
                    participantResult().all {
                        hasPosition(1)
                        participant().isEqualTo(TestParticipants.Lscc2019Points1Simplified.EUGENE_DRAKE)
                    }
                }
                index(1).all {
                    parent().isSameAs(TestClasses.Lscc2019.STREET)
                    participantResult().all {
                        hasPosition(1)
                        participant().isEqualTo(TestParticipants.Lscc2019Points1Simplified.BRANDY_HUFF)
                    }
                }
            }
        }
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
        val subject = GroupedEventResultsFactory(
            groupParticipantResultMapper = event.groupedParticipantResultMapper,
            rawTimeParticipantResultMapper = event.rawTimeParticipantResultMapper,
            scoredRunsComparatorProvider = { scoredRunsComparator }
        )

        val actual = subject.factory(
            eventCrispyFishMetadata = event.coreSeasonEvent.event.crispyFish!!,
            allClassesByAbbreviation = TestClasses.Lscc2019.allByAbbreviation,
            context = context
        )

        assertThat(actual).all {
            hasType(StandardEventResultsTypes.grouped)
            resultsForGroupAbbreviation("HS").isNotNull().all {
                hasSize(1)
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Anastasia")
                        hasLastName("Rigler")
                        classing().isNotNull().all { 
                            group().isNull()
                            handicap().hasAbbreviation("HS")
                        }
                        hasNumber("130")
                    }
                }
            }
            resultsForGroupAbbreviation("STR").isNotNull().all {
                hasSize(2)
                each {
                    it.participant()
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
                        hasNumber("8")
                    }
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Jimmy")
                        hasLastName("Mckenzie")
                        hasNumber("23")
                    }
                }
            }
            resultsForGroupAbbreviation("NOV").isNotNull().all {
                hasSize(3)
                each {
                    it.participant().classing().isNotNull().group().isNotNull().hasAbbreviation("NOV")
                }
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Brandy")
                        hasLastName("Huff")
                        classing().isNotNull().handicap().hasAbbreviation("BS")
                        hasNumber("52")
                    }
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Dominic")
                        hasLastName("Rogers")
                        classing().isNotNull().handicap().hasAbbreviation("ES")
                        hasNumber("18")
                    }
                }
                index(2).all {
                    hasPosition(3)
                    participant().all {
                        hasFirstName("Bennett")
                        hasLastName("Pantone")
                        classing().isNotNull().handicap().hasAbbreviation("CS")
                        hasNumber("20")
                    }
                }
            }
            parentClassTopTimes().all {
                hasSize(2)
                index(0).all {
                    parent().isSameAs(TestClasses.Lscc2019.STREET_TOURING)
                    participantResult().participant().isEqualTo(TestParticipants.Lscc2019Points2Simplified.REBECCA_JACKSON)
                }
                index(1).all {
                    parent().isSameAs(TestClasses.Lscc2019.STREET)
                    participantResult().participant().isEqualTo(TestParticipants.Lscc2019Points2Simplified.BRANDY_HUFF)
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
        val subject = GroupedEventResultsFactory(
            groupParticipantResultMapper = event.groupedParticipantResultMapper,
            rawTimeParticipantResultMapper = event.rawTimeParticipantResultMapper,
            scoredRunsComparatorProvider = { scoredRunsComparator }
        )

        val actual = subject.factory(
            eventCrispyFishMetadata = event.coreSeasonEvent.event.crispyFish!!,
            allClassesByAbbreviation = TestClasses.Lscc2019.allByAbbreviation,
            context = context
        )

        assertThat(actual).all {
            hasType(StandardEventResultsTypes.grouped)
            resultsForGroupAbbreviation("HS").isNotNull().all {
                hasSize(1)
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Anastasia")
                        hasLastName("Rigler")
                        classing().isNotNull().handicap().hasAbbreviation("HS")
                        hasNumber("130")
                    }
                }
            }
            resultsForGroupAbbreviation("STR").isNotNull().all {
                hasSize(3)
                each {
                    it.participant().classing().isNotNull().all {
                        group().isNull()
                        handicap().hasAbbreviation("STR")
                    }
                }
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Rebecca")
                        hasLastName("Jackson")
                        hasNumber("8")
                    }
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Jimmy")
                        hasLastName("Mckenzie")
                        hasNumber("23")
                    }
                }
                index(2).all {
                    hasPosition(3)
                    participant().all {
                        hasFirstName("Eugene")
                        hasLastName("Drake")
                        hasNumber("1")
                    }
                }
            }
            resultsForGroupAbbreviation("NOV").isNotNull().all {
                hasSize(4)
                each {
                    it.participant().classing().isNotNull()
                        .group().isNotNull().hasAbbreviation("NOV")
                }
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Brandy")
                        hasLastName("Huff")
                        classing().isNotNull()
                            .handicap().hasAbbreviation("BS")
                        classing().isNotNull().handicap().hasAbbreviation("BS")
                        hasNumber("52")
                    }
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Bryant")
                        hasLastName("Moran")
                        classing().isNotNull().handicap().hasAbbreviation("GS")
                        hasNumber("58")
                    }
                }
                index(2).all {
                    hasPosition(3)
                    participant().all {
                        hasFirstName("Dominic")
                        hasLastName("Rogers")
                        classing().isNotNull().handicap().hasAbbreviation("ES")
                        hasNumber("18")
                    }
                }
                index(3).all {
                    hasPosition(4)
                    participant().all {
                        hasFirstName("Bennett")
                        hasLastName("Pantone")
                        classing().isNotNull().handicap().hasAbbreviation("CS")
                        hasNumber("20")
                    }
                }
            }
            parentClassTopTimes().all {
                hasSize(2)
                index(0).all {
                    parent().isSameAs(TestClasses.Lscc2019.STREET_TOURING)
                    participantResult().participant().isEqualTo(TestParticipants.Lscc2019Points3Simplified.REBECCA_JACKSON)
                }
                index(1).all {
                    parent().isSameAs(TestClasses.Lscc2019.STREET)
                    participantResult().participant().isEqualTo(TestParticipants.Lscc2019Points3Simplified.BRANDY_HUFF)
                }
            }
        }
    }

}
