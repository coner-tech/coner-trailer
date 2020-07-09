package org.coner.trailer.datasource.crispyfish.eventsresults

import assertk.all
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.index
import assertk.assertions.isNotNull
import assertk.assertions.key
import org.coner.crispyfish.filetype.classdefinition.ClassDefinitionFile
import org.coner.crispyfish.filetype.ecf.EventControlFile
import org.coner.crispyfish.filetype.ecf.EventControlFileAssistant
import org.coner.crispyfish.filetype.staging.StagingFileAssistant
import org.coner.crispyfish.model.ClassDefinition
import org.coner.crispyfish.query.CategoriesQuery
import org.coner.crispyfish.query.HandicapsQuery
import org.coner.crispyfish.query.RegistrationsQuery
import org.coner.trailer.*
import org.coner.trailer.eventresults.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class CompetitionGroupedResultsReportCreatorTest {

    lateinit var peopleByMemberId: Map<String, Person>
    lateinit var classDefinitionFile: ClassDefinitionFile
    lateinit var lscc2019Categories: List<ClassDefinition>
    lateinit var lscc2019Handicaps: List<ClassDefinition>

    @BeforeEach
    fun before() {
        peopleByMemberId = buildPeopleByMemberId()
        classDefinitionFile = ClassDefinitionFile(getResourceFile("lscc2019.def"))
        lscc2019Categories = CategoriesQuery(classDefinitionFile).query()
        lscc2019Handicaps = HandicapsQuery(classDefinitionFile).query()
    }

    @Test
    fun `It should create from registration data for LSCC 2019 event 1`() {
        val ecf = ecfFactory("2019-01-01 event 1.ecf")
        val registrations = RegistrationsQuery(
                eventControlFile = ecf,
                categories = lscc2019Categories,
                handicaps = lscc2019Handicaps
        ).query()
        check(registrations.isNotEmpty()) { "Sanity check failed: registrations empty" }

        val actual = CompetitionGroupedResultsReportCreator().createFromRegistrationData(
                crispyFishRegistrations = registrations,
                peopleByMemberId = peopleByMemberId
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
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Bryant")
                        hasLastName("Moran")
                        grouping().isPaired().all {
                            first().hasAbbreviation("NOV")
                            second().hasAbbreviation("ES")
                        }
                        hasNumber("58")
                    }
                }
                index(2).all {
                    hasPosition(3)
                    participant().all {
                        hasFirstName("Dominic")
                        hasLastName("Rogers")
                        grouping().isPaired().all {
                            first().hasAbbreviation("NOV")
                            second().hasAbbreviation("ES")
                        }
                        hasNumber("18")
                    }
                }
            }
        }
    }

    @Test
    fun `It should create from registration data for LSCC 2019 event 2`() {
        val ecf = ecfFactory("2019-02-02 event 2.ecf")
        val registrations = RegistrationsQuery(
                eventControlFile = ecf,
                categories = lscc2019Categories,
                handicaps = lscc2019Handicaps
        ).query()
        check(registrations.isNotEmpty()) { "Sanity check failed: registrations empty" }

        val actual = CompetitionGroupedResultsReportCreator().createFromRegistrationData(
                crispyFishRegistrations = registrations,
                peopleByMemberId = peopleByMemberId
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
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Rebecca")
                        hasLastName("Jackson")
                        grouping().isSingular().hasAbbreviation("STR")
                        hasNumber("8")
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
                index(0).all {
                    hasPosition(1)
                    participant().all {
                        hasFirstName("Brandy")
                        hasLastName("Huff")
                        grouping().isPaired().all {
                            first().hasAbbreviation("NOV")
                            second().hasAbbreviation("BS")
                        }
                        hasNumber("52")
                    }
                }
                index(1).all {
                    hasPosition(2)
                    participant().all {
                        hasFirstName("Dominic")
                        hasLastName("Rogers")
                        grouping().isPaired().all {
                            first().hasAbbreviation("NOV")
                            second().hasAbbreviation("ES")
                        }
                        hasNumber("18")
                    }
                }
                index(2).all {
                    hasPosition(3)
                    participant().all {
                        hasFirstName("Bennett")
                        hasLastName("Pantone")
                        grouping().isPaired().all {
                            first().hasAbbreviation("NOV")
                            second().hasAbbreviation("CS")
                        }
                        hasNumber("20")
                    }
                }
            }
        }
    }

    @Test
    fun `It should create from registration data for LSCC 2019 event 3`() {
        val ecf = ecfFactory("2019-03-03 event 3.ecf")
        val registrations = RegistrationsQuery(
                eventControlFile = ecf,
                categories = lscc2019Categories,
                handicaps = lscc2019Handicaps
        ).query()
        check(registrations.isNotEmpty()) { "Sanity check failed: registrations empty" }

        val actual = CompetitionGroupedResultsReportCreator().createFromRegistrationData(
                crispyFishRegistrations = registrations,
                peopleByMemberId = peopleByMemberId
        )

        TODO()
    }

}

private fun personFactory(person: Person, withMemberId: String): Person {
    return person.copy(memberId = withMemberId)
}

private fun CompetitionGroupedResultsReportCreatorTest.ecfFactory(fileName: String): EventControlFile {
    return EventControlFile(
            file = getResourceFile(fileName),
            classDefinitionFile = classDefinitionFile,
            conePenalty = 2,
            ecfAssistant = EventControlFileAssistant(),
            stagingFileAssistant = StagingFileAssistant(),
            isTwoDayEvent = false
    )
}

private fun buildPeopleByMemberId() = listOf(
        personFactory(TestPeople.DOMINIC_ROGERS, "2019-00061"),
        personFactory(TestPeople.BRANDY_HUFF, "2019-00080"),
        personFactory(TestPeople.BRYANT_MORAN, "2019-00125"),
        personFactory(TestPeople.REBECCA_JACKSON, "1807"),
        personFactory(TestPeople.ANASTASIA_RIGLER, "2019-00094"),
        personFactory(TestPeople.JIMMY_MCKENZIE, "2476"),
        personFactory(TestPeople.EUGENE_DRAKE, "2019-00057"),
        personFactory(TestPeople.BENNETT_PANTONE, "2019-00295")
).map { it.memberId to it }.toMap()

private fun CompetitionGroupedResultsReportCreatorTest.getResourceFile(name: String): File {
    return File(javaClass.getResource("/crispy-fish-it/$name").toURI())
}
