package org.coner.trailer.io.service

import assertk.all
import assertk.assertAll
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isFalse
import assertk.assertions.isSameAs
import assertk.assertions.isTrue
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.coner.trailer.TestParticipantEventResultPointsCalculators
import org.coner.trailer.datasource.snoozle.ParticipantEventResultPointsCalculatorResource
import org.coner.trailer.datasource.snoozle.entity.ParticipantEventResultPointsCalculatorEntity
import org.coner.trailer.io.mapper.ParticipantEventResultPointsCalculatorMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.util.*
import java.util.stream.Stream

class ParticipantEventResultPointsCalculatorServiceTest {

    lateinit var service: ParticipantEventResultPointsCalculatorService

    @MockK
    lateinit var resource: ParticipantEventResultPointsCalculatorResource
    @MockK
    lateinit var mapper: ParticipantEventResultPointsCalculatorMapper
    @MockK
    lateinit var snoozleCalculator: ParticipantEventResultPointsCalculatorEntity

    @BeforeEach
    fun before() {
        MockKAnnotations.init(this)
        service = ParticipantEventResultPointsCalculatorService(
                resource = resource,
                mapper = mapper
        )
    }

    @Test
    fun `It should create calculator`() {
        val calculator = TestParticipantEventResultPointsCalculators.lsccGroupingCalculator
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
        val calculator = TestParticipantEventResultPointsCalculators.lsccGroupingCalculator
        val slot = slot<ParticipantEventResultPointsCalculatorEntity.Key>()
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
        val lsccGrouping = TestParticipantEventResultPointsCalculators.lsccGroupingCalculator
        val lsccOverall = TestParticipantEventResultPointsCalculators.lsccOverallCalculator
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
        val calculator = TestParticipantEventResultPointsCalculators.lsccGroupingCalculator
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
        val calculator = TestParticipantEventResultPointsCalculators.lsccGroupingCalculator
        val snoozleCalculator: ParticipantEventResultPointsCalculatorEntity = mockk()
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

    @Test
    fun `It should find out if name is new`() {
        val lsccGrouping = TestParticipantEventResultPointsCalculators.lsccGroupingCalculator
        val lsccOverall = TestParticipantEventResultPointsCalculators.lsccOverallCalculator
        every { resource.stream() } answers { Stream.of(
                mockk { every { name } returns lsccGrouping.name },
                mockk { every { name } returns lsccOverall.name }
        ) }
        every { mapper.fromSnoozle(match { it.name == lsccGrouping.name }) } returns lsccGrouping
        every { mapper.fromSnoozle(match { it.name == lsccOverall.name }) } returns lsccOverall

        val actualNotNew = service.hasNewName(lsccGrouping.name)
        val actualNew = service.hasNewName(UUID.randomUUID().toString())

        assertAll {
            assertThat(actualNotNew, "not new case").isFalse()
            assertThat(actualNew, "new case").isTrue()
        }
    }
}