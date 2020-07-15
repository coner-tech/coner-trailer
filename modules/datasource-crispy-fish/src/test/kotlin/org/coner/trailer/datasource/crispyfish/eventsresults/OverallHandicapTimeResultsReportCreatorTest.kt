package org.coner.trailer.datasource.crispyfish.eventsresults

import assertk.all
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.index
import org.coner.trailer.*
import org.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import org.coner.trailer.eventresults.*
import org.junit.jupiter.api.Test

class OverallHandicapTimeResultsReportCreatorTest {

    @Test
    fun `It should create from registration data for LSCC 2019 event 1`() {
        val season = SeasonFixture.Lscc2019Simplified
        val event = season.event1

        val actual = OverallHandicapTimeResultsReportCreator(event.participantResultMapper).createFromRegistrationData(
                crispyFishRegistrations = event.registrations(season)
        )

        assertThat(actual).all {
            hasType(StandardResultsTypes.overallHandicapTime)
            participantResults().all {
                hasSize(7)
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Brandy")
                        hasLastName("Huff")
                        grouping().isPaired().all {
                            first().hasAbbreviation("NOV")
                            second().hasAbbreviation("BS")
                        }
                        hasNumber("177")
                    }
                    score().hasValue("39.297")
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Eugene")
                        hasLastName("Drake")
                        grouping().isSingular().hasAbbreviation("STR")
                        hasNumber("1")
                    }
                    score().hasValue("39.318")
                }
                index(2).all {
                    hasPosition(3)
                    participant().all {
                        hasFirstName("Anastasia")
                        hasLastName("Rigler")
                        grouping().isSingular().hasAbbreviation("HS")
                        hasNumber("130")
                    }
                    score().hasValue("40.031")
                }
                index(3).all {
                    hasPosition(4)
                    participant().all {
                        hasFirstName("Rebecca")
                        hasLastName("Jackson")
                        grouping().isSingular().hasAbbreviation("HS")
                        hasNumber("1")
                    }
                    score().hasValue("40.098")
                }
                index(4).all {
                    hasPosition(5)
                    participant().all {
                        hasFirstName("Jimmy")
                        hasLastName("Mckenzie")
                        grouping().isSingular().hasAbbreviation("STR")
                        hasNumber("23")
                    }
                    score().hasValue("40.293")
                }
                index(5).all {
                    hasPosition(6)
                    participant().all {
                        hasFirstName("Bryant")
                        hasLastName("Moran")
                        grouping().isPaired().all {
                            first().hasAbbreviation("NOV")
                            second().hasAbbreviation("ES")
                        }
                        hasNumber("58")
                    }
                    score().hasValue("41.186")
                }
                index(6).all {
                    hasPosition(7)
                    participant().all {
                        hasFirstName("Dominic")
                        hasLastName("Rogers")
                        grouping().isPaired().all {
                            first().hasAbbreviation("NOV")
                            second().hasAbbreviation("ES")
                        }
                        hasNumber("18")
                    }
                    score().hasValue("41.380")
                }
            }
        }
    }


    @Test
    fun `It should create from registration data for LSCC 2019 event 2`() {
        val season = SeasonFixture.Lscc2019Simplified
        val event = season.event2
        val registrations = event.registrations(season)

        val actual = OverallHandicapTimeResultsReportCreator(event.participantResultMapper).createFromRegistrationData(
                crispyFishRegistrations = registrations
        )

        assertThat(actual).all {
            hasType(StandardResultsTypes.overallHandicapTime)
            participantResults().all {
                hasSize(6)
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Rebecca")
                        hasLastName("Jackson")
                        grouping().isSingular().hasAbbreviation("STR")
                        hasNumber("8")
                    }
                    score().hasValue("28.748")
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Jimmy")
                        hasLastName("Mckenzie")
                        grouping().isSingular().hasAbbreviation("STR")
                        hasNumber("23")
                    }
                    score().hasValue("29.925")
                }
                index(2).all {
                    hasPosition(3)
                    participant().all {
                        hasFirstName("Brandy")
                        hasLastName("Huff")
                        grouping().isPaired().all {
                            first().hasAbbreviation("NOV")
                            second().hasAbbreviation("BS")
                        }
                        hasNumber("52")
                    }
                    score().hasValue("30.017")
                }
                index(3).all {
                    hasPosition(4)
                    participant().all {
                        hasFirstName("Anastasia")
                        hasLastName("Rigler")
                        grouping().isSingular().hasAbbreviation("HS")
                        hasNumber("130")
                    }
                    score().hasValue("30.409")
                }
                index(4).all {
                    hasPosition(5)
                    participant().all {
                        hasFirstName("Dominic")
                        hasLastName("Rogers")
                        grouping().isPaired().all {
                            first().hasAbbreviation("NOV")
                            second().hasAbbreviation("ES")
                        }
                        hasNumber("18")
                    }
                    score().hasValue("30.532")
                }
                index(5).all {
                    hasPosition(6)
                    participant().all {
                        hasFirstName("Bennett")
                        hasLastName("Pantone")
                        grouping().isPaired().all {
                            first().hasAbbreviation("NOV")
                            second().hasAbbreviation("CS")
                        }
                        hasNumber("20")
                    }
                    score().hasDidNotFinish()
                }
            }
        }
    }


    @Test
    fun `It should create from registration data for LSCC 2019 event 3`() {
        val season = SeasonFixture.Lscc2019Simplified
        val event = season.event3
        val registrations = event.registrations(season)

        val actual = OverallHandicapTimeResultsReportCreator(event.participantResultMapper).createFromRegistrationData(
                crispyFishRegistrations = registrations
        )

        assertThat(actual).all {
            hasType(StandardResultsTypes.overallHandicapTime)
            participantResults().all {
                hasSize(8)
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Rebecca")
                        hasLastName("Jackson")
                        grouping().isSingular().hasAbbreviation("STR")
                        hasNumber("8")
                    }
                    score().hasValue("66.553")
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Jimmy")
                        hasLastName("Mckenzie")
                        grouping().isSingular().hasAbbreviation("STR")
                        hasNumber("23")
                    }
                    score().hasValue("69.253")
                }
                index(2).all {
                    hasPosition(3)
                    participant().all {
                        hasFirstName("Eugene")
                        hasLastName("Drake")
                        grouping().isSingular().hasAbbreviation("STR")
                        hasNumber("1")
                    }
                    score().hasValue("71.978")
                }
                index(3).all {
                    hasPosition(4)
                    participant().all {
                        hasFirstName("Anastasia")
                        hasLastName("Rigler")
                        grouping().isSingular().hasAbbreviation("HS")
                        hasNumber("130")
                    }
                    score().hasValue("72.120")
                }
                index(4).all {
                    hasPosition(5)
                    participant().all {
                        hasFirstName("Brandy")
                        hasLastName("Huff")
                        grouping().isPaired().all {
                            first().hasAbbreviation("NOV")
                            second().hasAbbreviation("BS")
                        }
                        hasNumber("52")
                    }
                    score().hasValue("72.964")
                }
                index(5).all {
                    hasPosition(6)
                    participant().all {
                        hasFirstName("Bryant")
                        hasLastName("Moran")
                        grouping().isPaired().all {
                            first().hasAbbreviation("NOV")
                            second().hasAbbreviation("GS")
                        }
                        hasNumber("58")
                    }
                    score().hasValue("78.846")
                }
                index(6).all {
                    hasPosition(7)
                    participant().all {
                        hasFirstName("Dominic")
                        hasLastName("Rogers")
                        grouping().isPaired().all {
                            first().hasAbbreviation("NOV")
                            second().hasAbbreviation("ES")
                        }
                        hasNumber("18")
                    }
                    score().hasValue("79.069")
                }
                index(7).all {
                    hasPosition(8)
                    participant().all {
                        hasFirstName("Bennett")
                        hasLastName("Pantone")
                        grouping().isPaired().all {
                            first().hasAbbreviation("NOV")
                            second().hasAbbreviation("CS")
                        }
                        hasNumber("20")
                    }
                    score().hasValue("80.614")
                }
            }
        }
    }
}