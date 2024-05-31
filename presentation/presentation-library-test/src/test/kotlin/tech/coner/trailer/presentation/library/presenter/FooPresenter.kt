package tech.coner.trailer.presentation.library.presenter

import tech.coner.trailer.presentation.library.adapter.LoadableItemAdapter
import tech.coner.trailer.presentation.library.model.BaseItemModel
import tech.coner.trailer.presentation.library.state.LoadableItem
import tech.coner.trailer.presentation.library.state.LoadableItemState
import tech.coner.trailer.toolkit.konstraints.CompositeConstraint

class FooPresenter : LoadableItemPresenter<
        Unit,
        FooState,
        Foo,
        Unit,
        FooModel
        >() {
    override val argument = Unit
    override val initialState = FooState(LoadableItem.Empty())
    override val adapter = FooAdapter()
}

data class Foo(val value: Int)

data class FooState(override val loadable: LoadableItem<Foo>) : LoadableItemState<Foo>

class FooModel(override val original: Foo) : BaseItemModel<Foo, FooConstraint>() {
    override val constraints = FooConstraint()
}

class FooConstraint : CompositeConstraint<Foo>() {

    val valueIsInRange = propertyConstraint(
        property = Foo::value,
        assessFn = {
            when (it.value) {
                in 0..4 -> true
                else -> false
            }
        },
        violationMessageFn = { "Value is out of range" }
    )
}

class FooAdapter : LoadableItemAdapter<
        Unit,
        FooState,
        Foo,
        Unit,
        FooModel
        >() {
    override val argumentModelAdapter = null
    override val partialItemAdapter = null
    override val itemAdapter: (Foo) -> FooModel = ::FooModel
}