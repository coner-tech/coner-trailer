package org.coner.trailer.cli.io

import org.coner.trailer.datasource.snoozle.ConerTrailerDatabase
import org.coner.trailer.datasource.snoozle.entity.ParticipantEventResultPointsCalculatorEntity
import org.coner.trailer.datasource.snoozle.entity.ParticipantEventResultPointsCalculatorMapper
import org.coner.trailer.seasonpoints.ParticipantEventResultPointsCalculator

class ParticipantEventResultPointsCalculatorService(
        private val database: ConerTrailerDatabase,
        private val mapper: ParticipantEventResultPointsCalculatorMapper
) {

    private val resource = database.entity<ParticipantEventResultPointsCalculatorEntity>()

    fun loadAll(): List<ParticipantEventResultPointsCalculator> {
        return resource.list()
                .map { mapper.fromSnoozle(it) }
    }

    fun loadAllMappedByName(): Map<String, ParticipantEventResultPointsCalculator> {
        return loadAll()
                .map { it.name to it }
                .toMap()
    }

    fun create(calculator: ParticipantEventResultPointsCalculator) {
        require(hasNewName(calculator, loadAll())) { "Name already exists: ${calculator.name}" }
        resource.put(mapper.fromCore(calculator))
    }

    fun delete(calculator: ParticipantEventResultPointsCalculator) {
        resource.delete(mapper.fromCore(calculator))
    }

    fun hasNewName(
            calculator: ParticipantEventResultPointsCalculator,
            inList: List<ParticipantEventResultPointsCalculator>
    ): Boolean {
        val withMatchingName = inList.filter { it.name == calculator.name }
        return withMatchingName.isEmpty()
    }

    fun hasUniqueName(
            calculator: ParticipantEventResultPointsCalculator,
            inList: List<ParticipantEventResultPointsCalculator>
    ): Boolean {
        val withMatchingName = inList.filter { it.name == calculator.name }
        return withMatchingName.isEmpty()
                || withMatchingName.singleOrNull { it.id == calculator.id } != null
    }
}