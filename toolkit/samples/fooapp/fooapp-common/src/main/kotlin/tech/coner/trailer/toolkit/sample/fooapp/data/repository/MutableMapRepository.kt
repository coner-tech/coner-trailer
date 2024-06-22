package tech.coner.trailer.toolkit.sample.fooapp.data.repository

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.ensureNotNull
import tech.coner.trailer.toolkit.sample.fooapp.domain.exception.AlreadyExistsException
import tech.coner.trailer.toolkit.sample.fooapp.domain.exception.NotFoundException
import tech.coner.trailer.toolkit.sample.fooapp.domain.repository.SimpleRepository
import tech.coner.trailer.toolkit.sample.fooapp.domain.repository.SimpleRepository.*

abstract class MutableMapRepository<KEY, VALUE> : SimpleRepository<KEY, VALUE> {

    protected abstract val keyFn: (VALUE) -> KEY
    protected abstract val mutableMap: MutableMap<KEY, VALUE>

    override fun create(value: VALUE): Result<Either<CreateFailure, VALUE>> = runCatching {
        either {
            val key = keyFn(value)
            ensure(!mutableMap.containsKey(key)) { CreateFailure.AlreadyExists }
            value.also { mutableMap[key] = it }
        }
    }

    override fun read(key: KEY): Result<Either<ReadFailure, VALUE>> = runCatching {
        either {
            ensureNotNull(mutableMap[key]) {
                ReadFailure.NotFound
            }
        }
    }

    override fun update(value: VALUE): Result<Either<UpdateFailure, VALUE>> = runCatching {
        either {
            val key = keyFn(value)
            ensure(mutableMap.containsKey(key)) {
                UpdateFailure.NotFound
            }
            value.also { mutableMap[key] = it }
        }
    }

    override fun delete(key: KEY): Result<Either<DeleteFailure, VALUE>> = runCatching {
        either {
           ensureNotNull(mutableMap.remove(key)) {
               DeleteFailure.NotFound
           }
        }
    }

    override fun exists(key: KEY): Result<Boolean> = runCatching {
        mutableMap.containsKey(key)
    }
}