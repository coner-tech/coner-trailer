package tech.coner.trailer.presentation.library.testsupport.fooapp.presentation.adapter

import tech.coner.trailer.presentation.library.adapter.LoadableItemAdapter
import tech.coner.trailer.presentation.library.testsupport.fooapp.domain.entity.Foo
import tech.coner.trailer.presentation.library.testsupport.fooapp.presentation.model.FooItemModel

class FooAdapter : LoadableItemAdapter<
        Foo.Id,
        Foo,
        Unit,
        FooItemModel
        >() {
    override val argumentToModelAdapter = null
    override val itemToModelAdapter: (Foo) -> FooItemModel = { FooItemModel(it, this) }
    override val modelToItemAdapter: (FooItemModel) -> Foo = {
        Foo(
            id = it.item.id,
            name = it.name.lowercase()
        )
    }

    val modelNameProperty: (Foo) -> String = { it.name.capitalizeFirstChar() }
    val entityNameProperty: (String) -> String = { it.lowercase() }

    private fun String.capitalizeFirstChar(): String { // TODO: adapter
        return when (length) {
            0 -> this
            1 -> uppercase()
            else -> let { "${it[0].uppercaseChar()}${it.substring(1)}" }
        }
    }
}