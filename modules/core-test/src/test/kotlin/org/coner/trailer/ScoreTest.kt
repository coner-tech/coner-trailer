package org.coner.trailer

import assertk.assertThat
import org.coner.trailer.eventresults.Score
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class ScoreTest {

    @Test
    fun `It should construct with valid time string`() {
        val actual = Score("123.456")

        assertThat(actual).hasValue(BigDecimal.valueOf(123456, 3))
    }
}