package tech.coner.trailer.presentation.library.presenter

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import tech.coner.trailer.presentation.library.model.ItemModel
import tech.coner.trailer.presentation.library.state.LoadableItem
import tech.coner.trailer.presentation.library.state.LoadableItemState

abstract class LoadableItemPresenter<S, I, IM>
        where S : LoadableItemState<I>, IM : ItemModel<I> {

    protected abstract val initialState: S
    protected abstract val adapter: tech.coner.trailer.presentation.library.adapter.LoadableItemAdapter<I, IM>

    private val _itemModelFlow: MutableStateFlow<LoadableItem<IM>?> by lazy { MutableStateFlow(adapter(initialState.loadable)) }
    val itemModelFlow: StateFlow<LoadableItem<IM>?> by lazy { _itemModelFlow.asStateFlow() }
    val itemModel: LoadableItem<IM>?
        get() = itemModelFlow.value

}