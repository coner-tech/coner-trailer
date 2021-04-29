package org.coner.trailer

import assertk.assertThat
import assertk.assertions.messageContains
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class EventTest {

    @Suppress("UnusedDataClassCopyResult")
    @Test
    fun `It should not instantiate with runCount from crispy fish when it lacks crispy fish metadata`() {
        val valid = TestEvents.Lscc2019.points1

        val exception = assertThrows<IllegalStateException> {
            valid.copy(crispyFish = null, runCount = Event.RunCount.FromCrispyFish)
        }

        assertThat(exception).messageContains("crispy fish metadata")
    }
}