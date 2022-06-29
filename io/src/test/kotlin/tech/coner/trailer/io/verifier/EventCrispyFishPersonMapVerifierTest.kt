package tech.coner.trailer.io.verifier

import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import tech.coner.trailer.Event
import tech.coner.trailer.TestClasses
import tech.coner.trailer.TestParticipants
import tech.coner.trailer.TestPeople
import tech.coner.trailer.datasource.crispyfish.CrispyFishClassingMapper
import tech.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import tech.coner.trailer.datasource.crispyfish.TestRegistrations
import tech.coner.trailer.io.service.CrispyFishClassService
import tech.coner.trailer.io.service.MotorsportRegPeopleMapService
import tech.coner.trailer.io.service.PersonService
import java.nio.file.Paths

@ExtendWith(MockKExtension::class)
class EventCrispyFishPersonMapVerifierTest {

    lateinit var verifier: EventCrispyFishPersonMapVerifier

    @MockK lateinit var personService: PersonService
    @MockK lateinit var crispyFishClassService: CrispyFishClassService
    @MockK lateinit var crispyFishClassingMapper: CrispyFishClassingMapper
    @MockK lateinit var motorsportRegPeopleMapService: MotorsportRegPeopleMapService

    @MockK lateinit var context: CrispyFishEventMappingContext
    @MockK lateinit var callback: EventCrispyFishPersonMapVerifier.Callback

    @BeforeEach
    fun before() {
        verifier = EventCrispyFishPersonMapVerifier(
            personService = personService,
            crispyFishClassService = crispyFishClassService,
            crispyFishClassingMapper = crispyFishClassingMapper,
            motorsportRegPeopleMapService = motorsportRegPeopleMapService
        )
    }

    @Test
    fun `When person mapped, it should invoke onMapped`() {
        val person = TestPeople.REBECCA_JACKSON
        val registration = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON.copy(
            memberNumber = null
        )
        val participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON.copy(
            person = null
        )
        val key = Event.CrispyFishMetadata.PeopleMapKey(
            classing = requireNotNull(participant.signage?.classing),
            number = requireNotNull(participant.signage?.number),
            firstName = requireNotNull(registration.firstName),
            lastName = requireNotNull(registration.lastName)
        )
        val event: Event = mockk {
            every { crispyFish } returns mockk {
                every { peopleMap } returns mapOf(key to person)
                every { classDefinitionFile } returns Paths.get("classDefinitionFile")
            }
            every { requireCrispyFish() } returns crispyFish!!
        }
        val allRegistrations = listOf(registration)
        every { context.allRegistrations } returns allRegistrations
        every { personService.list() } returns listOf(person)
        every { crispyFishClassingMapper.toCore(any(), registration) } returns participant.signage?.classing
        every { crispyFishClassService.loadAllByAbbreviation(any()) } returns TestClasses.Lscc2019.allByAbbreviation
        justRun { callback.onMapped(any(), any()) }

        verifier.verify(
            event = event,
            context = context,
            callback = callback
        )

        verifySequence {
            personService.list()
            crispyFishClassService.loadAllByAbbreviation(any())
            callback.onMapped(registration, key to person)
        }
    }

    @Test
    fun `When no person mapped but registration can cross-reference to person via motorsportreg it should invoke such`() {
        val person = TestPeople.REBECCA_JACKSON
        every { personService.list() } returns listOf(person)
        val participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON
        val registration = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON
        every { context.allRegistrations } returns listOf(registration)
        val crossReference = Event.CrispyFishMetadata.PeopleMapKey(
            classing = requireNotNull(participant.signage?.classing),
            number = requireNotNull(participant.signage?.number),
            firstName = requireNotNull(participant.firstName),
            lastName = requireNotNull(participant.lastName)
        ) to person
        every { motorsportRegPeopleMapService.assemble(any(), any()) } returns mapOf(crossReference)
        every {
            crispyFishClassService.loadAllByAbbreviation(any())
        } returns TestClasses.Lscc2019.allByAbbreviation
        every { crispyFishClassingMapper.toCore(any(), registration) } returns requireNotNull(participant.signage?.classing)
        val event: Event = mockk {
            every { crispyFish } returns mockk {
                every { peopleMap } returns emptyMap()
                every { classDefinitionFile } returns Paths.get("classDefinitionFile")
            }
            every { requireCrispyFish() } returns crispyFish!!
        }
        justRun { callback.onUnmappedMotorsportRegPersonExactMatch(any(), any()) }

        verifier.verify(
            event = event,
            context = context,
            callback = callback
        )

        verifySequence {
            personService.list()
            crispyFishClassingMapper.toCore(any(), registration)
            callback.onUnmappedMotorsportRegPersonExactMatch(registration, crossReference)
        }
    }

