package tech.coner.trailer.io.service

import tech.coner.trailer.Season
import tech.coner.trailer.datasource.snoozle.SeasonResource
import tech.coner.trailer.datasource.snoozle.entity.SeasonEntity
import tech.coner.trailer.io.constraint.SeasonDeleteConstraints
import tech.coner.trailer.io.constraint.SeasonPersistConstraints
import tech.coner.trailer.io.mapper.SeasonMapper
import java.util.*
import kotlin.streams.toList

class SeasonService(
        private val resource: SeasonResource,
        private val mapper: SeasonMapper,
        private val persistConstraints: SeasonPersistConstraints,
        private val deleteConstraints: SeasonDeleteConstraints
) {
    fun create(create: Season) {
        persistConstraints.assess(create)
        resource.create(mapper.toSnoozle(create))
    }

    fun findById(id: UUID): Season {
        val key = SeasonEntity.Key(id = id)
        return mapper.toCore(resource.read(key))
    }

    fun findByName(name: String): Season {
        return resource.stream()
                .filter { it.name == name }
                .map(mapper::toCore)
                .findFirst()
                .orElseThrow { NotFoundException("No Season found with name: $name") }
    }

    fun list(): List<Season> {
        return resource.stream()
                .map(mapper::toCore)
                .toList()
    }

    fun update(update: Season) {
        persistConstraints.assess(update)
        resource.update(mapper.toSnoozle(update))
    }

    fun delete(delete: Season) {
        deleteConstraints.assess(delete)
        resource.delete(mapper.toSnoozle(delete))
    }
}