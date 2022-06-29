package tech.coner.trailer

import assertk.assertThat
import org.junit.jupiter.api.Test
import tech.coner.trailer.eventresults.Score
import java.math.BigDecimal

class ScoreTest {

    @Test
    fun `It should construct with valid time string`() {
        val actual = Score("123.456")

        assertThat(actual).hasValue(BigDecimal.valueOf(123456, 3))
    }
}