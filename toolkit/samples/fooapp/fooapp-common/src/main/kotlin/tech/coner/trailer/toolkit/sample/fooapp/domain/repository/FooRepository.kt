package tech.coner.trailer.toolkit.sample.fooapp.domain.repository

import tech.coner.trailer.toolkit.sample.fooapp.domain.entity.Foo

typealias FooRepository = SimpleRepository<Foo.Id, Foo>
