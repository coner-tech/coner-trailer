package tech.coner.trailer.toolkit.sample.fooapp.presentation.adapter

import tech.coner.trailer.toolkit.presentation.adapter.EntityModelAdapter
import tech.coner.trailer.toolkit.sample.fooapp.domain.entity.Foo
import tech.coner.trailer.toolkit.sample.fooapp.presentation.model.FooModel
import tech.coner.trailer.toolkit.sample.fooapp.util.capitalizeFirstChar

class FooEntityModelAdapter : EntityModelAdapter<Foo, FooModel>() {
    override val entityToModelAdapter: (Foo) -> FooModel = {
        FooModel(
            id = it.id,
            name = entityToModelNamePropertyAdapter(it.name)
        )
    }
    override val modelToEntityAdapter: (FooModel) -> Foo = {
        Foo(
            id = it.id,
            name = it.name.lowercase()
        )
    }

    val entityToModelNamePropertyAdapter: (String) -> String = { it.capitalizeFirstChar() }
}