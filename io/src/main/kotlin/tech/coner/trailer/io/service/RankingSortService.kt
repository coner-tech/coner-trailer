package tech.coner.trailer.io.service

import tech.coner.trailer.datasource.snoozle.RankingSortResource
import tech.coner.trailer.datasource.snoozle.entity.RankingSortEntity
import tech.coner.trailer.io.constraint.RankingSortPersistConstraints
import tech.coner.trailer.io.mapper.RankingSortMapper
import tech.coner.trailer.seasonpoints.RankingSort
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

    fun list(): List<RankingSort> {
        return resource.stream()
                .map(mapper::fromSnoozle)
                .toList()
    }

    fun findByName(name: String): RankingSort? {
        return resource.stream()
                .map(mapper::fromSnoozle)
                .filter { it.name == name }
                .findFirst()
                .orElseThrow { NotFoundException("No RankingSort found with name: $name") }
    }

    fun delete(delete: RankingSort) {
        resource.delete(mapper.toSnoozle(delete))
    }
}