    @Test
    fun `When no person mapped and registration lacks club member ID it should invoke onUnmappedClubMemberIdNull`() {
        val registration = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON.copy(
            memberNumber = null
        )
        val participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON.copy(
            person = null
        )
        val person = TestPeople.REBECCA_JACKSON
        val event: Event = mockk {
            every { crispyFish } returns mockk {
                every { peopleMap } returns emptyMap()
                every { classDefinitionFile } returns Paths.get("classDefinitionFile")
            }
            every { requireCrispyFish() } returns crispyFish!!
        }
        val allRegistrations = listOf(registration)
        every { context.allRegistrations } returns allRegistrations
        every { personService.list() } returns listOf(person)
        every { motorsportRegPeopleMapService.assemble(any(), any()) } returns emptyMap()
        every { crispyFishClassService.loadAllByAbbreviation(any()) } returns TestClasses.Lscc2019.allByAbbreviation
        every { crispyFishClassingMapper.toCore(any(), registration) } returns requireNotNull(participant.signage?.classing)
        justRun { callback.onUnmappedClubMemberIdNull(any()) }

        verifier.verify(
            event = event,
            context = context,
            callback = callback
        )

        verifySequence {
            personService.list()
            crispyFishClassingMapper.toCore(any(), registration)
            callback.onUnmappedClubMemberIdNull(registration)
        }
    }

    @Test
    fun `When no person mapped and registration's club member ID doesn't match any people, it should invoke onUnmappedClubMemberIdNotFound`() {
        val registration = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON
        val participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON.copy(
            person = null
        )
        val event: Event = mockk {
            every { crispyFish } returns mockk {
                every { peopleMap } returns emptyMap()
                every { classDefinitionFile } returns Paths.get("classDefinitionFile")
            }
            every { requireCrispyFish() } returns crispyFish!!
        }
        val allRegistrations = listOf(registration)
        every { context.allRegistrations } returns allRegistrations
        every { personService.list() } returns emptyList()
        every { motorsportRegPeopleMapService.assemble(any(), any()) } returns emptyMap()
        every { crispyFishClassService.loadAllByAbbreviation(any()) } returns TestClasses.Lscc2019.allByAbbreviation
        every { crispyFishClassingMapper.toCore(any(), registration) } returns requireNotNull(participant.signage?.classing)
        justRun { callback.onUnmappedClubMemberIdNotFound(any()) }

        verifier.verify(
            event = event,
            context = context,
            callback = callback
        )

        verifySequence {
            personService.list()
            crispyFishClassingMapper.toCore(any(), registration)
            callback.onUnmappedClubMemberIdNotFound(registration)
        }
    }

    @Test
    fun `When no person mapped and registration club member ID matches multiple people, it should invoke onUnmappedClubMemberIdAmbiguous`() {
        val registrations = listOf(
            TestRegistrations.Lscc2019Points1.REBECCA_JACKSON,
            TestRegistrations.Lscc2019Points1.BRANDY_HUFF.copy(
                memberNumber = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON.memberNumber
            )
        )
        val participants = listOf(
            TestParticipants.Lscc2019Points1.REBECCA_JACKSON.copy(
                person = null
            ),
            TestParticipants.Lscc2019Points1.BRANDY_HUFF.copy(
                person = null
            )
        )
        val people = listOf(
            TestPeople.REBECCA_JACKSON,
            TestPeople.BRANDY_HUFF.copy(
                clubMemberId = TestPeople.REBECCA_JACKSON.clubMemberId
            )
        )
        val event: Event = mockk {
            every { crispyFish } returns mockk {
                every { peopleMap } returns emptyMap()
                every { classDefinitionFile } returns Paths.get("classDefinitionFile")
            }
            every { requireCrispyFish() } returns crispyFish!!
        }
        every { context.allRegistrations } returns registrations
        every { personService.list() } returns people
        every { motorsportRegPeopleMapService.assemble(any(), any()) } returns emptyMap()
        every { crispyFishClassService.loadAllByAbbreviation(any()) } returns TestClasses.Lscc2019.allByAbbreviation
        every { crispyFishClassingMapper.toCore(any(), registrations[0]) } returns requireNotNull(participants[0].signage?.classing)
        every { crispyFishClassingMapper.toCore(any(), registrations[1]) } returns requireNotNull(participants[1].signage?.classing)
        justRun { callback.onUnmappedClubMemberIdAmbiguous(any(), any()) }

        verifier.verify(
            event = event,
            context = context,
            callback = callback
        )

        verifySequence {
            personService.list()
            crispyFishClassingMapper.toCore(any(), registrations[0])
            callback.onUnmappedClubMemberIdAmbiguous(registrations[0], people)
            crispyFishClassingMapper.toCore(any(), registrations[1])
            callback.onUnmappedClubMemberIdAmbiguous(registrations[1], people)
        }
    }

