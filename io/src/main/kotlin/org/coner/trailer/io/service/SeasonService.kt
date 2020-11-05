package org.coner.trailer.io.service

import org.coner.trailer.Season
import org.coner.trailer.datasource.snoozle.SeasonResource
import org.coner.trailer.datasource.snoozle.entity.SeasonEntity
import org.coner.trailer.io.constraint.PersonDeleteConstraints
import org.coner.trailer.io.constraint.PersonPersistConstraints
import org.coner.trailer.io.constraint.SeasonPersistConstraints
import org.coner.trailer.io.mapper.SeasonMapper
import java.util.*
import kotlin.streams.toList

class SeasonService(
        private val resource: SeasonResource,
        private val mapper: SeasonMapper,
        private val persistConstraints: SeasonPersistConstraints,
        private val deleteConstraints: SeasonPersistConstraints
) {
    fun create(create: Season) {
        persistConstraints.assess(create)
        resource.create(mapper.toSnoozle(create))
    }

    fun findById(id: UUID): Season {
        val key = SeasonEntity.Key(id = id)
        return mapper.toCore(resource.read(key))
    }

    fun findByName(name: String): Season? {
        return resource.stream()
                .filter { it.name == name }
                .map(mapper::toCore)
                .findFirst()
                .orElse(null)
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