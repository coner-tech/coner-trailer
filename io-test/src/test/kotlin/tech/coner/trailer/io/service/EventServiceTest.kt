package tech.coner.trailer.io.service

import assertk.all
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import assertk.assertions.isSameAs
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import tech.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import tech.coner.trailer.datasource.snoozle.EventResource
import tech.coner.trailer.datasource.snoozle.entity.EventEntity
import tech.coner.trailer.io.DatabaseConfiguration
import tech.coner.trailer.io.TestDatabaseConfigurations
import tech.coner.trailer.io.constraint.EventDeleteConstraints
import tech.coner.trailer.io.constraint.EventPersistConstraints
import tech.coner.trailer.io.mapper.EventMapper
import tech.coner.trailer.io.verifier.EventCrispyFishPersonMapVerifier
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import tech.coner.trailer.*
import tech.coner.trailer.io.verifier.RunWithInvalidSignageVerifier
import java.nio.file.Path
import java.util.stream.Stream

@ExtendWith(MockKExtension::class)
class EventServiceTest {

    lateinit var service: EventService

    @TempDir
    lateinit var root: Path
    lateinit var dbConfig: DatabaseConfiguration

    @MockK
    lateinit var resource: EventResource

    @MockK
    lateinit var mapper: EventMapper

    @MockK
    lateinit var persistConstraints: EventPersistConstraints

    @MockK
    lateinit var deleteConstraints: EventDeleteConstraints

    @MockK
    lateinit var eventCrispyFishPersonMapVerifier: EventCrispyFishPersonMapVerifier

    @MockK
    lateinit var runWithInvalidSignageVerifier: RunWithInvalidSignageVerifier

    @MockK
    lateinit var crispyFishEventMappingContextService: CrispyFishEventMappingContextService

    @MockK
    lateinit var runService: RunService

    @MockK
    lateinit var participantService: ParticipantService

    @BeforeEach
    fun before() {
        dbConfig = TestDatabaseConfigurations(root).foo
        service = EventService(
            dbConfig = dbConfig,
            resource = resource,
            mapper = mapper,
            persistConstraints = persistConstraints,
            deleteConstraints = deleteConstraints,
            eventCrispyFishPersonMapVerifier = eventCrispyFishPersonMapVerifier,
            runWithInvalidSignageVerifier = runWithInvalidSignageVerifier,
            crispyFishEventMappingContextService = crispyFishEventMappingContextService,
            runService = runService,
            participantService = participantService,
        )
    }

    @Test
    fun `It should create event`() {
        val create = TestEvents.Lscc2019.points1
            .copy(
                lifecycle = Event.Lifecycle.CREATE
            )
        justRun { persistConstraints.assess(create) }
        val snoozleEvent: EventEntity = mockk()
        every { mapper.toSnoozle(create) } returns snoozleEvent
        justRun { resource.create(snoozleEvent) }

        service.create(
            id = create.id,
            name = create.name,
            date = create.date,
            crispyFishEventControlFile = create.requireCrispyFish().eventControlFile,
            crispyFishClassDefinitionFile = create.requireCrispyFish().classDefinitionFile,
            motorsportRegEventId = create.motorsportReg?.id,
            policy = create.policy
        )

        verifySequence {
            mapper.toSnoozle(create)
            resource.create(snoozleEvent)
        }
    }

    @Test
    fun `It should find event by id`() {
        val event = TestEvents.Lscc2019.points1
        val slot = slot<EventEntity.Key>()
        val snoozleEvent: EventEntity = mockk()
        every { resource.read(capture(slot)) } returns snoozleEvent
        every { mapper.toCore(snoozleEvent) } returns event

        val actual = service.findById(event.id)

        assertThat(actual).isSameAs(event)
        assertThat(slot.captured.id).isSameAs(event.id)
        verifySequence {
            resource.read(any())
            mapper.toCore(any())
        }
    }

    @Test
    fun `It should find event by name`() {
        val event1 = TestEvents.Lscc2019.points1
        val event2 = TestEvents.Lscc2019.points2
        every { resource.stream() } returns Stream.of(
            mockk { every { name } returns event1.name },
            mockk { every { name } returns event2.name }
        )
        every { mapper.toCore(match { it.name == event1.name }) } returns event1
        every { mapper.toCore(match { it.name == event2.name }) } returns event2

        val actual = service.findByName(event2.name)

        assertThat(actual).isSameAs(event2)
    }

