package org.coner.trailer.datasource.crispyfish.eventresults

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import assertk.assertions.isSameAs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.coner.trailer.Event
import org.coner.trailer.TestParticipants
import org.coner.trailer.TestPeople
import org.coner.trailer.Time
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import org.coner.trailer.datasource.crispyfish.TestRegistrations
import org.coner.trailer.datasource.crispyfish.eventsresults.ParticipantResultMapper
import org.coner.trailer.datasource.crispyfish.eventsresults.ResultRunMapper
import org.coner.trailer.datasource.crispyfish.eventsresults.ScoreMapper
import org.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import org.coner.trailer.eventresults.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi

@ExperimentalPathApi
@ExtendWith(MockKExtension::class)
class ParticipantResultMapperTest {

    lateinit var mapper: ParticipantResultMapper

    @TempDir lateinit var fixtureRoot: Path

    @MockK lateinit var resultRunMapper: ResultRunMapper
    @MockK lateinit var finalScoreFactory: FinalScoreFactory
    @MockK lateinit var crispyFishParticipantMapper: CrispyFishParticipantMapper

    @BeforeEach
    fun before() {
        mapper = ParticipantResultMapper(
            resultRunMapper = resultRunMapper,
            finalScoreFactory = finalScoreFactory,
            crispyFishParticipantMapper = crispyFishParticipantMapper
        )
    }

    @Test
    fun `It should map (core) ParticipantResult from (CF) Registration, arbitrary RegistrationResult, and Map of MemberId to People`() {
        val registration = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON
        val result = requireNotNull(registration.classResult)
        val expectedPerson = TestPeople.REBECCA_JACKSON
        val expectedParticipant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON
        val usePeopleMap = mapOf(
            Event.CrispyFishMetadata.PeopleMapKey(
                grouping = requireNotNull(expectedParticipant.signage?.grouping),
                number = requireNotNull(expectedParticipant.signage?.number),
                firstName = requireNotNull(expectedParticipant.firstName),
                lastName = requireNotNull(expectedPerson.lastName)
            ) to expectedPerson
        )
        val seasonFixture = SeasonFixture.Lscc2019Simplified(fixtureRoot)
        val context = CrispyFishEventMappingContext(
            allClassDefinitions = seasonFixture.classDefinitions,
            allRegistrations = seasonFixture.event1.registrations(),
            runCount = seasonFixture.event1.runCount
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
        } returns checkNotNull(expectedParticipant.signage)
        val expectedScoredRuns = listOf(
            ResultRun(time = Time("52.749"), score = Score("52.749")),
            ResultRun(time = Time("53.175"), score = Score("53.175")),
            ResultRun(time = Time("52.130"), score = Score("52.130")),
            ResultRun(time = Time("52.117"), score = Score("52.117")),
            ResultRun(time = Time("51.408"), score = Score("51.408"), personalBest = true)
        )
        every {
            resultRunMapper.toCore(
                cfRegistrationRuns = registration.runs,
                cfRegistrationBestRun = registration.bestRun,
                participant = expectedParticipant
            )
        }.returns(expectedScoredRuns)
        val crispyFishMetadata: Event.CrispyFishMetadata = mockk {
            every { peopleMap } returns usePeopleMap
        }
        val expectedScore: Score = mockk()
        every { finalScoreFactory.factory(expectedScoredRuns) } returns expectedScore

        val actual = mapper.toCore(
            eventCrispyFishMetadata = crispyFishMetadata,
            context = context,
            cfRegistration = registration
        )

        assertThat(actual).isNotNull().all {
            score().isSameAs(expectedScore)
            hasParticipant(expectedParticipant)
            hasScoredRuns(expectedScoredRuns)
            hasPosition(Int.MAX_VALUE) // not calculated here
            diffFirst().isNull() // not calculated here
            diffPrevious().isNull() // not calculated here
        }
    }

    @Test
    fun `It should calculate ranking-related properties of ParticipantResult`() {
        val sortedResults = listOf(
            ParticipantResult(
                score = Score("34.567"),
                participant = TestParticipants.Lscc2019Points1Simplified.REBECCA_JACKSON,
                scoredRuns = listOf(ResultRun(
                    time = Time("34.567"), score = Score("34.567"), personalBest = true
                )),
                position = Int.MAX_VALUE,
                diffFirst = null,
                diffPrevious = null
            ),
            ParticipantResult(
                score = Score("45.678"),
                participant = TestParticipants.Lscc2019Points1Simplified.JIMMY_MCKENZIE,
                scoredRuns = listOf(ResultRun(
                    time = Time("45.678"), score = Score("45.678"), personalBest = true
                )),
                position = Int.MAX_VALUE,
                diffFirst = null,
                diffPrevious = null
            ),
            ParticipantResult(
                score = Score("56.789"),
                participant = TestParticipants.Lscc2019Points1Simplified.ANASTASIA_RIGLER,
                scoredRuns = listOf(ResultRun(
                    time = Time("56.789"), score = Score("56.789"), personalBest = true
                )),
                position = Int.MAX_VALUE,
                diffFirst = null,
                diffPrevious = null
            )
        )

        val actual = sortedResults.mapIndexed { index, result ->
            mapper.toCoreRanked(sortedResults, index, result)
        }

        val expected = listOf(
            sortedResults[0].copy(
                position = 1,
                diffFirst = null,
                diffPrevious = null
            ),
            sortedResults[1].copy(
                position = 2,
                diffFirst = Time("11.111"),
                diffPrevious = Time("11.111")
            ),
            sortedResults[2].copy(
                position = 3,
                diffFirst = Time("22.222"),
                diffPrevious = Time("11.111")
            )
        )
        assertThat(actual).isEqualTo(expected)
    }
}