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


}