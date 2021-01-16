package org.coner.trailer.io.verification

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verifySequence
import org.coner.trailer.*
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import org.coner.trailer.datasource.crispyfish.TestRegistrations
import org.coner.trailer.io.service.PersonService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class EventCrispyFishForcePersonVerificationTest {

    lateinit var verifier: EventCrispyFishPersonMapVerifier

    @MockK lateinit var personService: PersonService
    @MockK lateinit var crispyFishParticipantMapper: CrispyFishParticipantMapper

    @BeforeEach
    fun before() {
        verifier = EventCrispyFishPersonMapVerifier(
            personService = personService,
            crispyFishParticipantMapper = crispyFishParticipantMapper
        )
    }

    @Test
    fun `It should pass registrations with forced people`(
        @MockK context: CrispyFishEventMappingContext,
        @MockK callback: EventCrispyFishPersonMapVerifier.Callback
    ) {
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

        assertDoesNotThrow {
            verifier.verify(
                context = context,
                peopleMap = forcePeople,
                callback = callback
            )
        }

        verifySequence {
            personService.list()
            crispyFishParticipantMapper.toCoreSignage(
                context = context,
                crispyFish = registration
            )
        }
    }

    @Test
    fun `It should fail when registration lacks club member ID`(
        @MockK context: CrispyFishEventMappingContext,
        @MockK callback: EventCrispyFishPersonMapVerifier.Callback
    ) {
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
        justRun { callback.onRegistrationWithoutClubMemberId(registration) }

        assertThrows<VerificationException> {
            verifier.verify(
                context = context,
                peopleMap = peopleMap,
                callback = callback
            )
        }

        verifySequence {
            personService.list()
            crispyFishParticipantMapper.toCoreSignage(
                context = context,
                crispyFish = registration
            )
            callback.onRegistrationWithoutClubMemberId(registration)
        }
    }

    @Test
    fun `It should fail when person with registration's club member ID is not found`(
        @MockK context: CrispyFishEventMappingContext,
        @MockK callback: EventCrispyFishPersonMapVerifier.Callback
    ) {
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
        justRun { callback.onPersonWithClubMemberIdNotFound(registration) }

        assertThrows<VerificationException> {
            verifier.verify(
                context = context,
                peopleMap = peopleMap,
                callback = callback
            )
        }

        verifySequence {
            personService.list()
            crispyFishParticipantMapper.toCoreSignage(
                context = context,
                crispyFish = registration
            )
            callback.onPersonWithClubMemberIdNotFound(registration)
        }
    }

    @Test
    fun `It should fail when there are multiple people with registration's club member ID`(
        @MockK context: CrispyFishEventMappingContext,
        @MockK callback: EventCrispyFishPersonMapVerifier.Callback
    ) {
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
        justRun { callback.onMultiplePeopleWithClubMemberIdFound(any()) }

        assertThrows<VerificationException> {
            verifier.verify(
                context = context,
                peopleMap = peopleMap,
                callback = callback
            )
        }

        verifySequence {
            personService.list()
            crispyFishParticipantMapper.toCoreSignage(
                context = context,
                crispyFish = registrations[0]
            )
            callback.onMultiplePeopleWithClubMemberIdFound(registrations[0])
            crispyFishParticipantMapper.toCoreSignage(
                context = context,
                crispyFish = registrations[1]
            )
            callback.onMultiplePeopleWithClubMemberIdFound(registrations[1])
        }
    }
}