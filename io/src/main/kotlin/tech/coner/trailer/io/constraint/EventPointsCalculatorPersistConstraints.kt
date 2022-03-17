package tech.coner.trailer.io.constraint

import tech.coner.trailer.datasource.snoozle.EventPointsCalculatorResource
import tech.coner.trailer.io.mapper.EventPointsCalculatorMapper
import tech.coner.trailer.seasonpoints.EventPointsCalculator
import java.util.*

class EventPointsCalculatorPersistConstraints(
        private val resource: EventPointsCalculatorResource,
        private val mapper: EventPointsCalculatorMapper
) : Constraint<EventPointsCalculator>() {

    override fun assess(candidate: EventPointsCalculator) {
        constrain(hasUniqueName(candidate.id, candidate.name)) { "Name is not unique" }
        // TODO: prohibit alterations to event points calculators used for finalized events
        // https://github.com/caeos/coner-trailer/issues/17
    }

    fun hasUniqueName(id: UUID, name: String) = resource.stream { it.id != id }
            .noneMatch { it.name == name }
}