package org.coner.trailer.datasource.crispyfish.eventresults

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import org.coner.trailer.*
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.datasource.crispyfish.eventsresults.GroupedResultsReportCreator
import org.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import org.coner.trailer.eventresults.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi

@ExperimentalPathApi
class GroupedResultsReportCreatorTest {

    @TempDir lateinit var fixtureRoot: Path

    @Test
    fun `It should create from registration data for LSCC 2019 event 1`() {
        val season = SeasonFixture.Lscc2019Simplified(fixtureRoot)
        val event = season.event1
        val context = CrispyFishEventMappingContext(
            allClassDefinitions = season.classDefinitions,
            allRegistrations = event.registrations(),
            runCount = event.runCount
        )
        val scoredRunsComparator = ParticipantResult.ScoredRunsComparator(
            runCount = event.runCount
        )
        val subject = GroupedResultsReportCreator(
            participantResultMapper = event.groupedParticipantResultMapper,
            scoredRunsComparatorProvider = { scoredRunsComparator }
        )

        val actual = subject.createFromRegistrationData(event.coreSeasonEvent.event.crispyFish!!, context)

        assertThat(actual).all {
            hasType(StandardResultsTypes.grouped)
            resultsForGroupingAbbreviation("HS").isNotNull().all {
                hasSize(2)
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Anastasia")
                        hasLastName("Rigler")
                        signage().isNotNull().all {
                            grouping().isSingular().hasAbbreviation("HS")
                            number().isEqualTo("130")
                        }
                    }

                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Rebecca")
                        hasLastName("Jackson")
                        signage().isNotNull().all {
                            grouping().isSingular().hasAbbreviation("HS")
                            number().isEqualTo("1")
                        }
                    }
                }
            }
            resultsForGroupingAbbreviation("STR").isNotNull().all {
                hasSize(2)
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Eugene")
                        hasLastName("Drake")
                        signage().isNotNull().all {
                            grouping().isSingular().hasAbbreviation("STR")
                            number().isEqualTo("1")
                        }
                    }
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Jimmy")
                        hasLastName("Mckenzie")
                        signage().isNotNull().all {
                            grouping().isSingular().hasAbbreviation("STR")
                            number().isEqualTo("23")
                        }
                    }
                }
            }
            resultsForGroupingAbbreviation("NOV").isNotNull().all {
                hasSize(3)
                each {
                    it.participant()
                        .signage().isNotNull()
                        .grouping().isPaired()
                        .first().isNotNull().hasAbbreviation("NOV")
                }
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Brandy")
                        hasLastName("Huff")
                        signage().isNotNull().all {
                            grouping().isPaired().second().isNotNull().hasAbbreviation("BS")
                            number().isEqualTo("177")
                        }
                    }
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Bryant")
                        hasLastName("Moran")
                        signage().isNotNull().all {
                            grouping().isPaired().second().isNotNull().hasAbbreviation("ES")
                            number().isEqualTo("58")
                        }
                    }
                }
                index(2).all {
                    hasPosition(3)
                    participant().all {
                        hasFirstName("Dominic")
                        hasLastName("Rogers")
                        signage().isNotNull().all {
                            grouping().isPaired().second().isNotNull().hasAbbreviation("ES")
                            number().isEqualTo("18")
                        }
                    }
                }
            }
        }
    }

    @Test
    fun `It should create from registration data for LSCC 2019 event 2`() {
        val season = SeasonFixture.Lscc2019Simplified(fixtureRoot)
        val event = season.event2
        val context = CrispyFishEventMappingContext(
            allClassDefinitions = season.classDefinitions,
            allRegistrations = event.registrations(),
            runCount = event.runCount
        )
        val scoredRunsComparator = ParticipantResult.ScoredRunsComparator(
            runCount = event.runCount
        )
        val subject = GroupedResultsReportCreator(
            participantResultMapper = event.groupedParticipantResultMapper,
            scoredRunsComparatorProvider = { scoredRunsComparator }
        )

        val actual = subject.createFromRegistrationData(event.coreSeasonEvent.event.crispyFish!!, context)

        assertThat(actual).all {
            hasType(StandardResultsTypes.grouped)
            resultsForGroupingAbbreviation("HS").isNotNull().all {
                hasSize(1)
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Anastasia")
                        hasLastName("Rigler")
                        signage().isNotNull().all {
                            grouping().isSingular().hasAbbreviation("HS")
                            number().isEqualTo("130")
                        }
                    }

                }
            }
            resultsForGroupingAbbreviation("STR").isNotNull().all {
                hasSize(2)
                each {
                    it.participant()
                        .signage().isNotNull()
                        .grouping().isSingular().hasAbbreviation("STR")
                }
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Rebecca")
                        hasLastName("Jackson")
                        signage().isNotNull().number().isEqualTo("8")
                    }
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Jimmy")
                        hasLastName("Mckenzie")
                        signage().isNotNull().number().isEqualTo("23")
                    }
                }
            }
            resultsForGroupingAbbreviation("NOV").isNotNull().all {
                hasSize(3)
                each {
                    it.participant().signage().isNotNull()
                        .grouping().isPaired()
                        .first().isNotNull().hasAbbreviation("NOV")
                }
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Brandy")
                        hasLastName("Huff")
                        signage().isNotNull().all {
                            grouping().isPaired().second().isNotNull().hasAbbreviation("BS")
                            number().isEqualTo("52")
                        }
                    }
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Dominic")
                        hasLastName("Rogers")
                        signage().isNotNull().all {
                            grouping().isPaired().second().isNotNull().hasAbbreviation("ES")
                            number().isEqualTo("18")
                        }
                    }
                }
                index(2).all {
                    hasPosition(3)
                    participant().all {
                        hasFirstName("Bennett")
                        hasLastName("Pantone")
                        signage().isNotNull().all {
                            grouping().isPaired().second().isNotNull().hasAbbreviation("CS")
                            number().isEqualTo("20")
                        }
                    }
                }
            }
        }
    }

    @Test
    fun `It should create from registration data for LSCC 2019 event 3`() {
        val season = SeasonFixture.Lscc2019Simplified(fixtureRoot)
        val event = season.event3
        val context = CrispyFishEventMappingContext(
            allClassDefinitions = season.classDefinitions,
            allRegistrations = event.registrations(),
            runCount = event.runCount
        )
        val scoredRunsComparator = ParticipantResult.ScoredRunsComparator(
            runCount = event.runCount
        )
        val subject = GroupedResultsReportCreator(
            participantResultMapper = event.groupedParticipantResultMapper,
            scoredRunsComparatorProvider = { scoredRunsComparator }
        )

        val actual = subject.createFromRegistrationData(event.coreSeasonEvent.event.crispyFish!!, context)

        assertThat(actual).all {
            hasType(StandardResultsTypes.grouped)
            resultsForGroupingAbbreviation("HS").isNotNull().all {
                hasSize(1)
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Anastasia")
                        hasLastName("Rigler")
                        signage().isNotNull().all {
                            grouping().isSingular().hasAbbreviation("HS")
                            number().isEqualTo("130")
                        }
                    }
                }
            }
            resultsForGroupingAbbreviation("STR").isNotNull().all {
                hasSize(3)
                each {
                    it.participant().signage().isNotNull()
                        .grouping().isSingular().hasAbbreviation("STR")
                }
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Rebecca")
                        hasLastName("Jackson")
                        signage().isNotNull().number().isEqualTo("8")
                    }
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Jimmy")
                        hasLastName("Mckenzie")
                        signage().isNotNull().number().isEqualTo("23")
                    }
                }
                index(2).all {
                    hasPosition(3)
                    participant().all {
                        hasFirstName("Eugene")
                        hasLastName("Drake")
                        signage().isNotNull().number().isEqualTo("1")
                    }
                }
            }
            resultsForGroupingAbbreviation("NOV").isNotNull().all {
                hasSize(4)
                each {
                    it.participant().signage().isNotNull()
                        .grouping().isPaired()
                        .first().isNotNull().hasAbbreviation("NOV")
                }
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Brandy")
                        hasLastName("Huff")
                        signage().isNotNull().all {
                            grouping().isPaired().second().isNotNull().hasAbbreviation("BS")
                            number().isEqualTo("52")
                        }
                    }
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Bryant")
                        hasLastName("Moran")
                        signage().isNotNull().all {
                            grouping().isPaired().second().isNotNull().hasAbbreviation("GS")
                            number().isEqualTo("58")
                        }
                    }
                }
                index(2).all {
                    hasPosition(3)
                    participant().all {
                        hasFirstName("Dominic")
                        hasLastName("Rogers")
                        signage().isNotNull().all {
                            grouping().isPaired().second().isNotNull().hasAbbreviation("ES")
                            number().isEqualTo("18")
                        }
                    }
                }
                index(3).all {
                    hasPosition(4)
                    participant().all {
                        hasFirstName("Bennett")
                        hasLastName("Pantone")
                        signage().isNotNull().all {
                            grouping().isPaired().second().isNotNull().hasAbbreviation("CS")
                            number().isEqualTo("20")
                        }
                    }
                }
            }
        }
    }

}
