package org.coner.trailer.datasource.crispyfish.eventsresults

import assertk.all
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.index
import org.coner.trailer.*
import org.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import org.coner.trailer.eventresults.*
import org.junit.jupiter.api.Test

class OverallRawTimeResultsReportCreatorTest {

    @Test
    fun `It should create from registration data for LSCC 2019 event 1`() {
        val season = SeasonFixture.Lscc2019Simplified
        val event = season.event1

        val actual = OverallRawTimeResultsReportCreator(event.participantResultMapper).createFromRegistrationData(
                crispyFishRegistrations = event.registrations(season)
        )

        assertThat(actual).all {
            hasType(StandardResultsTypes.overallRawTime)
            participantResults().all {
                hasSize(7)
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Eugene")
                        hasLastName("Drake")
                        signage().all {
                            grouping().isSingular().hasAbbreviation("STR")
                            hasNumber("1")
                        }
                    }
                    score().hasValue("47.544")
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Brandy")
                        hasLastName("Huff")
                        signage().all {
                            grouping().isPaired().all {
                                first().hasAbbreviation("NOV")
                                second().hasAbbreviation("BS")
                            }
                            hasNumber("177")
                        }
                    }
                    score().hasValue("48.515")
                }
                index(2).all {
                    hasPosition(3)
                    participant().all {
                        hasFirstName("Jimmy")
                        hasLastName("Mckenzie")
                        signage().all {
                            grouping().isSingular().hasAbbreviation("STR")
                            hasNumber("23")
                        }
                    }
                    score().hasValue("48.723")
                }
                index(3).all {
                    hasPosition(4)
                    participant().all {
                        hasFirstName("Anastasia")
                        hasLastName("Rigler")
                        signage().all {
                            grouping().isSingular().hasAbbreviation("HS")
                            hasNumber("130")
                        }
                    }
                    score().hasValue("51.323")
                }
                index(4).all {
                    hasPosition(5)
                    participant().all {
                        hasFirstName("Rebecca")
                        hasLastName("Jackson")
                        signage().all {
                            grouping().isSingular().hasAbbreviation("HS")
                            hasNumber("1")
                        }
                    }
                    score().hasValue("51.408")
                }
                index(5).all {
                    hasPosition(6)
                    participant().all {
                        hasFirstName("Bryant")
                        hasLastName("Moran")
                        signage().all {
                            grouping().isPaired().all {
                                first().hasAbbreviation("NOV")
                                second().hasAbbreviation("ES")
                            }
                            hasNumber("58")
                        }
                    }
                    score().hasValue("52.201")
                }
                index(6).all {
                    hasPosition(7)
                    participant().all {
                        hasFirstName("Dominic")
                        hasLastName("Rogers")
                        signage().all {
                            grouping().isPaired().all {
                                first().hasAbbreviation("NOV")
                                second().hasAbbreviation("ES")
                            }
                            hasNumber("18")
                        }
                    }
                    score().hasValue("52.447")
                }
            }
        }
    }

    @Test
    fun `It should create from registration data for LSCC 2019 event 2`() {
        val season = SeasonFixture.Lscc2019Simplified
        val event = season.event2

        val actual = OverallRawTimeResultsReportCreator(event.participantResultMapper).createFromRegistrationData(
                crispyFishRegistrations = event.registrations(season)
        )

        assertThat(actual).all {
            hasType(StandardResultsTypes.overallRawTime)
            participantResults().all {
                hasSize(6)
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Rebecca")
                        hasLastName("Jackson")
                        signage().all {
                            grouping().isSingular().hasAbbreviation("STR")
                            hasNumber("8")
                        }
                    }
                    score().hasValue("34.762")
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Jimmy")
                        hasLastName("Mckenzie")
                        signage().all {
                            grouping().isSingular().hasAbbreviation("STR")
                            hasNumber("23")
                        }
                    }
                    score().hasValue("36.185")
                }
                index(2).all {
                    hasPosition(3)
                    participant().all {
                        hasFirstName("Brandy")
                        hasLastName("Huff")
                        signage().all {
                            grouping().isPaired().all {
                                first().hasAbbreviation("NOV")
                                second().hasAbbreviation("BS")
                            }
                            hasNumber("52")
                        }
                    }
                    score().hasValue("37.058")
                }
                index(3).all {
                    hasPosition(4)
                    participant().all {
                        hasFirstName("Dominic")
                        hasLastName("Rogers")
                        signage().all {
                            grouping().isPaired().all {
                                first().hasAbbreviation("NOV")
                                second().hasAbbreviation("ES")
                            }
                            hasNumber("18")
                        }
                    }
                    score().hasValue("38.698")
                }
                index(4).all {
                    hasPosition(5)
                    participant().all {
                        hasFirstName("Anastasia")
                        hasLastName("Rigler")
                        signage().all {
                            grouping().isSingular().hasAbbreviation("HS")
                            hasNumber("130")
                        }
                    }
                    score().hasValue("38.986")
                }
                index(5).all {
                    hasPosition(6)
                    participant().all {
                        hasFirstName("Bennett")
                        hasLastName("Pantone")
                        signage().all {
                            grouping().isPaired().all {
                                first().hasAbbreviation("NOV")
                                second().hasAbbreviation("CS")

                            }
                            hasNumber("20")
                        }
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

        val actual = OverallRawTimeResultsReportCreator(event.participantResultMapper).createFromRegistrationData(
                crispyFishRegistrations = event.registrations(season)
        )

        assertThat(actual).all {
            hasType(StandardResultsTypes.overallRawTime)
            participantResults().all {
                hasSize(8)
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Rebecca")
                        hasLastName("Jackson")
                        signage().all {
                            grouping().isSingular().hasAbbreviation("STR")
                            hasNumber("8")
                        }
                    }
                    score().hasValue("80.476")
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Jimmy")
                        hasLastName("Mckenzie")
                        signage().all {
                            grouping().isSingular().hasAbbreviation("STR")
                            hasNumber("23")
                        }
                    }
                    score().hasValue("83.740")
                }
                index(2).all {
                    hasPosition(3)
                    participant().all {
                        hasFirstName("Eugene")
                        hasLastName("Drake")
                        signage().all {
                            grouping().isSingular().hasAbbreviation("STR")
                            hasNumber("1")
                        }
                    }
                    score().hasValue("87.036")
                }
                index(3).all {
                    hasPosition(4)
                    participant().all {
                        hasFirstName("Brandy")
                        hasLastName("Huff")
                        signage().all {
                            grouping().isPaired().all {
                                first().hasAbbreviation("NOV")
                                second().hasAbbreviation("BS")
                            }
                            hasNumber("52")
                        }
                    }
                    score().hasValue("90.079")
                }
                index(4).all {
                    hasPosition(5)
                    participant().all {
                        hasFirstName("Anastasia")
                        hasLastName("Rigler")
                        signage().all {
                            grouping().isSingular().hasAbbreviation("HS")
                            hasNumber("130")
                        }
                    }
                    score().hasValue("92.462")
                }
                index(5).all {
                    hasPosition(6)
                    participant().all {
                        hasFirstName("Bennett")
                        hasLastName("Pantone")
                        signage().all {
                            grouping().isPaired().all {
                                first().hasAbbreviation("NOV")
                                second().hasAbbreviation("CS")
                            }
                            hasNumber("20")
                        }
                    }
                    score().hasValue("99.647")
                }
                index(6).all {
                    hasPosition(7)
                    participant().all {
                        hasFirstName("Bryant")
                        hasLastName("Moran")
                        signage().all {
                            grouping().isPaired().all {
                                first().hasAbbreviation("NOV")
                                second().hasAbbreviation("GS")
                            }
                            hasNumber("58")
                        }
                    }
                    score().hasValue("100.059")
                }
                index(7).all {
                    hasPosition(8)
                    participant().all {
                        hasFirstName("Dominic")
                        hasLastName("Rogers")
                        signage().all {
                            grouping().isPaired().all {
                                first().hasAbbreviation("NOV")
                                second().hasAbbreviation("ES")
                            }
                            hasNumber("18")
                        }
                    }
                    score().hasValue("100.215")
                }
            }
        }
    }
}