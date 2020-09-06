package org.coner.trailer.io.service

import org.coner.trailer.datasource.snoozle.RankingSortResource
import org.coner.trailer.datasource.snoozle.entity.RankingSortEntity
import org.coner.trailer.io.constraint.RankingSortPersistConstraints
import org.coner.trailer.io.mapper.RankingSortMapper
import org.coner.trailer.seasonpoints.RankingSort
import java.util.*

class RankingSortService(
        private val resource: RankingSortResource,
        private val mapper: RankingSortMapper,
        private val persistConstraints: RankingSortPersistConstraints
) {

    fun create(rankingSort: RankingSort) {
        persistConstraints.assess(rankingSort)
        resource.create(mapper.toSnoozle(rankingSort))
    }

    fun findById(id: UUID): RankingSort {
        val key = RankingSortEntity.Key(id = id)
        return mapper.fromSnoozle(resource.read(key))
    }

    fun update(rankingSort: RankingSort) {
        resource.update(mapper.toSnoozle(rankingSort))
    }
}