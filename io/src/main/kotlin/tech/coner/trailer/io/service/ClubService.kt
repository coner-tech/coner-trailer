package tech.coner.trailer.io.service

import tech.coner.snoozle.db.entity.EntityIoException
import tech.coner.trailer.Club
import tech.coner.trailer.datasource.snoozle.ClubResource
import tech.coner.trailer.datasource.snoozle.entity.ClubEntity
import tech.coner.trailer.io.constraint.ClubPersistConstraints
import tech.coner.trailer.io.mapper.ClubMapper

class ClubService(
    private val persistConstraints: ClubPersistConstraints,
    private val resource: ClubResource,
    private val mapper: ClubMapper
) {

    fun get(): Club {
        return try {
            mapper.toCore(resource.read(ClubEntity.Key))
        } catch (notFoundException: EntityIoException.NotFound) {
            throw NotFoundException(message = "Club not found.", cause = notFoundException)
        } catch (readFailure: EntityIoException.ReadFailure) {
            throw ReadException(message = "Failed to read contents of Club from storage.", cause = readFailure)
        }
    }

    fun createOrUpdate(
        name: String
    ): Result<Club> = runCatching {
        val club = Club(
            name = name
        )
        val snoozle = mapper.toSnoozle(persistConstraints(club).getOrThrow())
        try {
            resource.create(snoozle)
        } catch (eio: EntityIoException.AlreadyExists) {
            resource.update(snoozle)
        }
        club
    }
}