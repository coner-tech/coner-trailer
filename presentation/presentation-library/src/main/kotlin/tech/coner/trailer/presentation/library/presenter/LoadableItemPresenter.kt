package tech.coner.trailer.presentation.library.presenter

import kotlinx.coroutines.flow.*
import tech.coner.trailer.presentation.library.adapter.LoadableItemAdapter
import tech.coner.trailer.presentation.library.model.ItemModel
import tech.coner.trailer.presentation.library.model.LoadableModel
import tech.coner.trailer.presentation.library.state.LoadableItemState

abstract class LoadableItemPresenter<ARGUMENT, STATE, ITEM, ARGUMENT_MODEL, ITEM_MODEL>
        where STATE : LoadableItemState<ITEM>,
              ITEM_MODEL : ItemModel<ITEM> {

    protected abstract val argument: ARGUMENT
    protected abstract val initialState: STATE

    protected abstract val adapter: LoadableItemAdapter<ARGUMENT, STATE, ITEM, ARGUMENT_MODEL, ITEM_MODEL>

    private val _stateFlow: MutableStateFlow<STATE> by lazy { MutableStateFlow(initialState) }
    val stateFlow: StateFlow<STATE> by lazy { _stateFlow.asStateFlow() }

    private val modelFlow: Flow<LoadableModel<ARGUMENT_MODEL, ITEM_MODEL>> by lazy {
        stateFlow.map { state -> adapter(argument, state) }
    }
}