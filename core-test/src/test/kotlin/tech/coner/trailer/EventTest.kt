package tech.coner.trailer

import assertk.all
import assertk.assertThat
import assertk.assertions.messageContains
import io.mockk.mockk
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.platform.commons.util.Preconditions

class EventTest {

    @Nested
    inner class ConstructorTests {

        private val policyWithAuthoritativeRunSource = TestPolicies.lsccV1
            .also {
                Preconditions.condition(it.authoritativeRunSource == Policy.RunSource.CrispyFish) {
                    "Expected policy with authoritative run source: crispy fish"
                }
            }

        @Test
        fun `When its policy's authoritative run source is crispy fish, if it lacks crispy fish metadata, it should throw`() {
            val exception = assertThrows<IllegalStateException> {
                TestEvents.Lscc2019.points1.copy(
                    policy = policyWithAuthoritativeRunSource,
                    crispyFish = null
                )
            }

            assertThat(exception).all {
                messageContains("policy")
                messageContains("authoritative run source")
                messageContains("crispy fish metadata")
            }


        }

        @Test
        fun `When its policy's authoritative run source is crispy fish, if it has crispy fish metadata, it should instantiate`() {
            assertDoesNotThrow {
                TestEvents.Lscc2019.points1.copy(
                    policy = policyWithAuthoritativeRunSource,
                    crispyFish = mockk()
                )
            }
        }
    }
}