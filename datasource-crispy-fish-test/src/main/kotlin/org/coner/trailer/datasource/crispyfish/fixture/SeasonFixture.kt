package org.coner.trailer.datasource.crispyfish.fixture

import org.coner.crispyfish.filetype.classdefinition.ClassDefinitionFile
import org.coner.trailer.*
import org.coner.trailer.datasource.crispyfish.CrispyFishGroupingMapper
import org.coner.trailer.seasonpoints.TestSeasonPointsCalculatorConfigurations
import java.nio.file.Path
import java.time.LocalDate
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile
import kotlin.io.path.writeText

@OptIn(ExperimentalPathApi::class)
sealed class SeasonFixture(
    val temp: Path,
    val path: String,
    val classDefinitionFixture: ClassDefinitionFixture
) {
    abstract val events: List<EventFixture>
    abstract val memberIdToPeople: Map<String, Person>
    abstract val season: Season
    class Lscc2019Simplified(temp: Path) : SeasonFixture(
        temp = temp,
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
        ).map { requireNotNull(it.clubMemberId) to it }.toMap()
        val event1 = EventFixture(
            seasonFixture = this,
            temp = temp,
            crispyFishGroupingMapper = groupingMapper,
            memberIdToPeople = memberIdToPeople,
            coreSeasonEvent = SeasonEvent(
                event = Event(
                    name = "Event 1",
                    date = LocalDate.parse("2019-01-01"),
                    lifecycle = Event.Lifecycle.FINAL,
                    crispyFish = Event.CrispyFishMetadata(
                        eventControlFile = "2019-01-01 event 1.ecf",
                        classDefinitionFile = "lscc2019.def",
                        peopleMap = emptyMap()
                    )
                ),
                eventNumber = 1,
                points = true
            )
        )
        val event2 = EventFixture(
            seasonFixture = this,
            temp = temp,
            crispyFishGroupingMapper = groupingMapper,
            memberIdToPeople = memberIdToPeople,
            coreSeasonEvent = SeasonEvent(
                event = Event(
                    name = "Event 2",
                    date = LocalDate.parse("2019-02-02"),
                    lifecycle = Event.Lifecycle.FINAL,
                    crispyFish = Event.CrispyFishMetadata(
                        eventControlFile = "2019-02-02 event 2.ecf",
                        classDefinitionFile = "lscc2019.def",
                        peopleMap = emptyMap()
                    )
                ),
                eventNumber = 2,
                points = true
            )
        )
        val event3 = EventFixture(
            seasonFixture = this,
            temp = temp,
            crispyFishGroupingMapper = groupingMapper,
            memberIdToPeople = memberIdToPeople,
            coreSeasonEvent = SeasonEvent(
                event = Event(
                    name = "Event 3",
                    date = LocalDate.parse("2019-03-03"),
                    lifecycle = Event.Lifecycle.FINAL,
                    crispyFish = Event.CrispyFishMetadata(
                        eventControlFile = "2019-03-03 event 3.ecf",
                        classDefinitionFile = "lscc2019.def",
                        peopleMap = emptyMap()
                    )
                ),
                eventNumber = 3,
                points = true
            )
        )
        override val events = listOf(event1, event2, event3)
        override val season = Season(
            name = "LSCC 2019 Simplified",
            seasonEvents = events.map { it.coreSeasonEvent },
            seasonPointsCalculatorConfiguration = TestSeasonPointsCalculatorConfigurations.lscc2019,
            takeScoreCountForPoints = 2
        )
    }

    val classDefinitionPath: Path
    val classDefinitionFile: ClassDefinitionFile

    init {
        javaClass.getResourceAsStream("/seasons/$path/${classDefinitionFixture.fileName}").use {
            val text = it.bufferedReader().readText()
            val path = temp.resolve("seasons/$path/${classDefinitionFixture.fileName}")
            path.parent.createDirectories()
            path.createFile()
            path.writeText(text)
            classDefinitionPath = path
            classDefinitionFile = ClassDefinitionFile(
                file = path.toFile()
            )
        }
    }

    val classDefinitions = classDefinitionFile.mapper().all()
    val categories = classDefinitions.filter { it.paxed }
    val handicaps = classDefinitions.filter { !it.paxed }
    val groupingMapper = CrispyFishGroupingMapper()
}

private fun personFactory(person: Person, withMemberId: String): Person {
    return person.copy(clubMemberId = withMemberId)
}

