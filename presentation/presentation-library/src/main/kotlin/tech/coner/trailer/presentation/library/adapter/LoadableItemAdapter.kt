package tech.coner.trailer.presentation.library.adapter

import tech.coner.trailer.presentation.library.model.ItemModel
import tech.coner.trailer.presentation.library.state.LoadableItem

class LoadableItemAdapter<I, IM : ItemModel<I>>(
    private val itemAdapter: tech.coner.trailer.presentation.library.adapter.Adapter<I, IM>
) : tech.coner.trailer.presentation.library.adapter.Adapter<LoadableItem<I>, LoadableItem<IM>> {
    override fun invoke(model: LoadableItem<I>): LoadableItem<IM> {
        return when (model) {
            is LoadableItem.Empty<I> -> LoadableItem.Empty()
            is LoadableItem.Loading<I> -> LoadableItem.Loading(priorItem = model.priorItem?.let { itemAdapter(it) })
            is LoadableItem.Loaded<I> -> LoadableItem.Loaded(item = itemAdapter(model.item))
            is LoadableItem.LoadFailed<I> -> LoadableItem.LoadFailed(
                priorItem = model.priorItem?.let { itemAdapter(it) },
                cause = model.cause
            )
        }
    }
}