    @Test
    fun `When no person mapped and registration matches a club member ID but not first or last name, it should invoke onUnmappedClubMemberIdMatchButNameMismatch`() {
        val people = listOf(
            TestPeople.REBECCA_JACKSON,
            TestPeople.BRANDY_HUFF
        )
        every { personService.list() } returns people
        val participants = listOf(
            TestParticipants.Lscc2019Points1.REBECCA_JACKSON,
            TestParticipants.Lscc2019Points1.BRANDY_HUFF
        )
        val registrations = listOf(
            TestRegistrations.Lscc2019Points1.REBECCA_JACKSON.copy(firstName = "Not Rebecca"),
            TestRegistrations.Lscc2019Points1.BRANDY_HUFF.copy(firstName = "Not Brandy")
        )
        every { context.allRegistrations } returns registrations
        every { motorsportRegPeopleMapService.assemble(any(), any()) } returns emptyMap()
        every { crispyFishClassService.loadAllByAbbreviation(any()) } returns TestClasses.Lscc2019.allByAbbreviation
        every { crispyFishClassingMapper.toCore(any(), registrations[0]) } returns requireNotNull(participants[0].signage?.classing)
        every { crispyFishClassingMapper.toCore(any(), registrations[1]) } returns requireNotNull(participants[1].signage?.classing)
        val event: Event = mockk {
            every { crispyFish } returns mockk {
                every { peopleMap } returns emptyMap()
                every { classDefinitionFile } returns Paths.get("classDefinitionFile")
            }
            every { requireCrispyFish() } returns crispyFish!!
        }
        justRun { callback.onUnmappedClubMemberIdMatchButNameMismatch(any(), any()) }

        verifier.verify(
            event = event,
            context = context,
            callback = callback
        )

        verifySequence {
            personService.list()
            crispyFishClassingMapper.toCore(any(), registrations[0])
            callback.onUnmappedClubMemberIdMatchButNameMismatch(registrations[0], people[0])
            crispyFishClassingMapper.toCore(any(), registrations[1])
            callback.onUnmappedClubMemberIdMatchButNameMismatch(registrations[1], people[1])
        }
    }

    @Test
    fun `When no person mapped and registration club member ID and first and last name matches single person, it should invoke onUnmappedExactMatch`() {
        val person = TestPeople.REBECCA_JACKSON
        every { personService.list() } returns listOf(person)
        val participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON
        val registration = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON
        every { context.allRegistrations } returns listOf(registration)
        every { motorsportRegPeopleMapService.assemble(any(), any()) } returns emptyMap()
        every { crispyFishClassService.loadAllByAbbreviation(any()) } returns TestClasses.Lscc2019.allByAbbreviation
        every { crispyFishClassingMapper.toCore(any(), registration) } returns requireNotNull(participant.signage?.classing)
        val event: Event = mockk {
            every { crispyFish } returns mockk {
                every { peopleMap } returns emptyMap()
                every { classDefinitionFile } returns Paths.get("classDefinitionFile")
            }
            every { requireCrispyFish() } returns crispyFish!!
        }
        justRun { callback.onUnmappedExactMatch(registration, person) }

        verifier.verify(
            event = event,
            context = context,
            callback = callback
        )

        verifySequence {
            personService.list()
            crispyFishClassingMapper.toCore(any(), registration)
            callback.onUnmappedExactMatch(registration, person)
        }
    }

    @Test
    fun `When there is an unused mapping, it should invoke onUnused`() {
        val person = TestPeople.REBECCA_JACKSON
        every { personService.list() } returns emptyList()
        val participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON
        val registration = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON
        every { crispyFishClassService.loadAllByAbbreviation(any()) } returns TestClasses.Lscc2019.allByAbbreviation
        every { context.allRegistrations } returns emptyList()
        val key = Event.CrispyFishMetadata.PeopleMapKey(
            classing = requireNotNull(participant.signage?.classing),
            number = requireNotNull(participant.signage?.number),
            firstName = requireNotNull(registration.firstName),
            lastName = requireNotNull(registration.lastName)
        )
        val event: Event = mockk {
            every { crispyFish } returns mockk {
                every { peopleMap } returns mapOf(key to person)
                every { classDefinitionFile } returns Paths.get("classDefinitionFile")
            }
            every { requireCrispyFish() } returns crispyFish!!
        }
        justRun { callback.onUnused(any(), any()) }

        verifier.verify(
            event = event,
            context = context,
            callback = callback
        )

        verifySequence {
            personService.list()
            callback.onUnused(key, person)
        }
        confirmVerified(crispyFishClassingMapper)
    }
}