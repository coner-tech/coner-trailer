package org.coner.trailer.io.verification

import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import org.coner.trailer.io.service.PersonService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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
    fun `It should verify sufficiently uniquely identifiable people's registrations`() {
        TODO()
    }

    @Test
    fun `It should fail when registration lacks club member ID`() {
        TODO()
    }

    @Test
    fun `It should fail when person with registration's club member ID is not found`() {
        TODO()
    }

    @Test
    fun `It should fail when there are multiple people with registration's club member ID`() {
        TODO()
    }
}