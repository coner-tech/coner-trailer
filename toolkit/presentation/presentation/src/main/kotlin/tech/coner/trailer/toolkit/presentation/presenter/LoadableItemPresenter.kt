package tech.coner.trailer.toolkit.presentation.presenter

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import tech.coner.trailer.toolkit.presentation.adapter.LoadableItemAdapter
import tech.coner.trailer.toolkit.presentation.model.ItemModel
import tech.coner.trailer.toolkit.presentation.model.LoadableModel
import tech.coner.trailer.toolkit.presentation.model.Model
import tech.coner.trailer.toolkit.presentation.state.LoadableItemState
import tech.coner.trailer.toolkit.validation.Feedback

abstract class LoadableItemPresenter<ITEM, ITEM_MODEL, FEEDBACK : Feedback>(
    override val initialState: LoadableItemState<ITEM, ITEM_MODEL, FEEDBACK> = LoadableItemState(LoadableModel.Empty())
)
    : SecondDraftPresenter<
        LoadableItemState<ITEM, ITEM_MODEL, FEEDBACK>
        >(), CoroutineScope
        where ITEM_MODEL : ItemModel<ITEM, FEEDBACK> {

    protected abstract val adapter: LoadableItemAdapter<ITEM, ITEM_MODEL, FEEDBACK>

    suspend fun load() {
        update { old -> old.copy(LoadableModel.Loading()) }
        performLoad()
            .onSuccess { loaded -> update { old -> old.copy(LoadableModel.Loaded(adapter.itemToModelAdapter(loaded))) } }
            .onFailure { failed -> update { old -> old.copy(LoadableModel.LoadFailed(failed)) } }
    }

    protected abstract suspend fun performLoad(): Result<ITEM>

    suspend fun awaitModelLoadedOrFailed(): LoadableModel<ITEM, ITEM_MODEL, FEEDBACK> {
        return stateFlow
            .map { it.loadable }
            .first {
                when (it) {
                    is LoadableModel.Loaded, is LoadableModel.LoadFailed -> true
                    else -> false
                }
            }
    }

    suspend fun awaitModelLoadedOrThrow(): LoadableModel.Loaded<ITEM, ITEM_MODEL, FEEDBACK> {
        return stateFlow
            .map { it.loadable }
            .first {
                when (it) {
                    is LoadableModel.Loaded -> true
                    is LoadableModel.LoadFailed -> throw it.cause
                    else -> false
                }
            } as LoadableModel.Loaded<ITEM, ITEM_MODEL, FEEDBACK>
    }
}