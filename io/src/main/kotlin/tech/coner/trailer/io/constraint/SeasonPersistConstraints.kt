package tech.coner.trailer.io.constraint

import tech.coner.trailer.Season
import tech.coner.trailer.datasource.snoozle.SeasonResource
import tech.coner.trailer.io.mapper.SeasonMapper
import java.util.*

class SeasonPersistConstraints(
        private val resource: SeasonResource,
        private val mapper: SeasonMapper
) : Constraint<Season>() {

    override fun assess(candidate: Season) {
        constrain(hasUniqueName(candidate.id, candidate.name)) { "Name is not unique" }
    }

    fun hasUniqueName(id: UUID, name: String) = resource.stream { it.id != id }
            .map(mapper::toCore)
            .noneMatch { it.name == name }
}