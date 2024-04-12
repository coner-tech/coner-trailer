package tech.coner.trailer.io.constraint

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import tech.coner.trailer.TestEvents
import tech.coner.trailer.datasource.snoozle.EventResource
import tech.coner.trailer.datasource.snoozle.entity.EventEntity
import tech.coner.trailer.io.mapper.EventMapper
import tech.coner.trailer.toolkit.konstraints.ConstraintViolationException
import java.util.*
import java.util.stream.Stream

@ExtendWith(MockKExtension::class)
class PolicyDeleteConstraintsTest {

    lateinit var constraints: PolicyDeleteConstraints

    @MockK lateinit var eventResource: EventResource

    private lateinit var eventMapper: EventMapper

    @BeforeEach
    fun before() {
        constraints = PolicyDeleteConstraints(
            eventResource = eventResource
        )
    }

    @Test
    fun `It should constrain deleting policies referenced by events`() {
        val events = TestEvents.Lscc2019.points1.let { Stream.of(
            EventEntity(
                id = it.id,
                name = it.name,
                date = it.date,
                lifecycle = it.lifecycle.name,
                crispyFish = null,
                motorsportReg = null,
                policyId = it.policy.id // important for test
            )
        ) }
        every { eventResource.stream() } returns events

        assertThrows<ConstraintViolationException> {
            constraints.assess(TestEvents.Lscc2019.points1.policy)
        }
    }

    @Test
    fun `It should not constrain deleting policies not referenced by events`() {
        val events = TestEvents.Lscc2019.points1.let { Stream.of(
            EventEntity(
                id = it.id,
                name = it.name,
                date = it.date,
                lifecycle = it.lifecycle.name,
                crispyFish = null,
                motorsportReg = null,
                policyId = UUID.randomUUID() // important for test
            )
        ) }
        every { eventResource.stream() } returns events

        assertDoesNotThrow {
            constraints.assess(TestEvents.Lscc2019.points1.policy)
        }
    }
}