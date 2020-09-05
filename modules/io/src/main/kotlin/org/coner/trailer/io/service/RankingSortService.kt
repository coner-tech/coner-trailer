package org.coner.trailer.io.service

import org.coner.trailer.datasource.snoozle.RankingSortResource
import org.coner.trailer.io.constraint.RankingSortPersistConstraints
import org.coner.trailer.io.mapper.RankingSortMapper
import org.coner.trailer.seasonpoints.RankingSort

class RankingSortService(
        private val resource: RankingSortResource,
        private val mapper: RankingSortMapper,
        private val persistConstraints: RankingSortPersistConstraints
) {

    fun create(rankingSort: RankingSort) {
        persistConstraints.assess(rankingSort)
        resource.create(mapper.toSnoozle(rankingSort))
    }
}