package tech.coner.trailer.io.service

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isSameInstanceAs
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import tech.coner.trailer.datasource.snoozle.EventPointsCalculatorResource
import tech.coner.trailer.datasource.snoozle.entity.EventPointsCalculatorEntity
import tech.coner.trailer.io.constraint.EventPointsCalculatorPersistConstraints
import tech.coner.trailer.io.mapper.EventPointsCalculatorMapper
import tech.coner.trailer.seasonpoints.TestEventPointsCalculators
import java.util.stream.Stream

class EventPointsCalculatorServiceTest {

    lateinit var service: EventPointsCalculatorService

    @MockK
    lateinit var resource: EventPointsCalculatorResource
    @MockK
    lateinit var mapper: EventPointsCalculatorMapper
    @MockK
    lateinit var persistConstraints: EventPointsCalculatorPersistConstraints
    @MockK
    lateinit var snoozleCalculator: EventPointsCalculatorEntity

    @BeforeEach
    fun before() {
        MockKAnnotations.init(this)
        service = EventPointsCalculatorService(
                resource = resource,
                mapper = mapper,
                persistConstraints = persistConstraints
        )
    }

    @Test
    fun `It should create calculator`() {
        val calculator = TestEventPointsCalculators.lsccGroupedCalculator
        every { persistConstraints.assess(calculator) } answers { Unit }
        every { mapper.toSnoozle(calculator) } returns snoozleCalculator
        every { resource.create(snoozleCalculator) } answers { Unit }

        service.create(calculator)

        verifySequence {
            mapper.toSnoozle(calculator)
            resource.create(snoozleCalculator)
        }
    }

    @Test
    fun `It should find calculator by id`() {
        val calculator = TestEventPointsCalculators.lsccGroupedCalculator
        val slot = slot<EventPointsCalculatorEntity.Key>()
        every { resource.read(capture(slot)) } returns snoozleCalculator
        every { mapper.fromSnoozle(snoozleCalculator) } returns calculator

        val actual = service.findById(calculator.id)

        assertThat(actual).isSameInstanceAs(calculator)
        assertThat(slot.captured.id).isSameInstanceAs(calculator.id)
        verifySequence {
            resource.read(any())
            mapper.fromSnoozle(any())
        }
    }

    @Test
    fun `It should find calculator by name`() {
        val lsccGrouped = TestEventPointsCalculators.lsccGroupedCalculator
        val lsccOverall = TestEventPointsCalculators.lsccOverallCalculator
        every { resource.stream() } returns Stream.of(
                mockk { every { name } returns lsccGrouped.name },
                mockk { every { name } returns lsccOverall.name }
        )
        every { mapper.fromSnoozle(match { it.name == lsccGrouped.name }) } returns lsccGrouped
        every { mapper.fromSnoozle(match { it.name == lsccOverall.name }) } returns lsccOverall

        val actual = service.findByName(lsccOverall.name)

        assertThat(actual).isSameInstanceAs(lsccOverall)
    }

    @Test
    fun `It should list calculators`() {
        every { resource.stream() } returns Stream.of(mockk(), mockk(), mockk())
        every { mapper.fromSnoozle(any()) } returns mockk()

        val actual = service.list()

        assertThat(actual).hasSize(3)
        verify(exactly = 3) { mapper.fromSnoozle(any()) }
    }

    @Test
    fun `It should update calculators`() {
        val calculator = TestEventPointsCalculators.lsccGroupedCalculator
        every { persistConstraints.assess(calculator) } answers { Unit }
        every { mapper.toSnoozle(calculator) } returns mockk()
        every { resource.update(any()) } returns Unit

        service.update(calculator)

        verifySequence {
            mapper.toSnoozle(calculator)
            resource.update(any())
        }
    }

    @Test
    fun `It should delete calculators`() {
        val calculator = TestEventPointsCalculators.lsccGroupedCalculator
        val snoozleCalculator: EventPointsCalculatorEntity = mockk()
        every { mapper.toSnoozle(calculator) } returns snoozleCalculator
        every { resource.delete(snoozleCalculator) } returns Unit

        assertDoesNotThrow {
            service.delete(calculator)
        }

        verifySequence {
            mapper.toSnoozle(calculator)
            resource.delete(snoozleCalculator)
        }
    }

}