    @Test
    fun `It should list events`() {
        val events = listOf(
            TestEvents.Lscc2019Simplified.points1,
            TestEvents.Lscc2019Simplified.points2,
            TestEvents.Lscc2019Simplified.points3
        )
        every { resource.stream() } returns Stream.of(
            mockk { every { name } returns events[0].name },
            mockk { every { name } returns events[1].name },
            mockk { every { name } returns events[2].name }
        )
        every { mapper.toCore(match { it.name == events[0].name }) } returns events[0]
        every { mapper.toCore(match { it.name == events[1].name }) } returns events[1]
        every { mapper.toCore(match { it.name == events[2].name }) } returns events[2]

        val actual = service.list()

        assertThat(actual).all {
            hasSize(3)
            isEqualTo(events)
        }
        verify(exactly = 3) { mapper.toCore(any()) }
    }

    @Test
    fun `It should check event`() {
        val checkCrispyFishPeopleMap = emptyMap<Event.CrispyFishMetadata.PeopleMapKey, Person>()
        val msrEventId = "msr-event-id"
        val eventCrispyFish: Event.CrispyFishMetadata = mockk {
            every { peopleMap } returns checkCrispyFishPeopleMap
        }
        val check: Event = mockk {
            every { crispyFish } returns eventCrispyFish
            every { requireCrispyFish() } returns eventCrispyFish
            every { motorsportReg } returns mockk {
                every { id } returns msrEventId
            }
        }
        val context: CrispyFishEventMappingContext = mockk()
        every { crispyFishEventMappingContextService.load(eventCrispyFish) } returns context
        val callbackSlot: CapturingSlot<EventCrispyFishPersonMapVerifier.Callback> = slot()
        every {
            eventCrispyFishPersonMapVerifier.verify(
                event = check,
                context = context,
                callback = capture(callbackSlot)
            )
        } answers {
            val callback = callbackSlot.captured
            callback.onMapped(registration = mockk(), entry = mockk())
            repeat(2) { callback.onUnmappedMotorsportRegPersonExactMatch(registration = mockk(), entry = mockk()) }
            repeat(3) { callback.onUnmappedClubMemberIdNull(registration = mockk()) }
            repeat(4) { callback.onUnmappedClubMemberIdNotFound(registration = mockk()) }
            repeat(5) {
                callback.onUnmappedClubMemberIdAmbiguous(
                    registration = mockk(),
                    peopleWithClubMemberId = mockk()
                )
            }
            repeat(6) { callback.onUnmappedClubMemberIdMatchButNameMismatch(registration = mockk(), person = mockk()) }
            repeat(7) { callback.onUnmappedExactMatch(registration = mockk(), person = mockk()) }
            repeat(8) { callback.onUnused(key = mockk(), person = mockk()) }
        }
        val allParticipants = emptyList<Participant>()
        every { participantService.list(check) } returns Result.success(allParticipants)
        val allRuns = listOf(
            Run(
                sequence = 1,
                participant = Participant(
                    person = null,
                    firstName = null,
                    lastName = null,
                    signage = Signage(
                        number = "42",
                        classing = Classing(group = null, handicap = TestClasses.Lscc2019.CS),
                    ),
                    car = null,
                    seasonPointsEligible = false,
                    sponsor = null
                ),
            )
        )
        every { runService.list(event = check) } returns Result.success(allRuns)
        every {
            runWithInvalidSignageVerifier.verify(
                allParticipants = allParticipants,
                allRuns = allRuns
            )
        } returns allRuns

        val actual = service.check(check = check)

        assertThat(actual.unmappedMotorsportRegPersonMatches).hasSize(2)
        assertThat(actual.unmappedClubMemberIdNullRegistrations).hasSize(3)
        assertThat(actual.unmappedClubMemberIdNotFoundRegistrations).hasSize(4)
        assertThat(actual.unmappedClubMemberIdAmbiguousRegistrations).hasSize(5)
        assertThat(actual.unmappedClubMemberIdMatchButNameMismatchRegistrations).hasSize(6)
        assertThat(actual.unmappedExactMatchRegistrations).hasSize(7)
        assertThat(actual.unusedPeopleMapKeys).hasSize(8)
    }

    @Test
    fun `It should update event`() {
        val update = TestEvents.Lscc2019.points1.copy(
            name = "Simulate basic edit such as rename, lifecycle=>PRE",
            lifecycle = Event.Lifecycle.CREATE
        )
        justRun { persistConstraints.assess(update) }
        every { mapper.toSnoozle(update) } returns mockk()
        justRun { resource.update(any()) }

        service.update(update)

        verifySequence {
            mapper.toSnoozle(update)
            resource.update(any())
        }
    }

    @Test
    fun `It should delete event`() {
        val delete = TestEvents.Lscc2019.points1
        val snoozleEvent: EventEntity = mockk()
        justRun { deleteConstraints.assess(delete) }
        every { mapper.toSnoozle(delete) } returns snoozleEvent
        every { resource.delete(snoozleEvent) } returns Unit

        assertDoesNotThrow {
            service.delete(delete)
        }

        verifySequence {
            mapper.toSnoozle(delete)
            resource.delete(snoozleEvent)
        }
    }


}