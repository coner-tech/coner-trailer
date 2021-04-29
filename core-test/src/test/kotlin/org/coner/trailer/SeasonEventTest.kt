package org.coner.trailer

import assertk.assertThat
import assertk.assertions.isNegative
import assertk.assertions.isPositive
import assertk.assertions.isZero
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class SeasonEventTest {

    @Test
    fun `Its constructor should not throw when event number is null and points is false`() {
        val event = TestEvents.Lscc2019.points1

        assertDoesNotThrow {
            SeasonEvent(
                event = event,
                eventNumber = null, // important
                points = false, // important
                seasonPointsCalculatorConfiguration = null
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
                seasonPointsCalculatorConfiguration = null
            )
        }
    }

    @Test
    fun `It should compare SeasonEvents with eventNumbers`() {
        val one = TestSeasonEvents.Lscc2019Simplified.points1
        val two = TestSeasonEvents.Lscc2019Simplified.points2

        // one to two
        val actualOneToTwo = one.compareTo(two)

        assertThat(actualOneToTwo, "comparing one to two").isNegative()

        // two to one
        val actualTwoToOne = two.compareTo(one)

        assertThat(actualTwoToOne, "comparing two to one").isPositive()

        // one to one
        val actualOneToOne = one.compareTo(one)

        assertThat(actualOneToOne, "comparing one to one").isZero()
    }

    @Test
    fun `It should compare SeasonEvents without eventNumbers`() {
        val one = TestSeasonEvents.Lscc2019Simplified.points1
        val testAndTune = SeasonEvent(
            event = Event(
                name = "Test and Tune", // important: after "one", before "two"
                date = LocalDate.parse("2019-02-01"),
                lifecycle = Event.Lifecycle.FINAL,
                crispyFish = null,
                motorsportReg = null,
                policy = TestPolicies.lsccV1,
                runCount = Event.RunCount.Defined(4)
            ),
            points = false,
            eventNumber = null
        )
        val two = TestSeasonEvents.Lscc2019Simplified.points2

        // one to test and tune
        val actualOneToTestAndTune = one.compareTo(testAndTune)

        assertThat(actualOneToTestAndTune, "comparing one to test and tune").isNegative()

        // test and tune to one
        val actualTestAndTuneToOne = testAndTune.compareTo(one)

        assertThat(actualTestAndTuneToOne, "comparing test and tune to one").isPositive()

        // test and tune to self
        val actualSelfToSelf = testAndTune.compareTo(testAndTune)

        assertThat(actualSelfToSelf, "comparing test and tune to self").isZero()

        // test and tune to two
        val actualTestAndTuneToTwo = testAndTune.compareTo(two)

        assertThat(actualTestAndTuneToTwo, "comparing test and tune to two").isNegative()

        // two to test and tune
        val actualTwoToTestAndTune = two.compareTo(testAndTune)

        assertThat(actualTwoToTestAndTune, "comparing two to test and tune").isPositive()
    }
}