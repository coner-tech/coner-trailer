package org.coner.trailer.datasource.crispyfish.eventsresults

import assertk.all
import assertk.assertThat
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.coner.crispyfish.model.RegistrationResult
import org.coner.trailer.TestParticipants
import org.coner.trailer.TestPeople
import org.coner.trailer.Time
import org.coner.trailer.datasource.crispyfish.ParticipantMapper
import org.coner.trailer.datasource.crispyfish.TestRegistrations
import org.coner.trailer.eventresults.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ParticipantResultMapperTest {

    @MockK
    private lateinit var participantMapper: ParticipantMapper

    @BeforeEach
    fun before() {
        MockKAnnotations.init(this)
        mockkObject(ResultRunMapper)
    }

    @AfterEach
    fun after() {
        unmockkAll()
    }

    @Test
    fun `It should map (core) ParticipantResult? to null when (CF) RegistrationResult position is null`() {
        // indicates a registration for which no result is available
        val noRegistrationResult = RegistrationResult(time = "", position = null)
        val registration = TestRegistrations.Lscc2019Points1.BRANDY_HUFF.copy(
                rawResult = noRegistrationResult,
                paxResult = noRegistrationResult,
                classResult = noRegistrationResult
        )
        val participantResultMapper = ParticipantResultMapper(
                participantMapper,
                memberIdToPeople = emptyMap()
        )

        val actual = participantResultMapper.map(
                cfRegistration = registration,
                cfResult = noRegistrationResult
        )

        assertThat(actual).isNull()
    }

    @Test
    fun `It should map (core) ParticipantResult from (CF) Registration, arbitrary RegistrationResult, and Map of MemberId to People`() {
        val registration = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON
        val result = registration.classResult
        val expectedPerson = TestPeople.REBECCA_JACKSON
        val expectedParticipant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON
        val memberIdToPeople = mapOf(checkNotNull(expectedPerson.memberId) to expectedPerson)
        every {
            participantMapper.map(fromRegistration = registration, withPerson = expectedPerson)
        }.returns(expectedParticipant)
        val expectedScoredRuns = listOf(
                ResultRun(time = Time("52.749")),
                ResultRun(time = Time("53.175")),
                ResultRun(time = Time("52.130")),
                ResultRun(time = Time("52.117")),
                ResultRun(time = Time("51.408"), personalBest = true)
        )
        every {
            ResultRunMapper.map(
                    crispyFishRegistrationRuns = registration.runs,
                    crispyFishRegistrationBestRun = registration.bestRun
            )
        }.returns(expectedScoredRuns)
        val participantResultMapper = ParticipantResultMapper(
                participantMapper,
                memberIdToPeople
        )

        val actual = participantResultMapper.map(
                cfRegistration = registration,
                cfResult = result
        )

        assertThat(actual).isNotNull().all {
            hasPosition(3)
            hasParticipant(expectedParticipant)
            hasScoredRuns(expectedScoredRuns)
            marginOfLoss().isNull() // not supported
            marginOfVictory().isNull() // not supported
        }
    }
}