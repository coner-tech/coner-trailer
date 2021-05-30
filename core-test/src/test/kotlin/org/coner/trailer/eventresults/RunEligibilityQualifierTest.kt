package org.coner.trailer.eventresults

import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import org.coner.trailer.Run
import org.coner.trailer.TestParticipants
import org.coner.trailer.Time
import org.junit.jupiter.api.Test

class RunEligibilityQualifierTest {

    private val subject = RunEligibilityQualifier()

    private val validParticipant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON
    private val validTime = Time("123.456")

    @Test
    fun `When run index is less than maxRunCount and is clean, it is eligible`() {
        val run = Run(
            sequence = 1,
            participant = validParticipant,
            time = validTime
        )

        val actual = subject.check(
            run = run,
            participantResultRunIndex = 3,
            maxRunCount = 4
        )

        assertThat(actual).isTrue()
    }

    @Test
    fun `When run index is less than maxRunCount and has cones, it is eligible`() {
        val run = Run(
            sequence = 1,
            participant = validParticipant,
            time = validTime,
            cones = 3
        )

        val actual = subject.check(
            run = run,
            participantResultRunIndex = 3,
            maxRunCount = 4
        )

        assertThat(actual).isTrue()
    }

    @Test
    fun `When run index is less than maxRunCount and has didNotFinish, it is eligible`() {
        val run = Run(
            sequence = 1,
            participant = validParticipant,
            time = validTime,
            didNotFinish = true
        )

        val actual = subject.check(
            run = run,
            participantResultRunIndex = 3,
            maxRunCount = 4
        )

        assertThat(actual).isTrue()
    }

    @Test
    fun `When run index is less than maxRunCount and has disqualified, it is eligible`() {
        val run = Run(
            sequence = 1,
            participant = validParticipant,
            time = validTime,
            disqualified = true
        )

        val actual = subject.check(
            run = run,
            participantResultRunIndex = 3,
            maxRunCount = 4
        )

        assertThat(actual).isFalse()
    }

    @Test
    fun `When run index is less than maxRunCount and has rerun, it is not eligible`() {
        val run = Run(
            sequence = 1,
            participant = validParticipant,
            time = validTime,
            rerun = true
        )

        val actual = subject.check(
            run = run,
            participantResultRunIndex = 3,
            maxRunCount = 4
        )

        assertThat(actual).isFalse()
    }

    @Test
    fun `When run index is equal to maxRunCount, it is not eligible`() {
        val run = Run(
            sequence = 1,
            participant = validParticipant,
            time = validTime
        )

        val actual = subject.check(
            run = run,
            participantResultRunIndex = 4,
            maxRunCount = 4
        )

        assertThat(actual).isFalse()
    }

    @Test
    fun `When run index is greater than maxRunCount, it is not eligible`() {
        val run = Run(
            sequence = 1,
            participant = validParticipant,
            time = validTime
        )

        val actual = subject.check(
            run = run,
            participantResultRunIndex = 5,
            maxRunCount = 4
        )

        assertThat(actual).isFalse()
    }

    @Test
    fun `When run lacks participant, it is not eligible`() {
        val run = Run(
            sequence = 1,
            participant = null,
            time = validTime
        )

        val actual = subject.check(
            run = run,
            participantResultRunIndex = 0,
            maxRunCount = 4
        )

        assertThat(actual).isFalse()
    }

    @Test
    fun `When run lacks time, it is not eligible`() {
        val run = Run(
            sequence = 1,
            participant = validParticipant,
            time = null
        )

        val actual = subject.check(
            run = run,
            participantResultRunIndex = 0,
            maxRunCount = 4
        )

        assertThat(actual).isFalse()
    }
}