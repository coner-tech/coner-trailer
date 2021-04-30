package org.coner.trailer.eventresults

import assertk.all
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import assertk.assertions.isGreaterThan
import assertk.assertions.isLessThan
import org.coner.trailer.TestParticipants
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
                scoredRuns = emptyList()
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
                scoredRuns = emptyList()
            )
        }
    }

    @Nested
    inner class ScoredRunsComparatorTests {

        private val padScore = Score("999.999")

        private val subject = ParticipantResult.ScoredRunsComparator(
            runCount = 2,
            padScore = padScore
        )

        @Test
        fun `It should build scores for sort for result with all runs taken`() {
            val participantResult = build(listOf(
                Score("123.654"),
                Score("123.456")
            ))

            val actual = subject.scoresForSort(participantResult)

            assertThat(actual).isEqualTo(listOf(
                Score("123.456"),
                Score("123.654")
            ))
        }

        @Test
        fun `It should build scores for sort for result with no runs taken`() {
            val participantResult = build(emptyList())

            val actual = subject.scoresForSort(participantResult)

            assertThat(actual).isEqualTo(listOf(
                padScore,
                padScore
            ))
        }

        @Test
        fun `It should build scores for sort for result with partial runs taken`() {
            val participantResult = build(listOf(
                Score("123.654"),
                padScore
            ))

            val actual = subject.scoresForSort(participantResult)

            assertThat(actual).isEqualTo(listOf(
                Score("123.654"),
                padScore
            ))
        }

        @Test
        fun `It should build scores for sort up to runCount`() {
            val participantResult = build(listOf(
                Score("123.654"),
                Score("123.564"),
                Score("123.000")
            ))

            val actual = subject.scoresForSort(participantResult)

            assertThat(actual).isEqualTo(listOf(
                Score("123.564"),
                Score("123.654"),
            ))
        }

        private val faster = build(listOf(
            Score("34.765"),
            Score("34.567")
        ))

        private val slower = build(listOf(
            Score("34.765"),
            Score("34.657")
        ))

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
        private val lessThanRunCountSized = build(listOf(faster.scoredRuns.minOf { it.score }))

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

        private fun build(scoredRuns: List<Score>) = ParticipantResult(
            position = Int.MAX_VALUE,
            score = Score("999.999"),
            participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON,
            diffFirst = null,
            diffPrevious = null,
            scoredRuns = scoredRuns.map { ResultRun(
                score = it,
                time = null
            ) }
        )
    }

}
