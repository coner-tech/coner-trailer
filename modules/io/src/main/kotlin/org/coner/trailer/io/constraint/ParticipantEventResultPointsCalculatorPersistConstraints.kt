package org.coner.trailer.io.constraint

import org.coner.trailer.datasource.snoozle.ParticipantEventResultPointsCalculatorResource
import org.coner.trailer.io.mapper.ParticipantEventResultPointsCalculatorMapper
import org.coner.trailer.seasonpoints.ParticipantEventResultPointsCalculator
import java.util.*

class ParticipantEventResultPointsCalculatorPersistConstraints(
        private val resource: ParticipantEventResultPointsCalculatorResource,
        private val mapper: ParticipantEventResultPointsCalculatorMapper
) : Constraint<ParticipantEventResultPointsCalculator>() {

    override fun assess(candidate: ParticipantEventResultPointsCalculator) {
        constrain(hasUniqueName(candidate.id, candidate.name)) { "Name is not unique" }
        // TODO: prohibit alterations to event points calculators used for finalized events
        // https://github.com/caeos/coner-trailer/issues/17
    }

    fun hasUniqueName(id: UUID, name: String) = resource.stream { it.id != id }
            .noneMatch { it.name == name }
}