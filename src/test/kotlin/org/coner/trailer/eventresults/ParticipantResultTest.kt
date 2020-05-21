package org.coner.trailer.eventresults

import org.coner.trailer.TestParticipants
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ParticipantResultTest {

    @ParameterizedTest
    @ValueSource(ints = [0, -1, Int.MIN_VALUE])
    fun `Its constructor should throw when position is invalid`(param: Int) {
        assertThrows<IllegalArgumentException> {
            ParticipantResult.Minimal(
                    position = param,
                    participant = TestParticipants.Thscc2019Points1.BRANDY_HUFF
            )
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 2, 3, 4, Int.MAX_VALUE])
    fun `Its constructor should not throw when position is valid`(param: Int) {
        assertDoesNotThrow {
            ParticipantResult.Minimal(
                    position = param,
                    participant = TestParticipants.Thscc2019Points1.BRANDY_HUFF
            )
        }
    }
}