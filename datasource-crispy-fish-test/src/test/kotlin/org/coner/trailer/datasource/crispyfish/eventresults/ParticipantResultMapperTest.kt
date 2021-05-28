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
        val person = TestPeople.REBECCA_JACKSON
        val participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON
        val usePeopleMap = mapOf(
            Event.CrispyFishMetadata.PeopleMapKey(
                grouping = requireNotNull(participant.signage?.grouping),
                number = requireNotNull(participant.signage?.number),
                firstName = requireNotNull(participant.firstName),
                lastName = requireNotNull(person.lastName)
            ) to person
        )
        val seasonFixture = SeasonFixture.Lscc2019Simplified(fixtureRoot)
        val allRegistrations = seasonFixture.event1.registrations()
        val allRuns = seasonFixture.event1.runs(allRegistrations)
        val context = CrispyFishEventMappingContext(
            allClassDefinitions = seasonFixture.classDefinitions,
            allRegistrations = allRegistrations,
            allRuns = allRuns,
            runCount = seasonFixture.event1.runCount
        )
        every {
            crispyFishParticipantMapper.toCore(
                context = context,
                fromRegistration = registration,
                withPerson = person
            )
        }.returns(participant)
        every {
            crispyFishParticipantMapper.toCoreSignage(
                context = context,
                crispyFish = registration
            )
        } returns checkNotNull(participant.signage)
        val expectedScoredRuns = listOf(
            testResultRun(sequence = 1, participant = participant, time = Time("52.749"), score = Score("52.749")),
            testResultRun(sequence = 2, participant = participant, time = Time("53.175"), score = Score("53.175")),
            testResultRun(sequence = 3, participant = participant, time = Time("52.130"), score = Score("52.130")),
            testResultRun(sequence = 4, participant = participant, time = Time("52.117"), score = Score("52.117")),
            testResultRun(sequence = 5, participant = participant, time = Time("51.408"), score = Score("51.408"))
        )
        val participantCfRuns = listOf(10, 11, 12, 13, 14)
            .mapNotNull { allRuns[it].second }
        every {
            resultRunMapper.toCores(
                context = context,
                participantCfRuns = participantCfRuns,
                participant = participant
            )
        }.returns(expectedScoredRuns)
        val crispyFishMetadata: Event.CrispyFishMetadata = mockk {
            every { peopleMap } returns usePeopleMap
        }
        val expectedScore: Score = mockk()
        every { finalScoreFactory.score(expectedScoredRuns) } returns expectedScore

        val actual = mapper.toCore(
            eventCrispyFishMetadata = crispyFishMetadata,
            context = context,
            cfRegistration = registration
        )

        assertThat(actual).isNotNull().all {
            score().isSameAs(expectedScore)
            hasParticipant(participant)
            hasScoredRuns(expectedScoredRuns)
            hasPosition(Int.MAX_VALUE) // not calculated here
            diffFirst().isNull() // not calculated here
            diffPrevious().isNull() // not calculated here
        }
    }

    @Test
    fun `It should calculate ranking-related properties of ParticipantResult`() {
        val sortedResults = listOf(
            testParticipantResult(
                score = Score("34.567"),
                participant = TestParticipants.Lscc2019Points1Simplified.REBECCA_JACKSON,
                scoredRunsFns = listOf { participant ->
                    testResultRun(
                        sequence = 1,
                        participant = participant,
                        time = Time("34.567"),
                        score = Score("34.567")
                    )
                },
                position = Int.MAX_VALUE,
                diffFirst = null,
                diffPrevious = null,
                personalBestScoredRunIndex = 0
            ),
            testParticipantResult(
                score = Score("45.678"),
                participant = TestParticipants.Lscc2019Points1Simplified.JIMMY_MCKENZIE,
                scoredRunsFns = listOf { participant ->
                    testResultRun(
                        sequence = 2,
                        participant = participant,
                        time = Time("45.678"),
                        score = Score("45.678")
                    )
                },
                position = Int.MAX_VALUE,
                diffFirst = null,
                diffPrevious = null,
                personalBestScoredRunIndex = 0
            ),
            testParticipantResult(
                score = Score("56.789"),
                participant = TestParticipants.Lscc2019Points1Simplified.ANASTASIA_RIGLER,
                scoredRunsFns = listOf { participant ->
                    testResultRun(
                        sequence = 3,
                        participant = participant,
                        time = Time("56.789"),
                        score = Score("56.789"),
                    )
                },
                position = Int.MAX_VALUE,
                diffFirst = null,
                diffPrevious = null,
                personalBestScoredRunIndex = 0
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