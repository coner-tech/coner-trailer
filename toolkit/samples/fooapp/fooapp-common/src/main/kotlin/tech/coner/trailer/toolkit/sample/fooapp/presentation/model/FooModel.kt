package tech.coner.trailer.toolkit.sample.fooapp.presentation.model

import tech.coner.trailer.toolkit.presentation.model.Model
import tech.coner.trailer.toolkit.sample.fooapp.domain.entity.Foo

data class FooModel(
    val id: Foo.Id,
    val name: String
) : Model
