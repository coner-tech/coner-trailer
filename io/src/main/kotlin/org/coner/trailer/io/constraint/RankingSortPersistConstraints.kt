package org.coner.trailer.io.constraint

import org.coner.trailer.datasource.snoozle.RankingSortResource
import org.coner.trailer.io.mapper.RankingSortMapper
import org.coner.trailer.seasonpoints.RankingSort
import java.util.*

class RankingSortPersistConstraints(
        private val resource: RankingSortResource,
        private val mapper: RankingSortMapper
) : Constraint<RankingSort>() {

    override fun assess(candidate: RankingSort) {
        constrain(hasUniqueName(candidate.id, candidate.name)) { "Name is not unique" }
    }

    fun hasUniqueName(id: UUID, name: String) = resource.stream { it.id != id }
            .map(mapper::fromSnoozle)
            .noneMatch { it.name == name }
}