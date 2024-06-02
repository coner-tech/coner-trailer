package tech.coner.trailer.presentation.library.testsupport.fooapp.presentation.adapter

import tech.coner.trailer.presentation.library.adapter.LoadableItemAdapter
import tech.coner.trailer.presentation.library.testsupport.fooapp.domain.entity.Foo
import tech.coner.trailer.presentation.library.testsupport.fooapp.presentation.model.FooModel

class FooAdapter : LoadableItemAdapter<
        Foo.Id,
        Foo,
        Unit,
        FooModel
        >() {
    override val argumentToModelAdapter = null
    override val itemToModelAdapter: (Foo) -> FooModel = ::FooModel
    override val modelToItemAdapter: (FooModel) -> Foo = {
        Foo(
            id = it.original.id,
            name = it.name.lowercase()
        )
    }

    val modelNamePropertyAdapter: (Foo) -> String
        get() { TODO() }

    private fun String.capitalizeFirstChar(): String { // TODO: adapter
        return when (length) {
            0 -> this
            1 -> uppercase()
            else -> let { "${it[0].uppercaseChar()}${it.substring(1)}" }
        }
    }
}