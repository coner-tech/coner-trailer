package tech.coner.trailer.toolkit.sample.fooapp.data.repository

import tech.coner.trailer.toolkit.sample.fooapp.domain.entity.FOO_ID_BAR
import tech.coner.trailer.toolkit.sample.fooapp.domain.entity.FOO_ID_BAT
import tech.coner.trailer.toolkit.sample.fooapp.domain.entity.FOO_ID_BAZ
import tech.coner.trailer.toolkit.sample.fooapp.domain.entity.FOO_ID_FOO
import tech.coner.trailer.toolkit.sample.fooapp.domain.entity.Foo

class FooRepositoryImpl : MutableMapRepository<Foo.Id, Foo>() {

    override val keyFn: (Foo) -> Foo.Id = { it.id }

    override val mutableMap = arrayOf(
        FOO_ID_FOO to "foo",
        FOO_ID_BAR to "bar",
        FOO_ID_BAZ to "baz",
        FOO_ID_BAT to "bat",
    )
        .associate { (idValue, name) -> Foo.Id(idValue).let { it to Foo(it, name) } }
        .toMutableMap()

}