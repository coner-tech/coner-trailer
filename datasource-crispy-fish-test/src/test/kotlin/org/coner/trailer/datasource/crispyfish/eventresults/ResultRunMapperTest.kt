package org.coner.trailer.datasource.crispyfish.eventresults

import assertk.all
import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isNotNull
import assertk.assertions.isSameAs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
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
        TODO()
    }

    @Test
    fun `It should map a coned run`() {
        TODO()
    }

    @Test
    fun `It should map a DNF`() {
        TODO()
    }

    @Test
    fun `It should map a DSQ`() {
        TODO()
    }

    @Test
    fun `When run index matches best run index, it should map as personal best`() {
        TODO()
    }

}