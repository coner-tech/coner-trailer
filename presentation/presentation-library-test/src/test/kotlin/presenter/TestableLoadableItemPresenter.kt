package presenter

import tech.coner.trailer.io.constraint.CompositeConstraint
import tech.coner.trailer.io.constraint.Constraint
import tech.coner.trailer.presentation.library.adapter.Adapter
import tech.coner.trailer.presentation.library.adapter.LoadableItemAdapter
import tech.coner.trailer.presentation.library.model.BaseItemModel
import tech.coner.trailer.presentation.library.presenter.LoadableItemPresenter
import tech.coner.trailer.presentation.library.state.LoadableItem
import tech.coner.trailer.presentation.library.state.LoadableItemState

class TestableLoadableItemPresenter(
    override val initialState: State,
    override val adapter: LoadableItemAdapter<Item, Model>
) : LoadableItemPresenter<TestableLoadableItemPresenter.State, TestableLoadableItemPresenter.Item, TestableLoadableItemPresenter.Model>() {

    data class State(override val loadable: LoadableItem<Item>) : LoadableItemState<Item>

    data class Item(val value: Int)

    class Model(
        override val original: Item,
        override val constraints: ItemConstraint
    ) : BaseItemModel<Item, ItemConstraint>() {

    }

    class ItemConstraint(private val valid: Boolean) : CompositeConstraint<Item>() {

        private val validityConstraint = objectConstraint(
            assessFn = { valid },
            violationMessageFn = { "arbitrarily invalid" }
        )
    }

    class ItemAdapter : Adapter<Item, Model> {
        override fun invoke(model: Item): Model {
            return Model(
                original = model,
                constraints = ItemConstraint(true)
            )
        }

    }
}