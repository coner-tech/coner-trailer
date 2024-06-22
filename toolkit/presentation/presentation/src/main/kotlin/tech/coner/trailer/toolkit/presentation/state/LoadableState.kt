package tech.coner.trailer.toolkit.presentation.state

import kotlinx.coroutines.flow.*
import tech.coner.trailer.toolkit.presentation.model.ItemModel
import tech.coner.trailer.toolkit.presentation.model.Loadable
import tech.coner.trailer.toolkit.presentation.model.letLoadedSuccess
import tech.coner.trailer.toolkit.presentation.model.whenLoadedSuccess
import tech.coner.trailer.toolkit.validation.Feedback
import kotlin.reflect.KProperty1

interface LoadableState<FAILURE, ITEM, ITEM_MODEL : ItemModel<ITEM, FEEDBACK>, FEEDBACK : Feedback<ITEM>>: State {
    val loadable: Loadable<FAILURE, ITEM, ITEM_MODEL, FEEDBACK>
}

class MutableLoadedPropertyDelegate
    <STATE : LoadableState<FAILURE, ITEM, ITEM_MODEL, FEEDBACK>, FAILURE, ITEM, ITEM_MODEL : ItemModel<ITEM, FEEDBACK>, FEEDBACK : Feedback<ITEM>, PROPERTY>(
    private val stateContainer: StateContainer<STATE>,
    private val getFn: ITEM.() -> PROPERTY,
    private val updateFn: ITEM.(PROPERTY) -> ITEM,
    private val property: KProperty1<ITEM, PROPERTY>
) {
    var value: PROPERTY
        get() = stateContainer.state.loadable.letLoadedSuccess { it.pendingItem?.let(getFn) }
            // let's see if this causes race conditions in the sample app
            // might need to add an extra property for null->default value
            ?: throw IllegalStateException("Accessing successfully loaded state but not successfully loaded")
        set(value) {
            stateContainer.state.loadable.whenLoadedSuccess { loaded ->
                loaded.updatePendingItem { updateFn(it, value) }
            }
        }
    val valueFlow: Flow<PROPERTY?> = stateContainer.stateFlow
        .map { if (it.loadable is Loadable.Loaded) it.loadable as Loadable.Loaded else null }
        .flatMapLatest { it?.either?.getOrNull()?.pendingItemFlow ?: flowOf(null) }
        .map { it?.let(getFn) }

    val feedback: List<FEEDBACK>
        get() = stateContainer.state.loadable.letLoadedSuccess { it.pendingItemValidation.feedbackByProperty[property] }
            ?: emptyList()
    val feedbackFlow: Flow<List<FEEDBACK>> = stateContainer.stateFlow
        .map { if (it.loadable is Loadable.Loaded) it.loadable as Loadable.Loaded else null }
        .flatMapLatest {
            it?.letLoadedSuccess { itemModel -> flowOf(itemModel.pendingItemValidation.feedbackByProperty[property]) }
                ?: flowOf(emptyList())
        }
        .map { it ?: emptyList() }
}

fun <STATE : LoadableState<FAILURE, ITEM, ITEM_MODEL, FEEDBACK>, FAILURE, ITEM, ITEM_MODEL : ItemModel<ITEM, FEEDBACK>, FEEDBACK : Feedback<ITEM>, PROPERTY>
        StateContainer<STATE>.mutableLoadedProperty(
    getFn: ITEM.() -> PROPERTY,
    updateFn: ITEM.(PROPERTY) -> ITEM,
    property: KProperty1<ITEM, PROPERTY>
): MutableLoadedPropertyDelegate<STATE, FAILURE, ITEM, ITEM_MODEL, FEEDBACK, PROPERTY> {
    return MutableLoadedPropertyDelegate(this, getFn, updateFn, property)
}
