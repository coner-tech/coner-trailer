package org.coner.trailer.datasource.crispyfish.fixture

import org.coner.crispyfish.filetype.classdefinition.ClassDefinitionFile
import org.coner.crispyfish.query.CategoriesQuery
import org.coner.crispyfish.query.HandicapsQuery
import org.coner.trailer.*
import org.coner.trailer.eventresults.StandardResultsTypes
import org.coner.trailer.seasonpoints.CalculatorConfigurationModel
import java.io.File
import java.time.LocalDate

sealed class SeasonFixture {
    abstract val path: String
    abstract val events: List<EventFixture>
    abstract val classDefinitionFixture: ClassDefinitionFixture
    abstract val memberIdToPeople: Map<String, Person>
    abstract val season: Season
    object Lscc2019Simplified : SeasonFixture() {
        val event1 = EventFixture(
                coreSeasonEvent = SeasonEvent(
                        event = Event(date = LocalDate.parse("2019-01-01"), name = "Event 1"),
                        eventNumber = 1,
                        points = true
                ),
                ecfFileName = "2019-01-01 event 1.ecf"
        )
        val event2 = EventFixture(
                coreSeasonEvent = SeasonEvent(
                        event = Event(date = LocalDate.parse("2019-02-02"), name = "Event 2"),
                        eventNumber = 2,
                        points = true
                ),
                ecfFileName = "2019-02-02 event 2.ecf"
        )
        val event3 = EventFixture(
                coreSeasonEvent = SeasonEvent(
                        event = Event(date = LocalDate.parse("2019-03-03"), name = "Event 3"),
                        eventNumber = 3,
                        points = true
                ),
                ecfFileName = "2019-03-03 event 3.ecf"
        )
        override val path = "lscc-2019-simplified"
        override val classDefinitionFixture = ClassDefinitionFixture("lscc2019.def")
        override val events = listOf(event1, event2, event3)
        override val memberIdToPeople = listOf(
                personFactory(TestPeople.DOMINIC_ROGERS, "2019-00061"),
                personFactory(TestPeople.BRANDY_HUFF, "2019-00080"),
                personFactory(TestPeople.BRYANT_MORAN, "2019-00125"),
                personFactory(TestPeople.REBECCA_JACKSON, "1807"),
                personFactory(TestPeople.ANASTASIA_RIGLER, "2019-00094"),
                personFactory(TestPeople.JIMMY_MCKENZIE, "2476"),
                personFactory(TestPeople.EUGENE_DRAKE, "2019-00057"),
                personFactory(TestPeople.BENNETT_PANTONE, "2019-00295")
        ).map { it.memberId to it }.toMap()
        override val season = Season(
                name = "LSCC 2019 Simplified",
                events = events.map { it.coreSeasonEvent },
                seasonPointsCalculatorConfigurationModel = CalculatorConfigurationModel(mapOf(
                        StandardResultsTypes.competitionGrouped to TestParticipantResultPointsCalculators.lsccGroupingCalculator,
                        StandardResultsTypes.overallRawTime to TestParticipantResultPointsCalculators.lsccOverallCalculator,
                        StandardResultsTypes.overallHandicapTime to TestParticipantResultPointsCalculators.lsccOverallCalculator
                ))
        )
    }

    fun classDefinitionFile() = ClassDefinitionFile(
            file = File(javaClass.getResource("/seasons/$path/${classDefinitionFixture.fileName}").toURI())
    )

    fun categories() = CategoriesQuery(classDefinitionFile()).query()

    fun handicaps() = HandicapsQuery(classDefinitionFile()).query()
}

private fun personFactory(person: Person, withMemberId: String): Person {
    return person.copy(memberId = withMemberId)
}
