package tech.coner.trailer.io.service

import assertk.assertThat
import assertk.assertions.isSameAs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import tech.coner.trailer.datasource.snoozle.SeasonPointsCalculatorConfigurationResource
import tech.coner.trailer.datasource.snoozle.entity.SeasonPointsCalculatorConfigurationEntity
import tech.coner.trailer.io.constraint.SeasonPointsCalculatorConfigurationConstraints
import tech.coner.trailer.io.mapper.SeasonPointsCalculatorConfigurationMapper
import tech.coner.trailer.seasonpoints.SeasonPointsCalculatorConfiguration
import tech.coner.trailer.seasonpoints.TestSeasonPointsCalculatorConfigurations
import java.util.stream.Stream

@ExtendWith(MockKExtension::class)
class SeasonPointsCalculatorConfigurationServiceTest {

    lateinit var service: SeasonPointsCalculatorConfigurationService

    @MockK
    lateinit var resource: SeasonPointsCalculatorConfigurationResource
    @MockK
    lateinit var mapper: SeasonPointsCalculatorConfigurationMapper
    @MockK
    lateinit var constraints: SeasonPointsCalculatorConfigurationConstraints

    @BeforeEach
    fun before() {
        service = SeasonPointsCalculatorConfigurationService(
                resource = resource,
                mapper = mapper,
                constraints = constraints
        )
    }

    @Test
    fun `It should create season points calculator configuration`(
            @MockK spcc: SeasonPointsCalculatorConfiguration,
            @MockK spccEntity: SeasonPointsCalculatorConfigurationEntity
    ) {
        every { constraints.assess(spcc) } answers { Unit }
        every { mapper.toSnoozle(spcc) } returns spccEntity
        every { resource.create(spccEntity) } answers { Unit }

        service.create(spcc)

        verifySequence {
            constraints.assess(spcc)
            mapper.toSnoozle(spcc)
            resource.create(spccEntity)
        }
    }

    @Test
    fun `It should find by id`(
            @MockK findEntity: SeasonPointsCalculatorConfigurationEntity
    ) {
        val find = TestSeasonPointsCalculatorConfigurations.lscc2019
        every { resource.read(match { it.id == find.id }) } returns findEntity
        every { mapper.fromSnoozle(findEntity) } returns find

        val actual = service.findById(find.id)

        verifySequence {
            resource.read(match { it.id == find.id })
            mapper.fromSnoozle(findEntity)
        }
        assertThat(actual).isSameAs(find)
    }

    @Test
    fun `It should find by name`() {
        val lscc2019 = TestSeasonPointsCalculatorConfigurations.lscc2019
        val lscc2019Entity: SeasonPointsCalculatorConfigurationEntity = mockk {
            every { name } returns lscc2019.name
        }
        val lscc2019SimplifiedEntity: SeasonPointsCalculatorConfigurationEntity = mockk {
            every { name } returns TestSeasonPointsCalculatorConfigurations.lscc2019Simplified.name
        }
        val stream = Stream.of(lscc2019Entity, lscc2019SimplifiedEntity)
        every { resource.stream() } returns stream
        every { mapper.fromSnoozle(lscc2019Entity) } returns lscc2019

        val actual = service.findByName(lscc2019.name)

        verifySequence {
            resource.stream()
            lscc2019Entity.name
            mapper.fromSnoozle(lscc2019Entity)
        }
        verify(exactly = 0) {
            lscc2019SimplifiedEntity.name
        }
        assertThat(actual).isSameAs(lscc2019)
    }

    @Test
    fun `It should update season points calculator`(
            @MockK update: SeasonPointsCalculatorConfiguration,
            @MockK updateEntity: SeasonPointsCalculatorConfigurationEntity
    ) {
        every { constraints.assess(update) } answers { Unit }
        every { mapper.toSnoozle(update) } returns updateEntity
        every { resource.update(updateEntity) } answers { Unit }

        service.update(update)

        verifySequence {
            constraints.assess(update)
            mapper.toSnoozle(update)
            resource.update(updateEntity)
        }
    }
}