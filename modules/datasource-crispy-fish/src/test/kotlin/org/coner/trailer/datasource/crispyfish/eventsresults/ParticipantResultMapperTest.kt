package org.coner.trailer.datasource.crispyfish.eventsresults

import assertk.all
import assertk.assertThat
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.coner.crispyfish.model.RegistrationResult
import org.coner.trailer.*
import org.coner.trailer.datasource.crispyfish.ParticipantMapper
import org.coner.trailer.datasource.crispyfish.TestRegistrations
import org.coner.trailer.eventresults.ResultRun
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ParticipantResultMapperTest {

    @BeforeEach
    fun before() {
        mockkObject(ParticipantMapper)
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

        val actual = ParticipantResultMapper.map(
                crispyFishRegistration = registration,
                crispyFishResult = noRegistrationResult,
                peopleByMemberId = emptyMap()
        )

        assertThat(actual).isNull()
    }

    @Test
    fun `It should map (core) ParticipantResult from (CF) Registration, arbitrary RegistrationResult, and Map of MemberId to People`() {
        val registration = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON
        val result = registration.classResult
        val expectedPerson = TestPeople.REBECCA_JACKSON
        val expectedParticipant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON
        val peopleByMemberId = mapOf(checkNotNull(expectedPerson.memberId) to expectedPerson)
        every {
            ParticipantMapper.map(fromRegistration = registration, withPerson = expectedPerson)
        }.returns(expectedParticipant)
        val expectedScoredRuns = listOf(
                ResultRun(time = Time("52.749")),
                ResultRun(time = Time("53.175")),
                ResultRun(time = Time("52.130")),
                ResultRun(time = Time("52.117")),
                ResultRun(time = Time("51.408"))
        )
        every {
            ResultRunMapper.map(
                    crispyFishRegistrationRuns = registration.runs,
                    crispyFishRegistrationBestRun = registration.bestRun
            )
        }.returns(expectedScoredRuns)

        val actual = ParticipantResultMapper.map(
                crispyFishRegistration = registration,
                crispyFishResult = result,
                peopleByMemberId = peopleByMemberId
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