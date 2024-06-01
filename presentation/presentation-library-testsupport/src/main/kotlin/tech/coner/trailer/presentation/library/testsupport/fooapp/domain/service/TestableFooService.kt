package tech.coner.trailer.presentation.library.testsupport.fooapp.domain.service

import tech.coner.trailer.presentation.library.testsupport.fooapp.data.service.FooService
import tech.coner.trailer.presentation.library.testsupport.fooapp.domain.entity.*
import tech.coner.trailer.presentation.library.testsupport.fooapp.domain.exception.NotFoundException
import tech.coner.trailer.toolkit.konstraints.Constraint

class TestableFooService(private val constraint: Constraint<Foo>) : FooService {

    private val table: MutableMap<Foo.Id, Foo> = arrayOf(
        FOO_ID_FOO to "foo",
        FOO_ID_BAR to "bar",
        FOO_ID_BAZ to "baz",
        FOO_ID_BAT to "bat",
    )
        .associate { (idValue, name) -> Foo.Id(idValue).let { it to Foo(it, name) } }
        .toMutableMap()

    override suspend fun findById(id: Foo.Id): Result<Foo> {
        return table[id]?.let { Result.success(it) }
            ?: Result.failure(NotFoundException())
    }

    override suspend fun update(update: Foo): Result<Foo> {
        return findById(update.id)
            .mapCatching { constraint(update).getOrThrow() }
            .map { table[update.id] = update; update }
    }
}
