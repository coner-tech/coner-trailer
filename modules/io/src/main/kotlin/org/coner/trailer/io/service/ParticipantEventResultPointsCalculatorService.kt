package org.coner.trailer.io.service

import org.coner.trailer.datasource.snoozle.ParticipantEventResultPointsCalculatorResource
import org.coner.trailer.io.mapper.ParticipantEventResultPointsCalculatorMapper
import org.coner.trailer.seasonpoints.ParticipantEventResultPointsCalculator

class ParticipantEventResultPointsCalculatorService(
        private val resource: ParticipantEventResultPointsCalculatorResource,
        private val mapper: ParticipantEventResultPointsCalculatorMapper
) {

    fun create(calculator: ParticipantEventResultPointsCalculator) {
        resource.create(mapper.toSnoozle(calculator))
    }

    fun delete(calculator: ParticipantEventResultPointsCalculator) {
        resource.delete(mapper.toSnoozle(calculator))
    }

    fun hasNewName(name: String): Boolean {
        return resource.stream().noneMatch { it.name == name }
    }
}