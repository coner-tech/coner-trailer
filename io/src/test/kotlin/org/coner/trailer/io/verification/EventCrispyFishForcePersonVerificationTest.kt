package org.coner.trailer.io.verification

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verifySequence
import org.coner.crispyfish.model.Registration
import org.coner.trailer.Participant
import org.coner.trailer.Person
import org.coner.trailer.TestParticipants
import org.coner.trailer.TestPeople
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

    lateinit var verification: EventCrispyFishForcePersonVerification

    @MockK lateinit var personService: PersonService
    @MockK lateinit var crispyFishParticipantMapper: CrispyFishParticipantMapper

    @BeforeEach
    fun before() {
        verification = EventCrispyFishForcePersonVerification(
            personService = personService,
            crispyFishParticipantMapper = crispyFishParticipantMapper
        )
    }

    @Test
    fun `It should pass registrations with forced people`(
        @MockK context: CrispyFishEventMappingContext,
        @MockK failureCallback: EventCrispyFishForcePersonVerification.FailureCallback
    ) {
        val registration = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON.copy(
            memberNumber = null
        )
        val participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON.copy(
            person = null
        )
        val person = TestPeople.REBECCA_JACKSON
        val forcePeople = mapOf(participant.signage to person)
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
            verification.verifyRegistrations(
                context = context,
                forcePeople = forcePeople,
                failureCallback = failureCallback
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
        @MockK failureCallback: EventCrispyFishForcePersonVerification.FailureCallback
    ) {
        val registration = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON.copy(
            memberNumber = null
        )
        val participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON.copy(
            person = null
        )
        val person = TestPeople.REBECCA_JACKSON
        val forcePeople = emptyMap<Participant.Signage, Person>()
        val allRegistrations = listOf(registration)
        every { context.allRegistrations } returns allRegistrations
        every { personService.list() } returns listOf(person)
        every {
            crispyFishParticipantMapper.toCoreSignage(
                context = context,
                crispyFish = registration
            )
        } returns participant.signage
        justRun { failureCallback.onRegistrationWithoutClubMemberId(registration) }

        assertThrows<VerificationException> {
            verification.verifyRegistrations(
                context = context,
                forcePeople = forcePeople,
                failureCallback = failureCallback
            )
        }

        verifySequence {
            personService.list()
            crispyFishParticipantMapper.toCoreSignage(
                context = context,
                crispyFish = registration
            )
            failureCallback.onRegistrationWithoutClubMemberId(registration)
        }
    }

    @Test
    fun `It should fail when person with registration's club member ID is not found`(
        @MockK context: CrispyFishEventMappingContext,
        @MockK failureCallback: EventCrispyFishForcePersonVerification.FailureCallback
    ) {
        val registration = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON
        val participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON.copy(
            person = null
        )
        val forcePeople = emptyMap<Participant.Signage, Person>()
        val allRegistrations = listOf(registration)
        every { context.allRegistrations } returns allRegistrations
        every { personService.list() } returns emptyList()
        every {
            crispyFishParticipantMapper.toCoreSignage(
                context = context,
                crispyFish = registration
            )
        } returns participant.signage
        justRun { failureCallback.onPersonWithClubMemberIdNotFound(registration) }

        assertThrows<VerificationException> {
            verification.verifyRegistrations(
                context = context,
                forcePeople = forcePeople,
                failureCallback = failureCallback
            )
        }

        verifySequence {
            personService.list()
            crispyFishParticipantMapper.toCoreSignage(
                context = context,
                crispyFish = registration
            )
            failureCallback.onPersonWithClubMemberIdNotFound(registration)
        }
    }

    @Test
    fun `It should fail when there are multiple people with registration's club member ID`() {
        TODO()
    }
}