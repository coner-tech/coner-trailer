package org.coner.trailer.datasource.crispyfish.eventsresults

import assertk.all
import assertk.assertThat
import org.coner.crispyfish.filetype.classdefinition.ClassDefinitionFile
import org.coner.crispyfish.filetype.ecf.EventControlFile
import org.coner.crispyfish.filetype.ecf.EventControlFileAssistant
import org.coner.crispyfish.filetype.staging.StagingFileAssistant
import org.coner.crispyfish.model.ClassDefinition
import org.coner.crispyfish.query.CategoriesQuery
import org.coner.crispyfish.query.HandicapsQuery
import org.coner.crispyfish.query.RegistrationsQuery
import org.coner.trailer.Person
import org.coner.trailer.TestPeople
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

        TODO()
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

        TODO()
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
