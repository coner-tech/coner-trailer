package org.coner.trailer.io.service

import org.coner.trailer.datasource.snoozle.RankingSortResource
import org.coner.trailer.io.mapper.RankingSortMapper
import org.coner.trailer.seasonpoints.RankingSort

class RankingSortService(
        private val resource: RankingSortResource,
        private val mapper: RankingSortMapper
) {

    fun create(rankingSort: RankingSort) {
        resource.create(mapper.toSnoozle(rankingSort))
    }
}