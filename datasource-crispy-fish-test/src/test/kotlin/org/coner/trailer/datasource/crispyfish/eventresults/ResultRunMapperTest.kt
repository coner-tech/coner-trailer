package org.coner.trailer.datasource.crispyfish.eventresults

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verifySequence
import tech.coner.crispyfish.model.RegistrationRun
import org.coner.trailer.TestParticipants
import org.coner.trailer.datasource.crispyfish.CrispyFishRunMapper
import org.coner.trailer.datasource.crispyfish.eventsresults.ResultRunMapper
import org.coner.trailer.datasource.crispyfish.eventsresults.ScoreMapper
import org.coner.trailer.eventresults.*
import org.coner.trailer.hasTime
import org.coner.trailer.isClean
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import tech.coner.crispyfish.filetype.staging.StagingFileAssistant
import tech.coner.crispyfish.model.PenaltyType
import tech.coner.crispyfish.model.Run

@ExtendWith(MockKExtension::class)
class ResultRunMapperTest {
    
    lateinit var mapper: ResultRunMapper

    @MockK lateinit var cfRunMapper: CrispyFishRunMapper
    @MockK lateinit var scoreMapper: ScoreMapper

    @BeforeEach
    fun before() {
        mapper = ResultRunMapper(
            cfRunMapper = cfRunMapper,
            scoreMapper = scoreMapper
        )
    }

    @Test
    fun `It should map a clean run`() {
        val participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON
        val cfRun = testCfRun(
            timeScratchAsString = "123.456"
        )
        val expectedScore: Score = mockk()
        every {
            cfRunMapper.toCore(
                cfRun = cfRun, // Run #2
                cfRunIndex = 1, // Run #2
                participant = participant
            )
        }
        every {
            scoreMapper.toScore(cfRun = cfRun, participant = participant)
        } returns expectedScore

        val actual = mapper.toCore(
            cfRun = cfRun, // Run #2
            cfRunIndex = 1, // Run #2
            participant = participant
        )

        assertThat(actual).isNotNull().all {
            score().isSameAs(expectedScore)
            run().all {
                hasTime("123.456")
                isClean()
            }
        }
    }

    @Test
    fun `When score maps to null, it should return null`() {
        val participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON
        val cfRegistrationRun = RegistrationRun(
            time = null, // participant has not taken run #x yet -- no time
            penalty = null
        )
        val expectedScore = null
        every { scoreMapper.toScore(cfRegistrationRun, participant) } returns expectedScore

        val actual = mapper.toCore(
            cfRegistrationRun = cfRegistrationRun,
            cfRegistrationRunIndex = 0, // Run #1
            cfRegistrationBestRun = null, // no times yet, no best run
            participant = participant
        )

        assertThat(actual).isNull()
        verifySequence {
            scoreMapper.toScore(cfRegistrationRun, participant)
        }
    }

    @Test
    fun `It should map a coned run`() {
        val participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON
        val cfRegistrationRun = RegistrationRun(
            time = "123.456",
            penalty = RegistrationRun.Penalty.Cone(2)
        )
        val expectedScore: Score = mockk()
        every {
            scoreMapper.toScore(cfRegistrationRun = cfRegistrationRun, participant = participant)
        } returns expectedScore

        val actual = mapper.toCore(
            cfRegistrationRun = cfRegistrationRun, // Run #2
            cfRegistrationRunIndex = 1, // Run #2
            cfRegistrationBestRun = 3, // Run #3,
            participant = participant
        )

        assertThat(actual).isNotNull().all {
            score().isSameAs(expectedScore)
            hasTime(cfRegistrationRun.time)
            hasCones(2)
            didNotFinish().isFalse()
            disqualified().isFalse()
            personalBest().isFalse()
        }
        verifySequence {
            scoreMapper.toScore(cfRegistrationRun, participant)
        }
    }

    @Test
    fun `It should map a DNF`() {
        val participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON
        val cfRegistrationRun = RegistrationRun(
            time = "123.456",
            penalty = RegistrationRun.Penalty.DidNotFinish
        )
        val expectedScore: Score = mockk()
        every {
            scoreMapper.toScore(cfRegistrationRun = cfRegistrationRun, participant = participant)
        } returns expectedScore

        val actual = mapper.toCore(
            cfRegistrationRun = cfRegistrationRun, // Run #2
            cfRegistrationRunIndex = 1, // Run #2
            cfRegistrationBestRun = 3, // Run #3,
            participant = participant
        )

        assertThat(actual).isNotNull().all {
            score().isSameAs(expectedScore)
            hasTime(cfRegistrationRun.time)
            didNotFinish().isTrue()
            conesIsNullOrZero()
            disqualified().isFalse()
            personalBest().isFalse()
        }
        verifySequence {
            scoreMapper.toScore(cfRegistrationRun, participant)
        }
    }

    @Test
    fun `It should map a DSQ`() {
        val participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON
        val cfRegistrationRun = RegistrationRun(
            time = "123.456",
            penalty = RegistrationRun.Penalty.Disqualified
        )
        val expectedScore: Score = mockk()
        every {
            scoreMapper.toScore(cfRegistrationRun = cfRegistrationRun, participant = participant)
        } returns expectedScore

        val actual = mapper.toCore(
            cfRegistrationRun = cfRegistrationRun, // Run #2
            cfRegistrationRunIndex = 1, // Run #2
            cfRegistrationBestRun = 3, // Run #3,
            participant = participant
        )

        assertThat(actual).isNotNull().all {
            score().isSameAs(expectedScore)
            hasTime(cfRegistrationRun.time)
            disqualified().isTrue()
            conesIsNullOrZero()
            didNotFinish().isFalse()
            personalBest().isFalse()
        }
        verifySequence {
            scoreMapper.toScore(cfRegistrationRun, participant)
        }
    }

    @Test
    fun `When run index matches best run index, it should map as personal best`() {
        val participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON
        val cfRegistrationRun = RegistrationRun(
            time = "123.456",
            penalty = null
        )
        val expectedScore: Score = mockk()
        every {
            scoreMapper.toScore(cfRegistrationRun = cfRegistrationRun, participant = participant)
        } returns expectedScore

        val actual = mapper.toCore(
            cfRegistrationRun = cfRegistrationRun, // Run #4
            cfRegistrationRunIndex = 3, // Run #4
            cfRegistrationBestRun = 4, // Run #4,
            participant = participant
        )

        assertThat(actual).isNotNull().all {
            score().isSameAs(expectedScore)
            hasTime(cfRegistrationRun.time)
            conesIsNullOrZero()
            didNotFinish().isFalse()
            disqualified().isFalse()
            personalBest().isTrue()
        }
        verifySequence {
            scoreMapper.toScore(cfRegistrationRun, participant)
        }
    }

}

private val stagingAssistant = StagingFileAssistant()

private fun testCfRun(
    timeScratchAsString: String? = null,
    penaltyType: PenaltyType? = null,
    cones: Int? = null
) = Run(
    timeScratchAsString = timeScratchAsString,
    penaltyType = penaltyType,
    number = null, // unused
    rawTime = null, // unused
    paxTime = null, // unused
    cones = cones,
    timeScored = null, // unused
    timeScratchAsDuration = null // unused
)
