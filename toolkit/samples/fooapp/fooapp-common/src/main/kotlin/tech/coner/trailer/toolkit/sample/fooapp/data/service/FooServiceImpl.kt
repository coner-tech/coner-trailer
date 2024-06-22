package tech.coner.trailer.toolkit.sample.fooapp.data.service

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import tech.coner.trailer.toolkit.sample.fooapp.domain.entity.Foo
import tech.coner.trailer.toolkit.sample.fooapp.domain.repository.FooRepository
import tech.coner.trailer.toolkit.sample.fooapp.domain.repository.SimpleRepository
import tech.coner.trailer.toolkit.sample.fooapp.domain.service.FooService
import tech.coner.trailer.toolkit.sample.fooapp.domain.service.FooService.CreateFailure
import tech.coner.trailer.toolkit.sample.fooapp.domain.validation.FooValidator
import tech.coner.trailer.toolkit.validation.invoke

class FooServiceImpl(
    private val repository: FooRepository,
    private val validator: FooValidator,
) : FooService {

    override suspend fun create(create: Foo): Result<Either<CreateFailure, Foo>> = runCatching {
        either {
            validator(create)
                .also { ensure(it.isValid) { CreateFailure.Invalid(it) } }

            repository.create(create).getOrThrow()
                .mapLeft {
                    when (it) {
                        SimpleRepository.CreateFailure.AlreadyExists -> CreateFailure.AlreadyExists
                    }
                }
                .bind()
        }
    }

    override suspend fun findByKey(key: Foo.Id): Result<Either<FooService.FindFailure, Foo>> = runCatching {
        repository.read(key).getOrThrow()
            .mapLeft {
                when (it) {
                    SimpleRepository.ReadFailure.NotFound -> FooService.FindFailure.NotFound
                }
            }
    }

    override suspend fun update(update: Foo): Result<Either<FooService.UpdateFailure, Foo>> = runCatching {
        either {
            validator(update)
                .also { ensure(it.isValid) { FooService.UpdateFailure.Invalid(it) } }

            repository.update(update).getOrThrow()
                .mapLeft {
                    when (it) {
                        SimpleRepository.UpdateFailure.NotFound -> FooService.UpdateFailure.NotFound
                    }
                }
                .bind()
        }
    }

    override suspend fun deleteByKey(key: Foo.Id): Result<Either<FooService.DeleteFailure, Foo>> = runCatching {
        repository.delete(key).getOrThrow()
            .mapLeft {
                when (it) {
                    SimpleRepository.DeleteFailure.NotFound -> FooService.DeleteFailure.NotFound
                }
            }
    }
}
