package org.coner.trailer.io.constraint

import org.coner.trailer.datasource.snoozle.SeasonPointsCalculatorConfigurationResource
import org.coner.trailer.io.mapper.SeasonPointsCalculatorConfigurationMapper
import org.coner.trailer.seasonpoints.SeasonPointsCalculatorConfiguration
import java.util.*

class SeasonPointsCalculatorConfigurationConstraints(
        private val resource: SeasonPointsCalculatorConfigurationResource,
        private val mapper: SeasonPointsCalculatorConfigurationMapper
) : Constraint<SeasonPointsCalculatorConfiguration>() {

    override fun assess(candidate: SeasonPointsCalculatorConfiguration) {
        constrain(hasUniqueName(candidate.id, candidate.name)) { "Name is not unique" }
    }

    fun hasUniqueName(id: UUID, name: String) = resource.stream { it.id != id }
            .map(mapper::fromSnoozle)
            .noneMatch { it.name == name }


}