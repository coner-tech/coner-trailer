package org.coner.trailer.datasource.crispyfish.fixture

import org.coner.crispyfish.filetype.classdefinition.ClassDefinitionFile
import org.coner.trailer.*
import org.coner.trailer.datasource.crispyfish.GroupingMapper
import org.coner.trailer.seasonpoints.TestSeasonPointsCalculatorConfigurations
import java.io.File
import java.time.LocalDate

sealed class SeasonFixture(
        val path: String,
        val classDefinitionFixture: ClassDefinitionFixture
) {
    abstract val events: List<EventFixture>
    abstract val memberIdToPeople: Map<String, Person>
    abstract val season: Season
    object Lscc2019Simplified : SeasonFixture(
            path = "lscc-2019-simplified",
            classDefinitionFixture = ClassDefinitionFixture("lscc2019.def")
    ) {
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
        val event1 = EventFixture(
                groupingMapper = groupingMapper,
                memberIdToPeople = memberIdToPeople,
                coreSeasonEvent = SeasonEvent(
                        event = Event(date = LocalDate.parse("2019-01-01"), name = "Event 1"),
                        eventNumber = 1,
                        points = true
                ),
                ecfFileName = "2019-01-01 event 1.ecf"
        )
        val event2 = EventFixture(
                groupingMapper = groupingMapper,
                memberIdToPeople = memberIdToPeople,
                coreSeasonEvent = SeasonEvent(
                        event = Event(date = LocalDate.parse("2019-02-02"), name = "Event 2"),
                        eventNumber = 2,
                        points = true
                ),
                ecfFileName = "2019-02-02 event 2.ecf"
        )
        val event3 = EventFixture(
                groupingMapper = groupingMapper,
                memberIdToPeople = memberIdToPeople,
                coreSeasonEvent = SeasonEvent(
                        event = Event(date = LocalDate.parse("2019-03-03"), name = "Event 3"),
                        eventNumber = 3,
                        points = true
                ),
                ecfFileName = "2019-03-03 event 3.ecf"
        )
        override val events = listOf(event1, event2, event3)
        override val season = Season(
                name = "LSCC 2019 Simplified",
                events = events.map { it.coreSeasonEvent },
                seasonPointsCalculatorConfiguration = TestSeasonPointsCalculatorConfigurations.lscc2019,
                takeScoreCountForPoints = 2
        )
    }

    val classDefinitionFile = ClassDefinitionFile(
            file = File(javaClass.getResource("/seasons/$path/${classDefinitionFixture.fileName}").toURI())
    )

    val classDefinitions = classDefinitionFile.mapper().all()
    val categories = classDefinitions.filter { it.paxed }
    val handicaps = classDefinitions.filter { !it.paxed }
    val groupingMapper = GroupingMapper(
            classDefinitions = classDefinitions
    )
}

private fun personFactory(person: Person, withMemberId: String): Person {
    return person.copy(memberId = withMemberId)
}
