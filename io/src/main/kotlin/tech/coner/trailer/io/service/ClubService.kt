package tech.coner.trailer.io.service

import arrow.core.Either
import arrow.core.raise.either
import tech.coner.snoozle.db.entity.EntityIoException
import tech.coner.trailer.domain.entity.Club
import tech.coner.trailer.datasource.snoozle.ClubResource
import tech.coner.trailer.datasource.snoozle.entity.ClubEntity
import tech.coner.trailer.io.constraint.ClubPersistConstraints
import tech.coner.trailer.io.mapper.ClubMapper

class ClubService(
    private val persistConstraints: ClubPersistConstraints,
    private val resource: ClubResource,
    private val mapper: ClubMapper
) {

    fun get(): Result<Either<GetFailure, Club>> = runCatching {
        either {
            try {
                mapper.toCore(resource.read(ClubEntity.Key))
            } catch (nFE: NotFoundException) {
                raise(GetFailure.NotFound)
            }
        }
    }

    sealed interface GetFailure {
        data object NotFound : GetFailure
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