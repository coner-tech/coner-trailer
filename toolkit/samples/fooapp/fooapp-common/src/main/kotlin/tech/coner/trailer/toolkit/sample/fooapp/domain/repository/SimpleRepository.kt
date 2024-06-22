package tech.coner.trailer.toolkit.sample.fooapp.domain.repository

import arrow.core.Either

interface SimpleRepository<KEY, VALUE> {

    fun create(value: VALUE): Result<Either<CreateFailure, VALUE>>

    sealed interface CreateFailure {
        data object AlreadyExists : CreateFailure
    }

    fun read(key: KEY): Result<Either<ReadFailure, VALUE>>

    sealed interface ReadFailure {
        data object NotFound : ReadFailure
    }

    fun update(value: VALUE): Result<Either<UpdateFailure, VALUE>>

    sealed interface UpdateFailure {
        data object NotFound : UpdateFailure
    }

    fun delete(key: KEY): Result<Either<DeleteFailure, VALUE>>

    sealed interface DeleteFailure {
        data object NotFound : DeleteFailure
    }

    fun exists(key: KEY): Result<Boolean>
}