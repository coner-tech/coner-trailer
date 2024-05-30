package tech.coner.trailer.presentation.library.adapter

import tech.coner.trailer.presentation.library.model.ItemModel
import tech.coner.trailer.presentation.library.model.LoadableModel
import tech.coner.trailer.presentation.library.state.LoadableItem
import tech.coner.trailer.presentation.library.state.LoadableItemState

class LoadableItemAdapter<ITEM, STATE : LoadableItemState<ITEM>, ITEM_MODEL : ItemModel<ITEM>, ARGUMENT_MODEL>(
    private val itemAdapter: Adapter<ITEM, ITEM_MODEL>
) : Adapter<STATE, LoadableModel<IM>> {
    override fun invoke(model: STATE): LoadableItem<IM> {
        val loadable = model.loadable
        return when (model.loadable) {
            is LoadableItem.Empty<I> -> LoadableModel.Empty
            is LoadableItem.Loading<I> -> LoadableItem.Loading(prior = loadable.priorItem?.let { itemAdapter(it) })
            is LoadableItem.Loaded<I> -> LoadableItem.Loaded(item = itemAdapter(loadable.item))
            is LoadableItem.LoadFailed<I> -> LoadableItem.LoadFailed(
                priorItem = model.priorItem?.let { itemAdapter(it) },
                cause = model.cause
            )
        }
    }
}