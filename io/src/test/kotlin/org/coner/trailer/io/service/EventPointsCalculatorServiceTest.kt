package org.coner.trailer.io.service

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isSameAs
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.coner.trailer.datasource.snoozle.EventPointsCalculatorResource
import org.coner.trailer.datasource.snoozle.entity.EventPointsCalculatorEntity
import org.coner.trailer.io.constraint.EventPointsCalculatorPersistConstraints
import org.coner.trailer.io.mapper.EventPointsCalculatorMapper
import org.coner.trailer.seasonpoints.TestEventPointsCalculators
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
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
        val calculator = TestEventPointsCalculators.lsccGroupingCalculator
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
        val calculator = TestEventPointsCalculators.lsccGroupingCalculator
        val slot = slot<EventPointsCalculatorEntity.Key>()
        every { resource.read(capture(slot)) } returns snoozleCalculator
        every { mapper.fromSnoozle(snoozleCalculator) } returns calculator

        val actual = service.findById(calculator.id)

        assertThat(actual).isSameAs(calculator)
        assertThat(slot.captured.id).isSameAs(calculator.id)
        verifySequence {
            resource.read(any())
            mapper.fromSnoozle(any())
        }
    }

    @Test
    fun `It should find calculator by name`() {
        val lsccGrouping = TestEventPointsCalculators.lsccGroupingCalculator
        val lsccOverall = TestEventPointsCalculators.lsccOverallCalculator
        every { resource.stream() } returns Stream.of(
                mockk { every { name } returns lsccGrouping.name },
                mockk { every { name } returns lsccOverall.name }
        )
        every { mapper.fromSnoozle(match { it.name == lsccGrouping.name }) } returns lsccGrouping
        every { mapper.fromSnoozle(match { it.name == lsccOverall.name }) } returns lsccOverall

        val actual = service.findByName(lsccOverall.name)

        assertThat(actual).isSameAs(lsccOverall)
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
        val calculator = TestEventPointsCalculators.lsccGroupingCalculator
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
        val calculator = TestEventPointsCalculators.lsccGroupingCalculator
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