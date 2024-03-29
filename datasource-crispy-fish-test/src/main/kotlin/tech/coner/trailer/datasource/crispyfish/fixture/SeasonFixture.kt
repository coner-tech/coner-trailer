package tech.coner.trailer.datasource.crispyfish.fixture

import tech.coner.crispyfish.CrispyFish
import tech.coner.trailer.*
import tech.coner.trailer.datasource.crispyfish.CrispyFishClassingMapper
import tech.coner.trailer.seasonpoints.TestSeasonPointsCalculatorConfigurations
import java.nio.file.Path
import java.nio.file.Paths
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
            coreSeasonEvent = SeasonEvent(
                event = Event(
                    name = "Event 1",
                    date = LocalDate.parse("2019-01-01"),
                    lifecycle = Event.Lifecycle.FINAL,
                    crispyFish = Event.CrispyFishMetadata(
                        eventControlFile = Paths.get("seasons", path, "2019-01-01 event 1.ecf"),
                        classDefinitionFile = Paths.get("seasons", path, "lscc2019.def"),
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
            coreSeasonEvent = SeasonEvent(
                event = Event(
                    name = "Event 2",
                    date = LocalDate.parse("2019-02-02"),
                    lifecycle = Event.Lifecycle.FINAL,
                    crispyFish = Event.CrispyFishMetadata(
                        eventControlFile = Paths.get("seasons", path, "2019-02-02 event 2.ecf"),
                        classDefinitionFile = Paths.get("seasons", path, "lscc2019.def"),
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
            coreSeasonEvent = SeasonEvent(
                event = Event(
                    name = "Event 3",
                    date = LocalDate.parse("2019-03-03"),
                    lifecycle = Event.Lifecycle.FINAL,
                    crispyFish = Event.CrispyFishMetadata(
                        eventControlFile = Paths.get("seasons", path, "2019-03-03 event 3.ecf"),
                        classDefinitionFile = Paths.get("seasons", path, "lscc2019.def"),
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

    class Issue64CrispyFishStagingLinesInvalidSignage(temp: Path) : SeasonFixture(
        temp = temp,
        path = "64-crispy-fish-staging-lines-invalid-signage",
        classDefinitionFixture = ClassDefinitionFixture("class2022_lscc.def")
    ) {
        val event = EventFixture(
            seasonFixture = this,
            temp = temp,
            coreSeasonEvent = SeasonEvent(
                event = Event(
                    name = "64-crispy-fish-staging-lines-invalid-signage",
                    date = LocalDate.parse("2022-04-08"),
                    lifecycle = Event.Lifecycle.FINAL,
                    crispyFish = Event.CrispyFishMetadata(
                        eventControlFile = Paths.get("seasons", path, "64-crispy-fish-staging-lines-invalid-signage.ecf"),
                        classDefinitionFile = Paths.get("seasons", path, "class2022_lscc.def"),
                        peopleMap = emptyMap() // intentionally empty to exercise person nullability
                    ),
                    motorsportReg = null,
                    policy = TestPolicies.lsccV1,
                ),
                eventNumber = null,
                points = false
            )
        )

        override val events = listOf(event)
        override val season = Season(
            name = "Issue 64 Season",
            seasonEvents = events.map { it.coreSeasonEvent },
            seasonPointsCalculatorConfiguration = TestSeasonPointsCalculatorConfigurations.lscc2019,
            takeScoreCountForPoints = null
        )
    }

    val classDefinitionPath: Path

    init {
        javaClass.getResourceAsStream("/seasons/$path/${classDefinitionFixture.fileName}").use {
            val text = it.bufferedReader().readText()
            val path = temp.resolve("seasons/$path/${classDefinitionFixture.fileName}")
            path.parent.createDirectories()
            path.createFile()
            path.writeText(text)
            classDefinitionPath = path
        }
    }

    val classDefinitions = CrispyFish.classDefinitions(classDefinitionPath).queryAllClassDefinitions()
    val categories = classDefinitions.categories
    val handicaps = classDefinitions.handicaps
    val groupingMapper = CrispyFishClassingMapper()
}

private fun peopleMapping(testParticipant: Participant, person: Person): Pair<Event.CrispyFishMetadata.PeopleMapKey, Person> {
    val key = Event.CrispyFishMetadata.PeopleMapKey(
        classing = requireNotNull(testParticipant.signage?.classing),
        number = requireNotNull(testParticipant.signage?.number),
        firstName = requireNotNull(testParticipant.firstName),
        lastName = requireNotNull(testParticipant.lastName)
    )
    return key to person
}
