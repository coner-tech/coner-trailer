package tech.coner.trailer.presentation.library.adapter

import tech.coner.trailer.presentation.library.model.ItemModel
import tech.coner.trailer.presentation.library.model.LoadableModel
import tech.coner.trailer.presentation.library.state.LoadableItem
import tech.coner.trailer.presentation.library.state.LoadableItemState

abstract class LoadableItemAdapter<ARGUMENT, STATE, ITEM, ARGUMENT_MODEL, ITEM_MODEL>
        where STATE : LoadableItemState<ITEM>,
              ITEM_MODEL : ItemModel<ITEM> {

    protected abstract val argumentModelAdapter: ((ARGUMENT) -> ARGUMENT_MODEL)?
    protected abstract val partialItemAdapter: ((ARGUMENT, STATE) -> ITEM_MODEL)?
    protected abstract val itemAdapter: (ITEM) -> ITEM_MODEL

    operator fun invoke(argument: ARGUMENT, state: STATE): LoadableModel<ARGUMENT_MODEL, ITEM_MODEL> {
        val argumentModel: ARGUMENT_MODEL? = argumentModelAdapter?.invoke(argument)
        return when (val loadable = state.loadable) {
            is LoadableItem.Empty<ITEM> -> LoadableModel.Empty(
                argument = argumentModel
            )

            is LoadableItem.Loading<ITEM> -> LoadableModel.Loading(
                argument = argumentModel,
                partial = partialItemAdapter?.invoke(argument, state),
            )

            is LoadableItem.Loaded<ITEM> -> LoadableModel.Loaded(
                argument = argumentModel,
                item = itemAdapter(loadable.item)
            )

            is LoadableItem.LoadFailed<ITEM> -> LoadableModel.LoadFailed(
                argument = argumentModel,
                cause = loadable.cause
            )
        }
    }
}

