package tech.coner.trailer.presentation.library.presenter

import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withTimeout
import tech.coner.trailer.presentation.library.adapter.LoadableItemAdapter
import tech.coner.trailer.presentation.library.model.ItemModel
import tech.coner.trailer.presentation.library.model.LoadableModel
import tech.coner.trailer.presentation.library.state.LoadableItem
import tech.coner.trailer.presentation.library.state.LoadableItemState

abstract class LoadableItemPresenter<
        ARGUMENT,
        ITEM,
        ARGUMENT_MODEL,
        ITEM_MODEL
        >
    : CoroutineScope
        where ITEM_MODEL : ItemModel<ITEM> {

    protected abstract val argument: ARGUMENT

    protected val initialState = LoadableItemState<ITEM>(LoadableItem.Empty())

    protected abstract val adapter: LoadableItemAdapter<ARGUMENT, ITEM, ARGUMENT_MODEL, ITEM_MODEL>

    private val _stateMutex = Mutex()
    private val _stateFlow: MutableStateFlow<LoadableItemState<ITEM>> by lazy { MutableStateFlow(initialState) }
    val stateFlow: StateFlow<LoadableItemState<ITEM>> by lazy { _stateFlow.asStateFlow() }

    val modelFlow: Flow<LoadableModel<ARGUMENT_MODEL, ITEM_MODEL>> by lazy {
        stateFlow
            .map { state -> adapter(argument, state) }
    }

    suspend fun load() {
        update { old -> old.copy(LoadableItem.Loading()) }
        performLoad()
            .onSuccess { update { old -> old.copy(LoadableItem.Loaded(it)) } }
            .onFailure { update { old -> old.copy(LoadableItem.LoadFailed(it)) } }
    }

    protected abstract suspend fun performLoad(): Result<ITEM>

    protected suspend fun update(reduceFn: (old: LoadableItemState<ITEM>) -> LoadableItemState<ITEM>) {
        _stateMutex.withLock {
            withTimeout(10.milliseconds) {
                _stateFlow.update(reduceFn)
            }
        }
    }

    suspend fun commit(): Result<ITEM_MODEL> {
        _stateMutex.withLock {
            withTimeout(10.milliseconds) {
                _stateFlow.update { state ->
                    when (val loadable = state.loadable) {

                    }
                }
            }
        }
    }
}