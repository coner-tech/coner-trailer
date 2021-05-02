package org.coner.trailer

import assertk.assertThat
import assertk.assertions.messageContains
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.platform.commons.util.Preconditions

class EventTest {

    @Suppress("UnusedDataClassCopyResult")
    @Test
    fun `It should not instantiate with runCount from crispy fish when it lacks crispy fish metadata`() {
        val valid = TestEvents.Lscc2019.points1

        val exception = assertThrows<IllegalStateException> {
            valid.copy(crispyFish = null)
        }

        assertThat(exception).messageContains("crispy fish metadata")
    }

    @Test
    fun `When its policy defines authoritative run source as crispy fish, it should not instantiate without crispy fish metadata`() {
        val policy = TestPolicies.lsccV1
        Preconditions.condition(policy.authoritativeRunSource == Policy.RunSource.CrispyFish) {
            "Expected policy with authoritative run source: crispy fish"
        }

        val exception = assertThrows<IllegalStateException> {
            Event()
        }
    }

    @Test
    fun `When its policy defines authoritative run source as crispy fish, it should not instantiate without crispy fish metadata`() {
        TODO()
    }
}