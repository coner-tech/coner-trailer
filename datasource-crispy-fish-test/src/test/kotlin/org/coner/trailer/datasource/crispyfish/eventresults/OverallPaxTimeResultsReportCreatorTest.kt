package org.coner.trailer.datasource.crispyfish.eventresults

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import org.coner.trailer.*
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.datasource.crispyfish.eventsresults.OverallPaxTimeResultsReportCreator
import org.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import org.coner.trailer.eventresults.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi

@ExperimentalPathApi
class OverallPaxTimeResultsReportCreatorTest {

    @TempDir lateinit var fixtureRoot: Path

    @Test
    fun `It should create from registration data for LSCC 2019 event 1`() {
        val season = SeasonFixture.Lscc2019Simplified(fixtureRoot)
        val event = season.event1
        val context = CrispyFishEventMappingContext(
            allClassDefinitions = season.classDefinitions,
            allRegistrations = event.registrations()
        )

        val actual = OverallPaxTimeResultsReportCreator(event.paxTimeParticipantResultMapper)
            .createFromRegistrationData(event.coreSeasonEvent.event.crispyFish!!, context)

        assertThat(actual).all {
            hasType(StandardResultsTypes.pax)
            participantResults().all {
                hasSize(7)
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Brandy")
                        hasLastName("Huff")
                        signage().isNotNull().all {
                            grouping().isPaired().all {
                                first().isNotNull().hasAbbreviation("NOV")
                                second().isNotNull().hasAbbreviation("BS")
                            }
                            number().isEqualTo("177")
                        }
                    }
                    score().hasValue("39.297")
                    diffFirst().isNull()
                    diffPrevious().isNull()
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Eugene")
                        hasLastName("Drake")
                        signage().isNotNull().all {
                            grouping().isSingular().hasAbbreviation("STR")
                            number().isEqualTo("1")
                        }
                    }
                    score().hasValue("39.318")
                    diffFirst().isEqualTo("0.021")
                    diffPrevious().isEqualTo("0.021")
                }
                index(2).all {
                    hasPosition(3)
                    participant().all {
                        hasFirstName("Anastasia")
                        hasLastName("Rigler")
                        signage().isNotNull().all {
                            grouping().isSingular().hasAbbreviation("HS")
                            number().isEqualTo("130")
                        }
                    }
                    score().hasValue("40.031")
                    diffFirst().isEqualTo("0.734")
                    diffPrevious().isEqualTo("0.713")
                }
                index(3).all {
                    hasPosition(4)
                    participant().all {
                        hasFirstName("Rebecca")
                        hasLastName("Jackson")
                        signage().isNotNull().all {
                            grouping().isSingular().hasAbbreviation("HS")
                            number().isEqualTo("1")
                        }
                    }
                    score().hasValue("40.098")
                    diffFirst().isEqualTo("0.801")
                    diffPrevious().isEqualTo("0.067")
                }
                index(4).all {
                    hasPosition(5)
                    participant().all {
                        hasFirstName("Jimmy")
                        hasLastName("Mckenzie")
                        signage().isNotNull().all {
                            grouping().isSingular().hasAbbreviation("STR")
                            number().isEqualTo("23")
                        }
                    }
                    score().hasValue("40.293")
                    diffFirst().isEqualTo("0.996")
                    diffPrevious().isEqualTo("0.195")
                }
                index(5).all {
                    hasPosition(6)
                    participant().all {
                        hasFirstName("Bryant")
                        hasLastName("Moran")
                        signage().isNotNull().all {
                            grouping().isPaired().all {
                                first().isNotNull().hasAbbreviation("NOV")
                                second().isNotNull().hasAbbreviation("ES")
                            }
                            number().isEqualTo("58")
                        }
                    }
                    score().hasValue("41.186")
                    diffFirst().isEqualTo("1.889")
                    diffPrevious().isEqualTo("0.893")
                }
                index(6).all {
                    hasPosition(7)
                    participant().all {
                        hasFirstName("Dominic")
                        hasLastName("Rogers")
                        signage().isNotNull().all {
                            grouping().isPaired().all {
                                first().isNotNull().hasAbbreviation("NOV")
                                second().isNotNull().hasAbbreviation("ES")
                            }
                            number().isEqualTo("18")
                        }
                    }
                    score().hasValue("41.380")
                    diffFirst().isEqualTo("2.083")
                    diffPrevious().isEqualTo("0.194")
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
            allRegistrations = event.registrations()
        )

