package tech.coner.trailer.io.service

import tech.coner.trailer.datasource.snoozle.SeasonPointsCalculatorConfigurationResource
import tech.coner.trailer.datasource.snoozle.entity.SeasonPointsCalculatorConfigurationEntity
import tech.coner.trailer.io.constraint.SeasonPointsCalculatorConfigurationConstraints
import tech.coner.trailer.io.mapper.SeasonPointsCalculatorConfigurationMapper
import tech.coner.trailer.seasonpoints.SeasonPointsCalculatorConfiguration
import java.util.*

class SeasonPointsCalculatorConfigurationService(
        private val resource: SeasonPointsCalculatorConfigurationResource,
        private val mapper: SeasonPointsCalculatorConfigurationMapper,
        private val constraints: SeasonPointsCalculatorConfigurationConstraints
) {

    fun create(create: SeasonPointsCalculatorConfiguration) {
        constraints.assess(create)
        resource.create(mapper.toSnoozle(create))
    }

    fun findById(id: UUID): SeasonPointsCalculatorConfiguration {
        val key = SeasonPointsCalculatorConfigurationEntity.Key(id = id)
        return mapper.fromSnoozle(resource.read(key))
    }

    fun findByName(name: String): SeasonPointsCalculatorConfiguration {
        return resource.stream()
                .filter { it.name == name }
                .map(mapper::fromSnoozle)
                .findFirst()
                .orElseThrow { NotFoundException("No SeasonPointsCalculatorConfiguration found with name: $name") }
    }

    fun list(): List<SeasonPointsCalculatorConfiguration> {
        return resource.stream()
                .map(mapper::fromSnoozle)
                .toList()
    }

    fun update(update: SeasonPointsCalculatorConfiguration) {
        constraints.assess(update)
        resource.update(mapper.toSnoozle(update))
    }

    fun delete(delete: SeasonPointsCalculatorConfiguration) {
        resource.delete(mapper.toSnoozle(delete))
    }
}