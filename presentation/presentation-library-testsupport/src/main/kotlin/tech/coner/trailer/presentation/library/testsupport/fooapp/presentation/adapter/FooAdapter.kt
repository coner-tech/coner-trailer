package tech.coner.trailer.presentation.library.testsupport.fooapp.presentation.adapter

import tech.coner.trailer.presentation.library.adapter.LoadableItemAdapter
import tech.coner.trailer.presentation.library.testsupport.fooapp.domain.entity.Foo
import tech.coner.trailer.presentation.library.testsupport.fooapp.presentation.model.FooModel
import tech.coner.trailer.presentation.library.testsupport.fooapp.presentation.state.FooState

class FooAdapter : LoadableItemAdapter<
        Foo.Id,
        FooState,
        Foo,
        Unit,
        FooModel
        >() {
    override val argumentModelAdapter = null
    override val partialItemAdapter = null
    override val itemAdapter: (Foo) -> FooModel = ::FooModel
}