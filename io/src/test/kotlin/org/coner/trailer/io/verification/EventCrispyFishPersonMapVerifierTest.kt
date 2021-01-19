package org.coner.trailer.io.verification

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verifySequence
import org.coner.trailer.Event
import org.coner.trailer.Person
import org.coner.trailer.TestParticipants
import org.coner.trailer.TestPeople
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import org.coner.trailer.datasource.crispyfish.TestRegistrations
import org.coner.trailer.io.service.PersonService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class EventCrispyFishPersonMapVerifierTest {

    lateinit var verifier: EventCrispyFishPersonMapVerifier

    @MockK lateinit var personService: PersonService
    @MockK lateinit var crispyFishParticipantMapper: CrispyFishParticipantMapper

    @MockK lateinit var context: CrispyFishEventMappingContext
    @MockK lateinit var callback: EventCrispyFishPersonMapVerifier.Callback

    @BeforeEach
    fun before() {
        verifier = EventCrispyFishPersonMapVerifier(
            personService = personService,
            crispyFishParticipantMapper = crispyFishParticipantMapper
        )
    }

    @Test
    fun `When person mapped, it should invoke onMapped`() {
        val registration = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON.copy(
            memberNumber = null
        )
        val participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON.copy(
            person = null
        )
        val person = TestPeople.REBECCA_JACKSON
        val key = Event.CrispyFishMetadata.PeopleMapKey(participant.signage, registration.firstName, registration.lastName)
        val forcePeople = mapOf(key to person)
        val allRegistrations = listOf(registration)
        every { context.allRegistrations } returns allRegistrations
        every { personService.list() } returns listOf(person)
        every {
            crispyFishParticipantMapper.toCoreSignage(
                context = context,
                crispyFish = registration
            )
        } returns participant.signage
        justRun { callback.onMapped(registration, person) }

        verifier.verify(
            context = context,
            peopleMap = forcePeople,
            callback = callback
        )

        verifySequence {
            personService.list()
            crispyFishParticipantMapper.toCoreSignage(
                context = context,
                crispyFish = registration
            )
            callback.onMapped(registration, person)
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
        val peopleMap = emptyMap<Event.CrispyFishMetadata.PeopleMapKey, Person>()
        val allRegistrations = listOf(registration)
        every { context.allRegistrations } returns allRegistrations
        every { personService.list() } returns listOf(person)
        every {
            crispyFishParticipantMapper.toCoreSignage(
                context = context,
                crispyFish = registration
            )
        } returns participant.signage
        justRun { callback.onUnmappedClubMemberIdNull(registration) }

        verifier.verify(
            context = context,
            peopleMap = peopleMap,
            callback = callback
        )

        verifySequence {
            personService.list()
            crispyFishParticipantMapper.toCoreSignage(
                context = context,
                crispyFish = registration
            )
            callback.onUnmappedClubMemberIdNull(registration)
        }
    }

    @Test
    fun `When no person mapped and registration's club member ID doesn't match any people, it should invoke onUnmappedClubMemberIdNotFound`() {
        val registration = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON
        val participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON.copy(
            person = null
        )
        val peopleMap = emptyMap<Event.CrispyFishMetadata.PeopleMapKey, Person>()
        val allRegistrations = listOf(registration)
        every { context.allRegistrations } returns allRegistrations
        every { personService.list() } returns emptyList()
        every {
            crispyFishParticipantMapper.toCoreSignage(
                context = context,
                crispyFish = registration
            )
        } returns participant.signage
        justRun { callback.onUnmappedClubMemberIdNotFound(registration) }

        verifier.verify(
            context = context,
            peopleMap = peopleMap,
            callback = callback
        )

        verifySequence {
            personService.list()
            crispyFishParticipantMapper.toCoreSignage(
                context = context,
                crispyFish = registration
            )
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
        val peopleMap = emptyMap<Event.CrispyFishMetadata.PeopleMapKey, Person>()
        every { context.allRegistrations } returns registrations
        every { personService.list() } returns people
        every {
            crispyFishParticipantMapper.toCoreSignage(
                context = context,
                crispyFish = registrations[0]
            )
        } returns participants[0].signage
        every {
            crispyFishParticipantMapper.toCoreSignage(
                context = context,
                crispyFish = registrations[1]
            )
        } returns participants[1].signage
        justRun { callback.onUnmappedClubMemberIdAmbiguous(any(), any()) }

        verifier.verify(
            context = context,
            peopleMap = peopleMap,
            callback = callback
        )

        verifySequence {
            personService.list()
            crispyFishParticipantMapper.toCoreSignage(
                context = context,
                crispyFish = registrations[0]
            )
            callback.onUnmappedClubMemberIdAmbiguous(registrations[0], people)
            crispyFishParticipantMapper.toCoreSignage(
                context = context,
                crispyFish = registrations[1]
            )
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
        every { crispyFishParticipantMapper.toCoreSignage(context, registrations[0]) } returns participants[0].signage
        every { crispyFishParticipantMapper.toCoreSignage(context, registrations[1]) } returns participants[1].signage
        val peopleMap: Map<Event.CrispyFishMetadata.PeopleMapKey, Person> = emptyMap()
        justRun { callback.onUnmappedClubMemberIdMatchButNameMismatch(any(), any()) }

        verifier.verify(
            context = context,
            peopleMap = peopleMap,
            callback = callback
        )

        verifySequence {
            personService.list()
            crispyFishParticipantMapper.toCoreSignage(context, registrations[0])
            callback.onUnmappedClubMemberIdMatchButNameMismatch(registrations[0], people[0])
            crispyFishParticipantMapper.toCoreSignage(context, registrations[1])
            callback.onUnmappedClubMemberIdMatchButNameMismatch(registrations[1], people[1])
        }
    }

    @Test
    fun `When no person mapped and registration club member ID and first and last name matches single person, it should invoke onUnmappedExactMatch`() {
        TODO()
    }
}