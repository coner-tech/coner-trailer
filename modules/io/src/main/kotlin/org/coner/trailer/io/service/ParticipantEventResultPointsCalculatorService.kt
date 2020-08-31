package org.coner.trailer.io.service

import org.coner.trailer.datasource.snoozle.ParticipantEventResultPointsCalculatorResource
import org.coner.trailer.datasource.snoozle.entity.ParticipantEventResultPointsCalculatorEntity
import org.coner.trailer.io.mapper.ParticipantEventResultPointsCalculatorMapper
import org.coner.trailer.seasonpoints.ParticipantEventResultPointsCalculator
import java.util.*
import kotlin.streams.toList

class ParticipantEventResultPointsCalculatorService(
        private val resource: ParticipantEventResultPointsCalculatorResource,
        private val mapper: ParticipantEventResultPointsCalculatorMapper
) {

    fun create(calculator: ParticipantEventResultPointsCalculator) {
        resource.create(mapper.toSnoozle(calculator))
    }

    fun findById(id: UUID): ParticipantEventResultPointsCalculator {
        return mapper.fromSnoozle(
                resource.read(ParticipantEventResultPointsCalculatorEntity.Key(id = id))
        )
    }

    fun findByName(name: String): ParticipantEventResultPointsCalculator? {
        return resource.stream()
                .filter { it.name == name }
                .map(mapper::fromSnoozle)
                .toList()
                .singleOrNull()
    }

    fun list(): List<ParticipantEventResultPointsCalculator> {
        return resource.stream()
                .map(mapper::fromSnoozle)
                .toList()
    }

    fun update(calculator: ParticipantEventResultPointsCalculator) {
        // TODO: prohibit update of event points calculators used for finalized events
        // https://github.com/caeos/coner-trailer/issues/17
        resource.update(mapper.toSnoozle(calculator))
    }

    fun delete(calculator: ParticipantEventResultPointsCalculator) {
        resource.delete(mapper.toSnoozle(calculator))
    }

    fun hasNewName(name: String): Boolean {
        return resource.stream().noneMatch { it.name == name }
    }
}