package tech.coner.trailer

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class RunTest {

    @Nested
    inner class CleanTests {

        @Test
        fun `Run without penalties should be clean`() {
            val run = cleanRun()

            assertThat(run).clean().isTrue()
        }

        @Test
        fun `Run with cones should not be clean`() {
            val run = penaltyRun(cones = 1)

            assertThat(run).clean().isFalse()
        }

        @Test
        fun `Run with DNF should not be clean`() {
            val run = penaltyRun(didNotFinish = true)

            assertThat(run).clean().isFalse()
        }

        @Test
        fun `Run with DSQ should not be clean`() {
            val run = penaltyRun(disqualified = true)

            assertThat(run).clean().isFalse()
        }

        @Test
        fun `Run with multiple penalties should not be clean`() {
            val run = penaltyRun(
                cones = 3,
                didNotFinish = true,
                disqualified = true,
            )

            assertThat(run).clean().isFalse()
        }
    }

    @Nested
    inner class EffectivePenaltyTests {

        @Test
        fun `When run is clean it should be null`() {
            val run = cleanRun()

            assertThat(run).effectivePenalty().isNull()
        }

        @Test
        fun `When run has one penalty it should be that penalty`() {
            val run = penaltyRun(cones = 1)

            assertThat(run).effectivePenalty()
                .isNotNull()
                .isInstanceOf(Run.Penalty.Cone::class)
        }

        @Test
        fun `When run has disqualified and other penalties it should be the first penalty`() {
            val run = penaltyRun(
                cones = 3,
                disqualified = true,
                didNotFinish = true
            )

            assertThat(run).effectivePenalty().isSameInstanceAs(Run.Penalty.Disqualified)
        }

        @Test
        fun `When run has DNF and cones it should be the DNF`() {
            val run = penaltyRun(
                cones = 2,
                didNotFinish = true
            )

            assertThat(run).effectivePenalty().isSameInstanceAs(Run.Penalty.DidNotFinish)
        }
    }

    @Nested
    inner class SupersededPenaltiesTest {

        @Test
        fun `When run is clean it should be null`() {
            val run = cleanRun()

            assertThat(run).supersededPenalties().isNull()
        }

        @Test
        fun `When run has only one penalty it should be null`() {
            val run = penaltyRun(cones = 1)

            assertThat(run).supersededPenalties().isNull()
        }

        @Test
        fun `When run has two penalty types it should be second penalty and not the effective penalty`() {
            val run = penaltyRun(
                cones = 2,
                didNotFinish = true
            )

            assertThat(run).supersededPenalties().isNotNull().all {
                hasSize(1)
                index(0).all {
                    isInstanceOf(Run.Penalty.Cone::class)
                    isNotEqualTo(run.effectivePenalty)
                }
            }
        }

        @Test
        fun `When run has three penalty types it should be second and third penalties and not the effective penalty`() {
            val run = penaltyRun(
                cones = 3,
                disqualified = true,
                didNotFinish = true
            )

            assertThat(run).supersededPenalties().isNotNull().all {
                hasSize(2)
                index(0).isSameInstanceAs(Run.Penalty.DidNotFinish)
                index(1).isInstanceOf(Run.Penalty.Cone::class)
            }
        }
    }

    @Nested
    inner class AllPenaltiesTests {

        @Test
        fun `When run is clean it should be null`() {
            val run = cleanRun()

            assertThat(run).allPenalties().isNull()
        }

        @Test
        fun `When run is DSQ it should only contain DSQ`() {
            val run = penaltyRun(disqualified = true)

            assertThat(run).allPenalties().isEqualTo(listOf(Run.Penalty.Disqualified))
        }

        @Test
        fun `When run is DNF it should only contain DNF`() {
            val run = penaltyRun(didNotFinish = true)

            assertThat(run).allPenalties().isEqualTo(listOf(Run.Penalty.DidNotFinish))
        }

        @Test
        fun `When run is coned it should only contain cones`() {
            val run = penaltyRun(cones = 1)

            assertThat(run).allPenalties().isEqualTo(listOf(Run.Penalty.Cone(1)))
        }

        @Test
        fun `When run is DSQd and coned it should be that order`() {
            val run = penaltyRun(
                cones = 2,
                disqualified = true
            )

            val expected = listOf(
                Run.Penalty.Disqualified,
                Run.Penalty.Cone(2)
            )
            assertThat(run).allPenalties().isEqualTo(expected)
        }

        @Test
        fun `When run is DSQd and DNFd it should be that order`() {
            val run = penaltyRun(
                disqualified = true,
                didNotFinish = true
            )

            val expected = listOf(
                Run.Penalty.Disqualified,
                Run.Penalty.DidNotFinish
            )
            assertThat(run).allPenalties().isEqualTo(expected)
        }

        @Test
        fun `When run is DNFd and coned it should be that order`() {
            val run = penaltyRun(
                cones = 2,
                didNotFinish = true
            )

            val expected = listOf(
                Run.Penalty.DidNotFinish,
                Run.Penalty.Cone(2)
            )
            assertThat(run).allPenalties().isEqualTo(expected)
        }
    }
}

private fun cleanRun() = penaltyRun(
    cones = 0,
    didNotFinish = false,
    disqualified = false
)

private fun penaltyRun(
    cones: Int = 0,
    didNotFinish: Boolean = false,
    disqualified: Boolean = false
) = Run(
    sequence = 1,
    participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON,
    cones = cones,
    didNotFinish = didNotFinish,
    disqualified = disqualified,
    rerun = false,
    time = null
)