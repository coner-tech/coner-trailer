package tech.coner.trailer.presentation.library.testsupport.fooapp.presentation.state

import tech.coner.trailer.presentation.library.state.LoadableItem
import tech.coner.trailer.presentation.library.state.LoadableItemState
import tech.coner.trailer.presentation.library.testsupport.fooapp.domain.entity.Foo

data class FooState(override val loadable: LoadableItem<Foo>) : LoadableItemState<Foo>