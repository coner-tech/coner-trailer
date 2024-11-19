package tech.coner.trailer.domain.entity

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isGreaterThan
import assertk.assertions.isLessThan
import assertk.assertions.messageContains
import io.mockk.mockk
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.platform.commons.util.Preconditions
import tech.coner.trailer.Event
import tech.coner.trailer.Policy
import tech.coner.trailer.TestEvents
import tech.coner.trailer.TestPolicies

class EventTest {

    @Nested
    inner class ConstructorTests {

        private val policyWithAuthoritativeRunDataSource = TestPolicies.lsccV1
            .also {
                Preconditions.condition(it.authoritativeRunDataSource == Policy.DataSource.CrispyFish) {
                    "Expected policy with authoritative run source: crispy fish"
                }
            }

        @Test
        fun `When its policy's authoritative run source is crispy fish, if it lacks crispy fish metadata, it should throw`() {
            val exception = assertThrows<IllegalStateException> {
                TestEvents.Lscc2019.points1.copy(
                    policy = policyWithAuthoritativeRunDataSource,
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
                    policy = policyWithAuthoritativeRunDataSource,
                    crispyFish = mockk()
                )
            }
        }
    }

    @Nested
    inner class LifecycleTests {

        @Test
        fun `It should compare create`() {
            assertThat(Event.Lifecycle.CREATE).all {
                isEqualTo(Event.Lifecycle.CREATE)
                isLessThan(Event.Lifecycle.PRE)
                isLessThan(Event.Lifecycle.ACTIVE)
                isLessThan(Event.Lifecycle.POST)
                isLessThan(Event.Lifecycle.FINAL)
            }
        }

        @Test
        fun `It should compare pre`() {
            assertThat(Event.Lifecycle.PRE).all {
                isGreaterThan(Event.Lifecycle.CREATE)
                isEqualTo(Event.Lifecycle.PRE)
                isLessThan(Event.Lifecycle.ACTIVE)
                isLessThan(Event.Lifecycle.POST)
                isLessThan(Event.Lifecycle.FINAL)
            }
        }

        @Test
        fun `It should compare active`() {
            assertThat(Event.Lifecycle.ACTIVE).all {
                isGreaterThan(Event.Lifecycle.CREATE)
                isGreaterThan(Event.Lifecycle.PRE)
                isEqualTo(Event.Lifecycle.ACTIVE)
                isLessThan(Event.Lifecycle.POST)
                isLessThan(Event.Lifecycle.FINAL)
            }
        }

        @Test
        fun `It should compare post`() {
            assertThat(Event.Lifecycle.POST).all {
                isGreaterThan(Event.Lifecycle.CREATE)
                isGreaterThan(Event.Lifecycle.PRE)
                isGreaterThan(Event.Lifecycle.ACTIVE)
                isEqualTo(Event.Lifecycle.POST)
                isLessThan(Event.Lifecycle.FINAL)
            }
        }

        @Test
        fun `It should compare final`() {
            assertThat(Event.Lifecycle.FINAL).all {
                isGreaterThan(Event.Lifecycle.CREATE)
                isGreaterThan(Event.Lifecycle.PRE)
                isGreaterThan(Event.Lifecycle.ACTIVE)
                isGreaterThan(Event.Lifecycle.POST)
                isEqualTo(Event.Lifecycle.FINAL)
            }
        }
    }
}