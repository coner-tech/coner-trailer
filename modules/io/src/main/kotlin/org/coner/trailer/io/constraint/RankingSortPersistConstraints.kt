package org.coner.trailer.io.constraint

import org.coner.trailer.datasource.snoozle.RankingSortResource
import org.coner.trailer.io.mapper.RankingSortMapper
import org.coner.trailer.seasonpoints.RankingSort

class RankingSortPersistConstraints(
        private val resource: RankingSortResource,
        private val mapper: RankingSortMapper
) : Constraint<RankingSort>() {

    override fun assess(candidate: RankingSort) {
        constrain(
                resource.stream { it.id != candidate.id }
                        .map(mapper::fromSnoozle)
                        .noneMatch { it.name == candidate.name }
        ) { "Name is not unique" }
    }
}