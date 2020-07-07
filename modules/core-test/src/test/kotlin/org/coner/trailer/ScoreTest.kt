package org.coner.trailer

import assertk.assertThat
import org.junit.jupiter.api.Test

class ScoreTest {

    @Test
    fun `It should construct with valid time string`() {
        val actual = Score("123.456")

        assertThat(actual).hasValue(123456)
    }
}