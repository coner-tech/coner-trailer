package org.coner.trailer

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
}