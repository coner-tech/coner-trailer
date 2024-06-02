package tech.coner.trailer.presentation.library.testsupport.fooapp.data.service

import tech.coner.trailer.presentation.library.testsupport.fooapp.domain.entity.Foo

interface FooService {

    suspend fun create(create: Foo): Result<Foo>
    suspend fun findById(id: Foo.Id): Result<Foo>
    suspend fun update(update: Foo): Result<Foo>
    suspend fun deleteById(id: Foo.Id): Result<Foo>
}