package org.coner.trailer.seasonpoints

import assertk.assertThat
import assertk.assertions.containsExactly
import io.mockk.every
import io.mockk.mockk
import org.coner.trailer.Time
import org.junit.jupiter.api.Test

class PersonStandingAccumulatorTest {

    @Test
    fun `It should break ties by average margin of victory descending`() {
        val accumulatorA = mockk<PersonStandingAccumulator> {
            every { marginsOfVictory }.returns(mutableListOf(
                    Time("1.000"),
                    Time("0.500"),
                    Time("0.250")
            ))
        }
        val accumulatorB = mockk<PersonStandingAccumulator> {
            every { marginsOfVictory }.returns(mutableListOf(
                    Time("3.333"),
                    Time("2.222"),
                    Time("1.111")
            ))
        }
        val accumulators = listOf(accumulatorA, accumulatorB)

        val actual = accumulators
                .sortedWith(
                        compareByDescending<PersonStandingAccumulator> { 0 } // make ties
                                .thenByAverageMarginOfVictoryDescending()
                )

        assertThat(actual).containsExactly(accumulatorB, accumulatorA)
    }
}