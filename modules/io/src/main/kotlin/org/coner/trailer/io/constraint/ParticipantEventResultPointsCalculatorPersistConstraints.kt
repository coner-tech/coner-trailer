package org.coner.trailer.io.constraint

import org.coner.trailer.datasource.snoozle.ParticipantEventResultPointsCalculatorResource
import org.coner.trailer.io.mapper.ParticipantEventResultPointsCalculatorMapper
import org.coner.trailer.seasonpoints.ParticipantEventResultPointsCalculator

class ParticipantEventResultPointsCalculatorPersistConstraints(
        private val resource: ParticipantEventResultPointsCalculatorResource,
        private val mapper: ParticipantEventResultPointsCalculatorMapper
) : Constraint<ParticipantEventResultPointsCalculator>() {

    override fun assess(candidate: ParticipantEventResultPointsCalculator) {
        constrain(
                resource.stream { it.id != candidate.id }
                        .map(mapper::fromSnoozle)
                        .noneMatch { it.name == candidate.name }
        ) { "Name is not unique" }
    }
    // TODO: prohibit alterations to event points calculators used for finalized events
    // https://github.com/caeos/coner-trailer/issues/17
}