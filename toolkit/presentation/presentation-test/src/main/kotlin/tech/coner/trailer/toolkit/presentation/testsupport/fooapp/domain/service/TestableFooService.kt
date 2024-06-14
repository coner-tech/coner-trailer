package tech.coner.trailer.toolkit.presentation.testsupport.fooapp.domain.service

import tech.coner.trailer.toolkit.konstraints.Constraint
import tech.coner.trailer.toolkit.presentation.testsupport.fooapp.domain.entity.FOO_ID_BAR
import tech.coner.trailer.toolkit.presentation.testsupport.fooapp.domain.entity.FOO_ID_BAT
import tech.coner.trailer.toolkit.presentation.testsupport.fooapp.domain.entity.FOO_ID_BAZ
import tech.coner.trailer.toolkit.presentation.testsupport.fooapp.domain.entity.FOO_ID_FOO
import tech.coner.trailer.toolkit.presentation.testsupport.fooapp.domain.entity.Foo
import tech.coner.trailer.toolkit.presentation.testsupport.fooapp.domain.exception.AlreadyExistsException
import tech.coner.trailer.toolkit.presentation.testsupport.fooapp.domain.exception.NotFoundException

class TestableFooService(private val constraint: Constraint<Foo>) :
    tech.coner.trailer.toolkit.presentation.testsupport.fooapp.data.service.FooService {

    private val map: MutableMap<Foo.Id, Foo> = arrayOf(
        FOO_ID_FOO to "foo",
        FOO_ID_BAR to "bar",
        FOO_ID_BAZ to "baz",
        FOO_ID_BAT to "bat",
    )
        .associate { (idValue, name) -> Foo.Id(idValue).let { it to Foo(it, name) } }
        .toMutableMap()

    override suspend fun create(create: Foo): Result<Foo> {
        if (map.containsKey(create.id)) {
            return Result.failure(AlreadyExistsException())
        }
        return constraint(create)
            .map { map[create.id] = create; create }
    }

    override suspend fun findById(id: Foo.Id): Result<Foo> {
        return map[id]?.let { Result.success(it) }
            ?: Result.failure(NotFoundException())
    }

    override suspend fun update(update: Foo): Result<Foo> {
        return findById(update.id)
            .mapCatching { constraint(update).getOrThrow() }
            .map { map[update.id] = update; update }
    }

    override suspend fun deleteById(id: Foo.Id): Result<Foo> {
        return map.remove(id)
            ?.let { Result.success(it) }
            ?: Result.failure(NotFoundException())
    }
}
