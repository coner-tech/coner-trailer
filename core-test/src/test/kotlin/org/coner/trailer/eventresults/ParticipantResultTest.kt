package org.coner.trailer.eventresults

import org.coner.trailer.TestParticipants
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ParticipantResultTest {

    @ParameterizedTest
    @ValueSource(ints = [0, -1, Int.MIN_VALUE])
    fun `Its constructor should throw when position is invalid`(param: Int) {
        assertThrows<IllegalArgumentException> {
            ParticipantResult(
                position = param,
                score = Score("0.000"),
                participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF,
                diffFirst = null,
                diffPrevious = null,
                scoredRuns = emptyList()
            )
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 2, 3, 4, Int.MAX_VALUE])
    fun `Its constructor should not throw when position is valid`(param: Int) {
        assertDoesNotThrow {
            ParticipantResult(
                position = param,
                score = Score("0.000"),
                participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF,
                diffFirst = null,
                diffPrevious = null,
                scoredRuns = emptyList()
            )
        }
    }

    @Test
    fun `It should build scores for sort for result with all runs taken`() {
        TODO()
    }

    @Test
    fun `It should build scores for sort for result with no runs taken`() {
        TODO()
    }

    @Test
    fun `It should build scores for sort for result with partial runs taken`() {
        TODO()
    }
}