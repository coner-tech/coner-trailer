package org.coner.trailer.datasource.crispyfish.eventresults

import assertk.all
import assertk.assertThat
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.coner.crispyfish.model.RegistrationResult
import org.coner.trailer.Event
import org.coner.trailer.TestParticipants
import org.coner.trailer.TestPeople
import org.coner.trailer.Time
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import org.coner.trailer.datasource.crispyfish.eventsresults.ParticipantResultMapper
import org.coner.trailer.datasource.crispyfish.eventsresults.ResultRunMapper
import org.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import org.coner.trailer.eventresults.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi

@ExperimentalPathApi
class ParticipantResultMapperTest {

    @MockK
    private lateinit var crispyFishParticipantMapper: CrispyFishParticipantMapper

    @TempDir lateinit var fixtureRoot: Path

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
        val registration = org.coner.trailer.datasource.crispyfish.TestRegistrations.Lscc2019Points1.BRANDY_HUFF.copy(
            rawResult = noRegistrationResult,
            paxResult = noRegistrationResult,
            classResult = noRegistrationResult
        )
        val participantResultMapper = ParticipantResultMapper(
            crispyFishParticipantMapper
        )
        val seasonFixture = SeasonFixture.Lscc2019Simplified(fixtureRoot)
        val context = CrispyFishEventMappingContext(
            allClassDefinitions = seasonFixture.classDefinitions,
            allRegistrations = seasonFixture.event1.registrations()
        )
        val eventCrispyFishMetadata: Event.CrispyFishMetadata = mockk() {
            every { peopleMap } returns emptyMap()
        }

        val actual = participantResultMapper.toCore(
            eventCrispyFishMetadata = eventCrispyFishMetadata,
            context = context,
            cfRegistration = registration,
            cfResult = noRegistrationResult
        )

        assertThat(actual).isNull()
    }

    @Test
    fun `It should map (core) ParticipantResult from (CF) Registration, arbitrary RegistrationResult, and Map of MemberId to People`() {
        val registration = org.coner.trailer.datasource.crispyfish.TestRegistrations.Lscc2019Points1.REBECCA_JACKSON
        val result = registration.classResult
        val expectedPerson = TestPeople.REBECCA_JACKSON
        val expectedParticipant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON
        val usePeopleMap = mapOf(
            Event.CrispyFishMetadata.PeopleMapKey(
                signage = expectedParticipant.signage,
                firstName = expectedParticipant.firstName,
                lastName = expectedPerson.lastName
            ) to expectedPerson
        )
        val seasonFixture = SeasonFixture.Lscc2019Simplified(fixtureRoot)
        val context = CrispyFishEventMappingContext(
            allClassDefinitions = seasonFixture.classDefinitions,
            allRegistrations = seasonFixture.event1.registrations()
        )
        every {
            crispyFishParticipantMapper.toCore(
                context = context,
                fromRegistration = registration,
                withPerson = expectedPerson
            )
        }.returns(expectedParticipant)
        every {
            crispyFishParticipantMapper.toCoreSignage(
                context = context,
                crispyFish = registration
            )
        } returns expectedParticipant.signage
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
        val participantResultMapper = ParticipantResultMapper(crispyFishParticipantMapper)
        val crispyFishMetadata: Event.CrispyFishMetadata = mockk {
            every { peopleMap } returns usePeopleMap
        }

        val actual = participantResultMapper.toCore(
            eventCrispyFishMetadata = crispyFishMetadata,
            context = context,
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