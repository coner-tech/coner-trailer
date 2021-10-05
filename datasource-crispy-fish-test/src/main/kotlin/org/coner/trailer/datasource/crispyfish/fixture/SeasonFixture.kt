package org.coner.trailer.datasource.crispyfish.fixture

import tech.coner.crispyfish.filetype.classdefinition.ClassDefinitionFile
import org.coner.trailer.*
import org.coner.trailer.datasource.crispyfish.CrispyFishClassingMapper
import org.coner.trailer.seasonpoints.TestSeasonPointsCalculatorConfigurations
import java.nio.file.Path
import java.time.LocalDate
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile
import kotlin.io.path.writeText

sealed class SeasonFixture(
    val temp: Path,
    val path: String,
    val classDefinitionFixture: ClassDefinitionFixture
) {
    abstract val events: List<EventFixture>
    abstract val season: Season

    class Lscc2019Simplified(temp: Path) : SeasonFixture(
        temp = temp,
        path = "lscc-2019-simplified",
        classDefinitionFixture = ClassDefinitionFixture("lscc2019.def")
    ) {
        val event1 = EventFixture(
            seasonFixture = this,
            temp = temp,
            crispyFishClassingMapper = groupingMapper,
            runCount = 5,
            coreSeasonEvent = SeasonEvent(
                event = Event(
                    name = "Event 1",
                    date = LocalDate.parse("2019-01-01"),
                    lifecycle = Event.Lifecycle.FINAL,
                    crispyFish = Event.CrispyFishMetadata(
                        eventControlFile = "2019-01-01 event 1.ecf",
                        classDefinitionFile = "lscc2019.def",
                        peopleMap = mapOf(
                            peopleMapping(TestParticipants.Lscc2019Points1Simplified.ANASTASIA_RIGLER, TestPeople.ANASTASIA_RIGLER),
                            peopleMapping(TestParticipants.Lscc2019Points1Simplified.REBECCA_JACKSON, TestPeople.REBECCA_JACKSON),
                            peopleMapping(TestParticipants.Lscc2019Points1Simplified.EUGENE_DRAKE, TestPeople.EUGENE_DRAKE),
                            peopleMapping(TestParticipants.Lscc2019Points1Simplified.JIMMY_MCKENZIE, TestPeople.JIMMY_MCKENZIE),
                            peopleMapping(TestParticipants.Lscc2019Points1Simplified.BRANDY_HUFF, TestPeople.BRANDY_HUFF),
                            peopleMapping(TestParticipants.Lscc2019Points1Simplified.BRYANT_MORAN, TestPeople.BRYANT_MORAN),
                            peopleMapping(TestParticipants.Lscc2019Points1Simplified.DOMINIC_ROGERS, TestPeople.DOMINIC_ROGERS)
                        )
                    ),
                    motorsportReg = null,
                    policy = TestPolicies.lsccV1,
                ),
                eventNumber = 1,
                points = true

            )
        )
        val event2 = EventFixture(
            seasonFixture = this,
            temp = temp,
            crispyFishClassingMapper = groupingMapper,
            runCount = 4,
            coreSeasonEvent = SeasonEvent(
                event = Event(
                    name = "Event 2",
                    date = LocalDate.parse("2019-02-02"),
                    lifecycle = Event.Lifecycle.FINAL,
                    crispyFish = Event.CrispyFishMetadata(
                        eventControlFile = "2019-02-02 event 2.ecf",
                        classDefinitionFile = "lscc2019.def",
                        peopleMap = mapOf(
                            peopleMapping(TestParticipants.Lscc2019Points2Simplified.ANASTASIA_RIGLER, TestPeople.ANASTASIA_RIGLER),
                            peopleMapping(TestParticipants.Lscc2019Points2Simplified.REBECCA_JACKSON, TestPeople.REBECCA_JACKSON),
                            peopleMapping(TestParticipants.Lscc2019Points2Simplified.JIMMY_MCKENZIE, TestPeople.JIMMY_MCKENZIE),
                            peopleMapping(TestParticipants.Lscc2019Points2Simplified.BRANDY_HUFF, TestPeople.BRANDY_HUFF),
                            peopleMapping(TestParticipants.Lscc2019Points2Simplified.DOMINIC_ROGERS, TestPeople.DOMINIC_ROGERS),
                            peopleMapping(TestParticipants.Lscc2019Points2Simplified.BENNETT_PANTONE, TestPeople.BENNETT_PANTONE)
                        )
                    ),
                    motorsportReg = null,
                    policy = TestPolicies.lsccV1,
                ),
                eventNumber = 2,
                points = true
            )
        )
        val event3 = EventFixture(
            seasonFixture = this,
            temp = temp,
            crispyFishClassingMapper = groupingMapper,
            runCount = 4,
            coreSeasonEvent = SeasonEvent(
                event = Event(
                    name = "Event 3",
                    date = LocalDate.parse("2019-03-03"),
                    lifecycle = Event.Lifecycle.FINAL,
                    crispyFish = Event.CrispyFishMetadata(
                        eventControlFile = "2019-03-03 event 3.ecf",
                        classDefinitionFile = "lscc2019.def",
                        peopleMap = mapOf(
                            peopleMapping(TestParticipants.Lscc2019Points3Simplified.ANASTASIA_RIGLER, TestPeople.ANASTASIA_RIGLER),
                            peopleMapping(TestParticipants.Lscc2019Points3Simplified.REBECCA_JACKSON, TestPeople.REBECCA_JACKSON),
                            peopleMapping(TestParticipants.Lscc2019Points3Simplified.JIMMY_MCKENZIE, TestPeople.JIMMY_MCKENZIE),
                            peopleMapping(TestParticipants.Lscc2019Points3Simplified.EUGENE_DRAKE, TestPeople.EUGENE_DRAKE),
                            peopleMapping(TestParticipants.Lscc2019Points3Simplified.BRANDY_HUFF, TestPeople.BRANDY_HUFF),
                            peopleMapping(TestParticipants.Lscc2019Points3Simplified.BRYANT_MORAN, TestPeople.BRYANT_MORAN),
                            peopleMapping(TestParticipants.Lscc2019Points3Simplified.DOMINIC_ROGERS, TestPeople.DOMINIC_ROGERS),
                            peopleMapping(TestParticipants.Lscc2019Points3Simplified.BENNETT_PANTONE, TestPeople.BENNETT_PANTONE)
                        )
                    ),
                    motorsportReg = null,
                    policy = TestPolicies.lsccV1,
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
    val groupingMapper = CrispyFishClassingMapper()
}

private fun peopleMapping(testParticipant: Participant, person: Person): Pair<Event.CrispyFishMetadata.PeopleMapKey, Person> {
    val key = Event.CrispyFishMetadata.PeopleMapKey(
        classing = requireNotNull(testParticipant.classing),
        number = requireNotNull(testParticipant.number),
        firstName = requireNotNull(testParticipant.firstName),
        lastName = requireNotNull(testParticipant.lastName)
    )
    return key to person
}
