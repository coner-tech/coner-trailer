package tech.coner.trailer.presentation.library.presenter

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import tech.coner.trailer.presentation.library.state.LoadableItem
import tech.coner.trailer.presentation.library.state.LoadableItemState

abstract class LoadableItemPresenter<ARGUMENT, STATE, ITEM>
        where STATE : LoadableItemState<ITEM> {

    protected abstract val argument: ARGUMENT
    protected abstract val initialState: STATE

    private val _stateFlow: MutableStateFlow<STATE> by lazy { MutableStateFlow(initialState) }
    val stateFlow: StateFlow<STATE> by lazy { _stateFlow.asStateFlow() }

    private val loadableItemFlow: Flow<LoadableItem<ITEM>> by lazy { stateFlow.map { it.loadable } }
}