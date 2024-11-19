package tech.coner.trailer.eventresults

import assertk.assertThat
import java.math.BigDecimal
import org.junit.jupiter.api.Test
import tech.coner.trailer.hasValue

class ScoreTest {

    @Test
    fun `It should construct with valid time string`() {
        val actual = Score("123.456")

        assertThat(actual).hasValue(BigDecimal.valueOf(123456, 3))
    }
}