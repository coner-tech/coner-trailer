package tech.coner.trailer.presentation.library.presenter

import kotlinx.coroutines.flow.*
import tech.coner.trailer.presentation.library.adapter.Adapter
import tech.coner.trailer.presentation.library.model.ItemModel
import tech.coner.trailer.presentation.library.model.ModelNotReadyToCommitException
import tech.coner.trailer.presentation.library.model.ModelValidationException

/**
 * A base presenter that deals with a loadable entity as the focus of its state
 */
abstract class BaseItemPresenter<
        ARGUMENT : Presenter.Argument,
        ENTITY,
        ADAPTER : tech.coner.trailer.presentation.library.adapter.Adapter<ENTITY, ITEM_MODEL>,
        ITEM_MODEL : ItemModel<ENTITY>
        > : BasePresenter<ARGUMENT>() {

    abstract val entityDefault: ENTITY

    protected abstract val adapter: ADAPTER

    private val _itemModelFlow by lazy { MutableStateFlow(Result.success(adapter(entityDefault))) }
    val itemModelFlow: StateFlow<Result<ITEM_MODEL>> by lazy { _itemModelFlow.asStateFlow() }
    val itemModel: ITEM_MODEL
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

    suspend fun awaitLoadedItemModel(): Result<ITEM_MODEL> {
        return itemModelFlow.zip(loadedFlow) { model, loaded -> model to loaded }
            .mapNotNull { item ->
                val (result: Result<ITEM_MODEL>, loaded) = item
                when {
                    loaded && result.isSuccess -> result
                    result.isFailure -> result
                    else -> null
                }
            }
            .first()
    }

    protected abstract suspend fun performLoad(): Result<ENTITY>

    fun commit(): Result<ITEM_MODEL> {
        var commitReturn: Result<ITEM_MODEL> = _itemModelFlow.value
        _itemModelFlow.update { result ->
            val itemModel = result.getOrNull()
            if (itemModel != null) {
                // itemModel can be validated
                if (itemModel.isValid) {
                    // itemModel is valid
                    Result.success(adapter(itemModel.itemValue))
                        .also { commitReturn = it }
                } else {
                    // itemModel is invalid
                    commitReturn =  itemModel.validatedItemFlow.value.violations
                        .let { Result.failure(ModelValidationException(it)) }
                    result // will retain the same result/model unchanged
                }
            } else {
                // itemModel isn't ready for validation
                // perhaps there was a failure loading it
                commitReturn = result.exceptionOrNull()
                    ?.let { Result.failure(ModelNotReadyToCommitException(it)) }
                    ?: Result.failure(Exception("Failed to find cause of commit not ready."))
                result // will retain the same result/model unchanged
            }
        }
        return commitReturn
    }

    fun rollback() {
        _itemModelFlow.update { item ->
            Result.success(adapter(item.getOrNull()?.original ?: entityDefault))
        }
    }
}