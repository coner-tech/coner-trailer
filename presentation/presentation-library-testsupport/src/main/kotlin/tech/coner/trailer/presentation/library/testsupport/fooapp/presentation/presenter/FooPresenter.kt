package tech.coner.trailer.presentation.library.testsupport.fooapp.presentation.presenter

import tech.coner.trailer.presentation.library.presenter.LoadableItemPresenter
import tech.coner.trailer.presentation.library.state.LoadableItem
import tech.coner.trailer.presentation.library.testsupport.fooapp.presentation.model.FooModel
import tech.coner.trailer.presentation.library.testsupport.fooapp.presentation.state.FooState
import tech.coner.trailer.presentation.library.testsupport.fooapp.domain.entity.Foo
import tech.coner.trailer.presentation.library.testsupport.fooapp.presentation.adapter.FooAdapter

class FooPresenter(override val argument: Foo.Id) : LoadableItemPresenter<
        Foo.Id,
        FooState,
        Foo,
        Unit,
        FooModel
        >() {
    override val initialState = FooState(LoadableItem.Empty())
    override val adapter = FooAdapter()
}