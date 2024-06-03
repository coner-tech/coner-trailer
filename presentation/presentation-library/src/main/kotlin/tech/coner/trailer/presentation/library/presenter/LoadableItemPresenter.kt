package tech.coner.trailer.presentation.library.presenter

import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withTimeout
import tech.coner.trailer.presentation.library.adapter.LoadableItemAdapter
import tech.coner.trailer.presentation.library.model.ItemModel
import tech.coner.trailer.presentation.library.model.LoadableModel
import tech.coner.trailer.presentation.library.state.LoadableItemState

abstract class LoadableItemPresenter<
        ARGUMENT,
        ITEM,
        ARGUMENT_MODEL,
        ITEM_MODEL
        >
    : CoroutineScope
        where ITEM_MODEL : ItemModel<ITEM> {

    protected abstract val adapter: LoadableItemAdapter<ARGUMENT, ITEM, ARGUMENT_MODEL, ITEM_MODEL>

    protected abstract val argument: ARGUMENT?
    val argumentModel: ARGUMENT_MODEL? by lazy {
        argument?.let { adapter.argumentToModelAdapter?.invoke(it) }
    }

    private val initialState = LoadableItemState<ITEM, ITEM_MODEL>(LoadableModel.Empty())
    private val _stateMutex = Mutex()
    private val _stateFlow: MutableStateFlow<LoadableItemState<ITEM, ITEM_MODEL>> by lazy { MutableStateFlow(initialState) }
    val stateFlow: StateFlow<LoadableItemState<ITEM, ITEM_MODEL>> by lazy { _stateFlow.asStateFlow() }

    suspend fun load() {
        update { old -> old.copy(LoadableModel.Loading()) }
        performLoad()
            .onSuccess { loaded -> update { old -> old.copy(LoadableModel.Loaded(adapter.itemToModelAdapter(loaded))) } }
            .onFailure { failed -> update { old -> old.copy(LoadableModel.LoadFailed(failed)) } }
    }

    protected abstract suspend fun performLoad(): Result<ITEM>

    suspend fun awaitModelLoadedOrFailed(): LoadableModel<ITEM, ITEM_MODEL> {
        return stateFlow
            .map { it.loadable }
            .first {
                when (it) {
                    is LoadableModel.Loaded, is LoadableModel.LoadFailed -> true
                    else -> false
                }
            }
    }

    suspend fun awaitModelLoadedOrThrow(): LoadableModel.Loaded<ITEM, ITEM_MODEL> {
        return stateFlow
            .map { it.loadable }
            .first {
                when (it) {
                    is LoadableModel.Loaded -> true
                    is LoadableModel.LoadFailed -> throw it.cause
                    else -> false
                }
            } as LoadableModel.Loaded<ITEM, ITEM_MODEL>
    }

    protected suspend fun update(reduceFn: (old: LoadableItemState<ITEM, ITEM_MODEL>) -> LoadableItemState<ITEM, ITEM_MODEL>) {
        _stateMutex.withLock {
            withTimeout(10.milliseconds) {
                _stateFlow.update(reduceFn)
            }
        }
    }

//    suspend fun commit(): Result<ITEM_MODEL> {
//        _stateMutex.withLock {
//            withTimeout(10.milliseconds) {
//            }
//        }
//    }
}