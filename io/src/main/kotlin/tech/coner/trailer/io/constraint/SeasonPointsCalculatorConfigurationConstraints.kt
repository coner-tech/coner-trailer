package tech.coner.trailer.io.constraint

import tech.coner.trailer.datasource.snoozle.SeasonPointsCalculatorConfigurationResource
import tech.coner.trailer.io.mapper.SeasonPointsCalculatorConfigurationMapper
import tech.coner.trailer.seasonpoints.SeasonPointsCalculatorConfiguration
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