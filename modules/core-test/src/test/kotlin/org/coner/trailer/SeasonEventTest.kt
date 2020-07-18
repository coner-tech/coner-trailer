package org.coner.trailer

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class SeasonEventTest {

    @Test
    fun `Its constructor should not throw when event number is null and points is false`() {
        val event = TestEvents.Lscc2019.points1

        assertDoesNotThrow {
            SeasonEvent(
                    event = event,
                    eventNumber = null, // important
                    points = false, // important
                    seasonPointsCalculatorConfigurationModel = null
            )
        }
    }

    @Test
    fun `Its constructor should throw when event number is null and points is true`() {
        val event = TestEvents.Lscc2019.points1

        assertThrows<IllegalArgumentException> {
            SeasonEvent(
                    event = event,
                    eventNumber = null, // important
                    points = true, // important
                    seasonPointsCalculatorConfigurationModel = null
            )
        }
    }

    @Test
    fun `It should compare events correctly`() {
        TODO()
    }
}