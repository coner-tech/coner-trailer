package org.coner.trailer.datasource.crispyfish.eventresults

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verifySequence
import org.coner.crispyfish.model.RegistrationRun
import org.coner.trailer.TestParticipants
import org.coner.trailer.datasource.crispyfish.eventsresults.ResultRunMapper
import org.coner.trailer.datasource.crispyfish.eventsresults.ScoreMapper
import org.coner.trailer.eventresults.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ResultRunMapperTest {
    
    lateinit var mapper: ResultRunMapper

    @MockK lateinit var scoreMapper: ScoreMapper
    
    @BeforeEach
    fun before() {
        mapper = ResultRunMapper(
            scoreMapper = scoreMapper
        )
    }

    @Test
    fun `It should map a clean run`() {
        val participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON
        val run = RegistrationRun(
                time = "123.456",
                penalty = null
        )
        val expectedScore: Score = mockk()
        every {
            scoreMapper.toScore(cfRegistrationRun = run, participant = participant)
        } returns expectedScore

        val actual = mapper.toCore(
            cfRegistrationRun = run, // Run #2
            cfRegistrationRunIndex = 1, // Run #2
            cfRegistrationBestRun = 3, // Run #3,
            participant = participant
        )

        assertThat(actual).isNotNull().all {
            score().isSameAs(expectedScore)
            hasTime(run.time)
            isClean()
            personalBest().isFalse()
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