package org.coner.trailer.io.service

import org.coner.trailer.datasource.snoozle.SeasonPointsCalculatorConfigurationResource
import org.coner.trailer.datasource.snoozle.entity.SeasonPointsCalculatorConfigurationEntity
import org.coner.trailer.io.constraint.SeasonPointsCalculatorConfigurationConstraints
import org.coner.trailer.io.mapper.SeasonPointsCalculatorConfigurationMapper
import org.coner.trailer.seasonpoints.SeasonPointsCalculatorConfiguration
import java.util.*
import kotlin.streams.toList

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

    fun findByName(name: String): SeasonPointsCalculatorConfiguration? {
        return resource.stream()
                .filter { it.name == name }
                .map(mapper::fromSnoozle)
                .findFirst()
                .orElse(null)
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
}