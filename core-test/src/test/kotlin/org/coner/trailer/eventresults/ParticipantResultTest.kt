package org.coner.trailer.eventresults

import assertk.all
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import assertk.assertions.isGreaterThan
import assertk.assertions.isLessThan
import org.coner.trailer.Run
import org.coner.trailer.TestParticipants
import org.coner.trailer.TestPolicies
import org.coner.trailer.Time
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ParticipantResultTest {

    @ParameterizedTest
    @ValueSource(ints = [0, -1, Int.MIN_VALUE])
    fun `Its constructor should throw when position is invalid`(param: Int) {
        assertThrows<IllegalArgumentException> {
            ParticipantResult(
                position = param,
                score = Score("0.000"),
                participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF,
                diffFirst = null,
                diffPrevious = null,
                scoredRuns = emptyList(),
                personalBestScoredRunIndex = null
            )
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 2, 3, 4, Int.MAX_VALUE])
    fun `Its constructor should not throw when position is valid`(param: Int) {
        assertDoesNotThrow {
            ParticipantResult(
                position = param,
                score = Score("0.000"),
                participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF,
                diffFirst = null,
                diffPrevious = null,
                scoredRuns = emptyList(),
                personalBestScoredRunIndex = null
            )
        }
    }

    @Nested
    inner class ScoredRunsComparatorTests {

        private val policy = TestPolicies.lsccV1
        private val subject = ParticipantResult.ScoredRunsComparator(
            runCount = 2
        )

        @Test
        fun `It should build scores for sort for result with all runs taken`() {
            val participantResult = build(
                scoredRuns = listOf(
                    Score("123.654"),
                    Score("123.456")
                ),
                personalBestScoredRunIndex = 1
            )

            val actual = subject.scoresForSort(participantResult)

            assertThat(actual).isEqualTo(listOf(
                Score("123.456"),
                Score("123.654")
            ))
        }

        @Test
        fun `It should build scores for sort for result with no runs taken`() {
            val participantResult = build(
                scoredRuns = emptyList(),
                personalBestScoredRunIndex = null
            )

            val actual = subject.scoresForSort(participantResult)

            assertThat(actual).isEqualTo(emptyList())
        }

        @Test
        fun `It should build scores for sort for result with partial runs taken`() {
            val participantResult = build(
                scoredRuns = listOf(
                    Score("123.654"),
                ),
                personalBestScoredRunIndex = 0,
            )

            val actual = subject.scoresForSort(participantResult)

            assertThat(actual).isEqualTo(listOf(
                Score("123.654"),
            ))
        }

        @Test
        fun `It should build scores for sort up to runCount`() {
            val participantResult = build(
                scoredRuns = listOf(
                    Score("123.654"),
                    Score("123.564"),
                    Score("123.000")
                ),
                personalBestScoredRunIndex = 2
            )

            val actual = subject.scoresForSort(participantResult)

            assertThat(actual).isEqualTo(listOf(
                Score("123.564"),
                Score("123.654"),
            ))
        }

        private val faster = build(
            scoredRuns = listOf(
                Score("34.765"),
                Score("34.567")
            ),
            personalBestScoredRunIndex = 1
        )

        private val slower = build(
            scoredRuns = listOf(
                Score("34.765"),
                Score("34.657")
            ),
            personalBestScoredRunIndex = 1
        )

        @Test
        fun `It should compare faster vs slower as lower`() {
            val actual = subject.compare(faster, slower)

            assertThat(actual).isLessThan(0)
        }

        @Test
        fun `It should compare slower vs faster as higher`() {
            val actual = subject.compare(slower, faster)

            assertThat(actual).isGreaterThan(0)
        }

        private val runCountSized = faster
        private val lessThanRunCountSized = build(
            scoredRuns = listOf(faster.scoredRuns.minOf { it.score }),
            personalBestScoredRunIndex = 1
        )

        @Test
        fun `It should compare runCount-sized scoredRuns vs less-than-runCount-sized scoredRuns as lower`() {
            val actual = subject.compare(runCountSized, lessThanRunCountSized)

            assertThat(actual).isLessThan(0)
        }

        @Test
        fun `It should compare less-than-runCount-sized scoredRuns vs runCount-sized runs taken as higher`() {
            val actual = subject.compare(lessThanRunCountSized, runCountSized)

            assertThat(actual).isGreaterThan(0)
        }

        @Test
        fun `It should compare identical scoredRuns as equal`() {
            val actual = subject.compare(faster, faster)

            assertThat(actual).isEqualTo(0)
        }

        private fun build(scoredRuns: List<Score>, personalBestScoredRunIndex: Int?) = ParticipantResult(
            position = Int.MAX_VALUE,
            score = Score("999.999"),
            participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON,
            diffFirst = null,
            diffPrevious = null,
            scoredRuns = scoredRuns.map { testResultRun(
                score = it,
                sequence = 0,
                participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON,
                time = Time(it.value)
            ) },
            personalBestScoredRunIndex = personalBestScoredRunIndex
        )
    }

}
