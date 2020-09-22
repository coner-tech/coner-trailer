package org.coner.trailer.datasource.crispyfish.eventsresults

import assertk.all
import assertk.assertThat
import assertk.assertions.each
import assertk.assertions.hasSize
import assertk.assertions.index
import assertk.assertions.isNotNull
import org.coner.trailer.*
import org.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import org.coner.trailer.eventresults.*
import org.junit.jupiter.api.Test

class CompetitionGroupedResultsReportCreatorTest {


    @Test
    fun `It should create from registration data for LSCC 2019 event 1`() {
        val season = SeasonFixture.Lscc2019Simplified
        val event = season.event1

        val actual = CompetitionGroupedResultsReportCreator(event.participantResultMapper).createFromRegistrationData(
                crispyFishRegistrations = event.registrations(season)
        )

        assertThat(actual).all {
            hasType(StandardResultsTypes.competitionGrouped)
            resultsForGroupingAbbreviation("HS").isNotNull().all {
                hasSize(2)
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Anastasia")
                        hasLastName("Rigler")
                        grouping().isSingular().hasAbbreviation("HS")
                        hasNumber("130")
                    }

                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Rebecca")
                        hasLastName("Jackson")
                        grouping().isSingular().hasAbbreviation("HS")
                        hasNumber("1")
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
                        grouping().isSingular().hasAbbreviation("STR")
                        hasNumber("1")
                    }
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Jimmy")
                        hasLastName("Mckenzie")
                        grouping().isSingular().hasAbbreviation("STR")
                        hasNumber("23")
                    }
                }
            }
            resultsForGroupingAbbreviation("NOV").isNotNull().all {
                hasSize(3)
                each {
                    it.participant().grouping().isPaired().first().hasAbbreviation("NOV")
                }
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Brandy")
                        hasLastName("Huff")
                        grouping().isPaired().second().hasAbbreviation("BS")
                        hasNumber("177")
                    }
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Bryant")
                        hasLastName("Moran")
                        grouping().isPaired().second().hasAbbreviation("ES")
                        hasNumber("58")
                    }
                }
                index(2).all {
                    hasPosition(3)
                    participant().all {
                        hasFirstName("Dominic")
                        hasLastName("Rogers")
                        grouping().isPaired().second().hasAbbreviation("ES")
                        hasNumber("18")
                    }
                }
            }
        }
    }

    @Test
    fun `It should create from registration data for LSCC 2019 event 2`() {
        val season = SeasonFixture.Lscc2019Simplified
        val event = season.event2
        val registrations = event.registrations(season)

        val actual = CompetitionGroupedResultsReportCreator(event.participantResultMapper).createFromRegistrationData(
                crispyFishRegistrations = registrations
        )

        assertThat(actual).all {
            hasType(StandardResultsTypes.competitionGrouped)
            resultsForGroupingAbbreviation("HS").isNotNull().all {
                hasSize(1)
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Anastasia")
                        hasLastName("Rigler")
                        grouping().isSingular().hasAbbreviation("HS")
                        hasNumber("130")
                    }

                }
            }
            resultsForGroupingAbbreviation("STR").isNotNull().all {
                hasSize(2)
                each {
                    it.participant().grouping().isSingular().hasAbbreviation("STR")
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
            resultsForGroupingAbbreviation("NOV").isNotNull().all {
                hasSize(3)
                each {
                    it.participant().grouping().isPaired().first().hasAbbreviation("NOV")
                }
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Brandy")
                        hasLastName("Huff")
                        grouping().isPaired().second().hasAbbreviation("BS")
                        hasNumber("52")
                    }
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Dominic")
                        hasLastName("Rogers")
                        grouping().isPaired().second().hasAbbreviation("ES")
                        hasNumber("18")
                    }
                }
                index(2).all {
                    hasPosition(3)
                    participant().all {
                        hasFirstName("Bennett")
                        hasLastName("Pantone")
                        grouping().isPaired().second().hasAbbreviation("CS")
                        hasNumber("20")
                    }
                }
            }
        }
    }

    @Test
    fun `It should create from registration data for LSCC 2019 event 3`() {
        val season = SeasonFixture.Lscc2019Simplified
        val event = season.event3
        val registrations = event.registrations(season)

        val actual = CompetitionGroupedResultsReportCreator(event.participantResultMapper).createFromRegistrationData(
                crispyFishRegistrations = registrations
        )

        assertThat(actual).all {
            hasType(StandardResultsTypes.competitionGrouped)
            resultsForGroupingAbbreviation("HS").isNotNull().all {
                hasSize(1)
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Anastasia")
                        hasLastName("Rigler")
                        grouping().isSingular().hasAbbreviation("HS")
                        hasNumber("130")
                    }
                }
            }
            resultsForGroupingAbbreviation("STR").isNotNull().all {
                hasSize(3)
                each {
                    it.participant().grouping().isSingular().hasAbbreviation("STR")
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
            resultsForGroupingAbbreviation("NOV").isNotNull().all {
                hasSize(4)
                each {
                    it.participant().grouping().isPaired().first().hasAbbreviation("NOV")
                }
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Brandy")
                        hasLastName("Huff")
                        grouping().isPaired().second().hasAbbreviation("BS")
                        hasNumber("52")
                    }
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Bryant")
                        hasLastName("Moran")
                        grouping().isPaired().second().hasAbbreviation("GS")
                        hasNumber("58")
                    }
                }
                index(2).all {
                    hasPosition(3)
                    participant().all {
                        hasFirstName("Dominic")
                        hasLastName("Rogers")
                        grouping().isPaired().second().hasAbbreviation("ES")
                        hasNumber("18")
                    }
                }
                index(3).all {
                    hasPosition(4)
                    participant().all {
                        hasFirstName("Bennett")
                        hasLastName("Pantone")
                        grouping().isPaired().second().hasAbbreviation("CS")
                        hasNumber("20")
                    }
                }
            }
        }
    }

}
