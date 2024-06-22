package tech.coner.trailer.toolkit.sample.fooapp.domain.service

import arrow.core.Either
import tech.coner.trailer.toolkit.sample.fooapp.domain.entity.Foo
import tech.coner.trailer.toolkit.sample.fooapp.domain.validation.FooFeedback
import tech.coner.trailer.toolkit.validation.ValidationOutcome

interface FooService {

    suspend fun create(create: Foo): Result<Either<CreateFailure, Foo>>

    sealed interface CreateFailure {
        data class Invalid(val validationOutcome: ValidationOutcome<Foo, FooFeedback>) : CreateFailure
        data object AlreadyExists : CreateFailure
    }

    suspend fun findByKey(key: Foo.Id): Result<Either<FindFailure, Foo>>

    sealed interface FindFailure {
        data object NotFound : FindFailure
    }

    suspend fun update(update: Foo): Result<Either<UpdateFailure, Foo>>

    sealed interface UpdateFailure {
        data class Invalid(val validationOutcome: ValidationOutcome<Foo, FooFeedback>) : UpdateFailure
        data object NotFound : UpdateFailure
    }

    suspend fun deleteByKey(key: Foo.Id): Result<Either<DeleteFailure, Foo>>

    sealed interface DeleteFailure {
        data object NotFound : DeleteFailure
    }

}