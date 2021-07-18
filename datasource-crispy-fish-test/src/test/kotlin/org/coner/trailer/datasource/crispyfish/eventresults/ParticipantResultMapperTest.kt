package org.coner.trailer.datasource.crispyfish.eventresults

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.coner.trailer.*
import org.coner.trailer.datasource.crispyfish.*
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
    @MockK lateinit var crispyFishClassingMapper: CrispyFishClassingMapper
    @MockK lateinit var crispyFishRunMapper: CrispyFishRunMapper

    @BeforeEach
    fun before() {
        mapper = ParticipantResultMapper(
            resultRunMapper = resultRunMapper,
            finalScoreFactory = finalScoreFactory,
            crispyFishParticipantMapper = crispyFishParticipantMapper,
            crispyFishClassingMapper = crispyFishClassingMapper,
            crispyFishRunMapper = crispyFishRunMapper
        )
    }

    @Test
    fun `It should map core ParticipantResult from EventCrispyFishMetadata, CrispyFishEventMappingContext, and cf Registration`() {
        val registration = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON
        val person = TestPeople.REBECCA_JACKSON
        val participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON
        val usePeopleMap = mapOf(
            Event.CrispyFishMetadata.PeopleMapKey(
                classing = requireNotNull(participant.classing),
                number = requireNotNull(participant.number),
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
                allClassesByAbbreviation = any(),
                fromRegistration = registration,
                withPerson = person
            )
        }.returns(participant)
        every {
            crispyFishClassingMapper.toCore(
                allClassesByAbbreviation = any(),
                cfRegistration = registration
            )
        } returns checkNotNull(participant.classing)
        val expectedScoredRuns = listOf(
            testResultRun(sequence = 1, participant = participant, time = Time("52.749"), score = Score("52.749")),
            testResultRun(sequence = 2, participant = participant, time = Time("53.175"), score = Score("53.175")),
            testResultRun(sequence = 3, participant = participant, time = Time("52.130"), score = Score("52.130")),
            testResultRun(sequence = 4, participant = participant, time = Time("52.117"), score = Score("52.117")),
            testResultRun(sequence = 5, participant = participant, time = Time("51.408"), score = Score("51.408"))
        )
        val participantCfRuns = listOf(10, 11, 12, 13, 14)
            .map { it to requireNotNull(allRuns[it].second) }
        participantCfRuns.forEachIndexed { index, pair ->
            every {
                crispyFishRunMapper.toCore(cfRunIndex = pair.first, cfRun = pair.second, participant = participant)
            } returns expectedScoredRuns[index].run
        }
        every {
            resultRunMapper.toCores(
                context = context,
                participantCfRuns = participantCfRuns,
                participant = participant
            )
        } returns(expectedScoredRuns)
        val crispyFishMetadata: Event.CrispyFishMetadata = mockk {
            every { peopleMap } returns usePeopleMap
        }
        val expectedScore: Score = mockk()
        every { finalScoreFactory.score(expectedScoredRuns) } returns expectedScore
        every { finalScoreFactory.bestRun(expectedScoredRuns) } returns expectedScoredRuns[4]

        val actual = mapper.toCore(
            eventCrispyFishMetadata = crispyFishMetadata,
            context = context,
            allClassesByAbbreviation = TestClasses.Lscc2019.allByAbbreviation,
            cfRegistration = registration
        )

        assertThat(actual).isNotNull().all {
            score().isSameAs(expectedScore)
            hasParticipant(participant)
            allRuns().hasSize(5)
            hasScoredRuns(expectedScoredRuns)
            personalBestScoredRunIndex().isEqualTo(4)
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
                runFns = listOf { participant ->
                    testRunWithScore(
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
                runFns = listOf { participant ->
                    testRunWithScore(
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
                runFns = listOf { participant ->
                    testRunWithScore(
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