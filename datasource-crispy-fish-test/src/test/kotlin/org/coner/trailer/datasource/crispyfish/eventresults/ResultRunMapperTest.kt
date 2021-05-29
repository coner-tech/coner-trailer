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
        val expectedRun: org.coner.trailer.Run = mockk()
        val expectedScore: Score = mockk()
        every {
            cfRunMapper.toCore(
                cfRun = cfRun, // Run #2
                cfRunIndex = 1, // Run #2
                participant = participant
            )
        } returns expectedRun
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
            run().isSameAs(expectedRun)
        }
        verifySequence {
            cfRunMapper.toCore(
                cfRun = cfRun,
                cfRunIndex = 1,
                participant = participant
            )
            scoreMapper.toScore(
                cfRun = cfRun,
                participant = participant
            )
        }
    }

    @Test
    fun `When score maps to null, it should return null`() {
        val participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON
        val cfRun = testCfRun(
            timeScratchAsString = null // participant has not taken run #x yet -- no time
        )
        every { cfRunMapper.toCore(cfRun, 0, participant) } returns mockk()
        every { scoreMapper.toScore(cfRun, participant) } returns null

        val actual = mapper.toCore(
            cfRun = cfRun,
            cfRunIndex = 0, // Run #1
            participant = participant
        )

        assertThat(actual).isNull()
        verifySequence {
            cfRunMapper.toCore(cfRun, 0, participant)
            scoreMapper.toScore(cfRun, participant)
        }
    }
}

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
