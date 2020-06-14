package org.coner.trailer

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class TimeTest {

    @ParameterizedTest
    @ValueSource(strings = ["123.456", "0.000", "1.100"])
    fun `It should accept properly formatted strings`(param: String) {
        assertDoesNotThrow {
            Time(param)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["0", "0.0", "0.0000", "-1.000"])
    fun `It should not accept improperly formatted strings`(param: String) {
        assertThrows<IllegalArgumentException> {
            Time(param)
        }
    }

    @Test
    fun `It should average empty collection of times to null`() {
        val times = emptyList<Time>()

        val actual = times.average()

        assertThat(actual).isNull()
    }

    @Test
    fun `It should average collection of times and maintain scale`() {
        val times = listOf(
                Time("51.408"),
                Time("34.762"),
                Time("80.476"),
                Time("36.013"),
                Time("35.655"),
                Time("43.992"),
                Time("34.691"),
                Time("52.557")
        )

        val actual = times.average()

        assertThat(actual).isEqualTo(Time("46.194"))
    }
}