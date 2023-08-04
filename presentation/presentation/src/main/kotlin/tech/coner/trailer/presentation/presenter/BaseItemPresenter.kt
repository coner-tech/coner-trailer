package tech.coner.trailer.presentation.presenter

import kotlinx.coroutines.flow.*
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.model.util.ModelValidationException
import tech.coner.trailer.presentation.model.util.ItemModel
import tech.coner.trailer.presentation.model.util.ModelNotReadyToCommitException

/**
 * A base presenter that deals with a loadable entity as the focus of its state
 */
abstract class BaseItemPresenter<
        E,
        A : Adapter<E, IM>,
        IM : ItemModel<E, *, A>
        > : Presenter {

    abstract val entityDefault: E

    protected abstract val adapter: A

    private val _itemModelFlow by lazy { MutableStateFlow(Result.success(adapter(entityDefault))) }
    val itemModelFlow: StateFlow<Result<IM>> by lazy { _itemModelFlow.asStateFlow() }
    val itemModel: IM
        get() = itemModelFlow.value.getOrThrow()

    private val _loadingFlow = MutableStateFlow(false)
    val loadingFlow: StateFlow<Boolean> = _loadingFlow.asStateFlow()

    private val _loadedFlow = MutableStateFlow(false)
    val loadedFlow: StateFlow<Boolean> = _loadedFlow.asStateFlow()

    suspend fun load() {
        _loadingFlow.emit(true)
        performLoad()
            .onSuccess {
                _loadedFlow.emit(true)
                _itemModelFlow.emit(Result.success(adapter(it)))
            }
            .onFailure {
                _itemModelFlow.emit(Result.failure(it))
            }
        _loadingFlow.emit(false)
    }

    suspend fun awaitLoadedItemModel(): Result<IM> {
        return itemModelFlow.zip(loadedFlow) { model, loaded -> model to loaded }
            .mapNotNull { item ->
                val (result: Result<IM>, loaded) = item
                when {
                    loaded && result.isSuccess -> result
                    result.isFailure -> result
                    else -> null
                }
            }
            .first()
    }

    protected abstract suspend fun performLoad(): Result<E>

    suspend fun commit(): Result<IM> {
        return flow<Result<IM>> {
            _itemModelFlow.update { result ->
                val itemModel = result.getOrNull()
                if (itemModel != null) {
                    // itemModel can be validated
                    if (itemModel.isValid) {
                        // itemModel is valid
                        Result.success(adapter(itemModel.itemValue))
                            .also { emit(it) }
                    } else {
                        // itemModel is invalid
                        result // will retain the same result/model unchanged
                            .also {
                                emit(
                                    itemModel.validatedItemFlow.value.violations
                                        .let { Result.failure<IM>(ModelValidationException(it)) }
                                )
                            }
                    }
                } else {
                    // itemModel isn't ready for validation
                    // perhaps there was a failure loading it
                    result // will retain the same result/model unchanged
                        .also {
                            //
                            emit(
                                it.exceptionOrNull()
                                    ?.let { Result.failure(ModelNotReadyToCommitException(it)) }
                                    ?: Result.failure(Exception("Failed to find cause of commit not ready."))
                            )
                        }
                }
            }
        }
            .single()
    }

    fun rollback() {
        _itemModelFlow.update { item ->
            Result.success(adapter(item.getOrNull()?.original ?: entityDefault))
        }
    }
}