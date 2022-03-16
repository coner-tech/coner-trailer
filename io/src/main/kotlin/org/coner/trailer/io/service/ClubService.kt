package org.coner.trailer.io.service

import org.coner.trailer.Club
import org.coner.trailer.datasource.snoozle.ClubResource
import org.coner.trailer.datasource.snoozle.entity.ClubEntity
import org.coner.trailer.io.mapper.ClubMapper
import tech.coner.snoozle.db.entity.EntityIoException

class ClubService(
    private val resource: ClubResource,
    private val mapper: ClubMapper
) {

    fun get(): Club {
        return mapper.toCore(resource.read(ClubEntity.Key))
    }

    fun createOrUpdate(
        name: String
    ): Club {
        val club = Club(
            name = name
        )
        val snoozle = mapper.toSnoozle(club)
        try {
            resource.create(snoozle)
        } catch (eio: EntityIoException.AlreadyExists) {
            resource.update(snoozle)
        }
        return club
    }
}