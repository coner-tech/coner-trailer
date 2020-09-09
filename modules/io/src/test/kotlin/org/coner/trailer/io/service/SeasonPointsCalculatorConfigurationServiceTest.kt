package org.coner.trailer.io.service

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import org.coner.trailer.datasource.snoozle.SeasonPointsCalculatorConfigurationResource
import org.coner.trailer.datasource.snoozle.entity.SeasonPointsCalculatorConfigurationEntity
import org.coner.trailer.io.constraint.SeasonPointsCalculatorConfigurationConstraints
import org.coner.trailer.io.mapper.SeasonPointsCalculatorConfigurationMapper
import org.coner.trailer.seasonpoints.SeasonPointsCalculatorConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

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
}