        val actual = OverallPaxTimeResultsReportCreator(event.paxTimeParticipantResultMapper)
            .createFromRegistrationData(event.coreSeasonEvent.event.crispyFish!!, context)

        assertThat(actual).all {
            hasType(StandardResultsTypes.pax)
            participantResults().all {
                hasSize(6)
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Rebecca")
                        hasLastName("Jackson")
                        signage().isNotNull().all {
                            grouping().isSingular().hasAbbreviation("STR")
                            number().isEqualTo("8")
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
                            grouping().isSingular().hasAbbreviation("STR")
                            number().isEqualTo("23")
                        }
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
                        signage().isNotNull().all {
                            grouping().isPaired().all {
                                first().isNotNull().hasAbbreviation("NOV")
                                second().isNotNull().hasAbbreviation("BS")
                            }
                            number().isEqualTo("52")
                        }
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
                        signage().isNotNull().all {
                            grouping().isSingular().hasAbbreviation("HS")
                            number().isEqualTo("130")
                        }
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
                        signage().isNotNull().all {
                            grouping().isPaired().all {
                                first().isNotNull().hasAbbreviation("NOV")
                                second().isNotNull().hasAbbreviation("ES")
                            }
                            number().isEqualTo("18")
                        }
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
                        signage().isNotNull().all {
                            grouping().isPaired().all {
                                first().isNotNull().hasAbbreviation("NOV")
                                second().isNotNull().hasAbbreviation("CS")
                            }
                            number().isEqualTo("20")
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
    fun `It should create from registration data for LSCC 2019 event 3`() {
        val season = SeasonFixture.Lscc2019Simplified(fixtureRoot)
        val event = season.event3
        val context = CrispyFishEventMappingContext(
            allClassDefinitions = season.classDefinitions,
            allRegistrations = event.registrations()
        )

        val actual = OverallPaxTimeResultsReportCreator(event.paxTimeParticipantResultMapper)
            .createFromRegistrationData(event.coreSeasonEvent.event.crispyFish!!, context)

        assertThat(actual).all {
            hasType(StandardResultsTypes.pax)
            participantResults().all {
                hasSize(8)
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Rebecca")
                        hasLastName("Jackson")
                        signage().isNotNull().all {
                            grouping().isSingular().hasAbbreviation("STR")
                            number().isEqualTo("8")
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
                            grouping().isSingular().hasAbbreviation("STR")
                            number().isEqualTo("23")
                        }
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
                        signage().isNotNull().all {
                            grouping().isSingular().hasAbbreviation("STR")
                            number().isEqualTo("1")
                        }
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
                        signage().isNotNull().all {
                            grouping().isSingular().hasAbbreviation("HS")
                            number().isEqualTo("130")
                        }
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
                        signage().isNotNull().all {
                            grouping().isPaired().all {
                                first().isNotNull().hasAbbreviation("NOV")
                                second().isNotNull().hasAbbreviation("BS")
                            }
                            number().isEqualTo("52")
                        }
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
                        signage().isNotNull().all {
                            grouping().isPaired().all {
                                first().isNotNull().hasAbbreviation("NOV")
                                second().isNotNull().hasAbbreviation("GS")
                            }
                            number().isEqualTo("58")
                        }
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
                        signage().isNotNull().all {
                            grouping().isPaired().all {
                                first().isNotNull().hasAbbreviation("NOV")
                                second().isNotNull().hasAbbreviation("ES")
                            }
                            number().isEqualTo("18")
                        }
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
                        signage().isNotNull().all {
                            grouping().isPaired().all {
                                first().isNotNull().hasAbbreviation("NOV")
                                second().isNotNull().hasAbbreviation("CS")
                            }
                            number().isEqualTo("20")
                        }
                    }
                    score().hasValue("80.614")
                    diffFirst().isEqualTo("14.061")
                    diffPrevious().isEqualTo("1.545")
                }
            }
        }
    }
}