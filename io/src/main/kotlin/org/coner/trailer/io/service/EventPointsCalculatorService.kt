package org.coner.trailer.io.service

import org.coner.trailer.datasource.snoozle.EventPointsCalculatorResource
import org.coner.trailer.datasource.snoozle.entity.EventPointsCalculatorEntity
import org.coner.trailer.io.constraint.EventPointsCalculatorPersistConstraints
import org.coner.trailer.io.mapper.EventPointsCalculatorMapper
import org.coner.trailer.seasonpoints.EventPointsCalculator
import java.util.*
import kotlin.streams.toList

class EventPointsCalculatorService(
        private val resource: EventPointsCalculatorResource,
        private val mapper: EventPointsCalculatorMapper,
        private val persistConstraints: EventPointsCalculatorPersistConstraints,
) {

    fun create(calculator: EventPointsCalculator) {
        persistConstraints.assess(calculator)
        resource.create(mapper.toSnoozle(calculator))
    }

    fun findById(id: UUID): EventPointsCalculator {
        return mapper.fromSnoozle(
                resource.read(EventPointsCalculatorEntity.Key(id = id))
        )
    }

    fun findByName(name: String): EventPointsCalculator {
        return resource.stream()
                .filter { it.name == name }
                .findFirst()
                .map(mapper::fromSnoozle)
                .orElseThrow { NotFoundException("No EventPointsCalculator found with name: $name") }
    }

    fun list(): List<EventPointsCalculator> {
        return resource.stream()
                .map(mapper::fromSnoozle)
                .toList()
    }

    fun update(calculator: EventPointsCalculator) {
        persistConstraints.assess(calculator)
        resource.update(mapper.toSnoozle(calculator))
    }

    fun delete(calculator: EventPointsCalculator) {
        // TODO: prohibit deletions of event points calculators used for finalized events
        // https://github.com/caeos/coner-trailer/issues/17
        resource.delete(mapper.toSnoozle(calculator))
    }

}