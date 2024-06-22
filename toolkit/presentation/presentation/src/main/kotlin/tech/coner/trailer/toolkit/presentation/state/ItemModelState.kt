package tech.coner.trailer.toolkit.presentation.state

import kotlin.reflect.KProperty1
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import tech.coner.trailer.toolkit.presentation.model.ItemModel
import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.ValidationOutcome

interface ItemModelState<ITEM, FEEDBACK : Feedback<ITEM>> : State {
    val itemModel: ItemModel<ITEM, FEEDBACK>
}

class MutableItemModelPropertyDelegate
<STATE : ItemModelState<ITEM, FEEDBACK>, ITEM, FEEDBACK : Feedback<ITEM>, PROPERTY>(
    private val stateContainer: StateContainer<STATE>,
    private val getFn: ITEM.() -> PROPERTY,
    private val updateFn: ITEM.(PROPERTY) -> ITEM,
    private val property: KProperty1<ITEM, PROPERTY>
) {
    var value: PROPERTY
        get() = stateContainer.state.itemModel.pendingItem.let(getFn)
        set(value) {
            stateContainer.state.itemModel.updatePendingItem { updateFn(it, value) }
        }
    val valueFlow: Flow<PROPERTY?> = stateContainer.stateFlow
        .flatMapLatest { it.itemModel.pendingItemFlow }
        .map(getFn)

    val feedback: List<FEEDBACK>
        get() = stateContainer.state.itemModel.pendingItemValidation.feedbackByProperty[property]
            ?: emptyList()
    val feedbackFlow: Flow<List<FEEDBACK>> = stateContainer.stateFlow
        .flatMapLatest {
            flow {
                emit(ValidationOutcome(emptyList()))
                it.itemModel.pendingItemValidationFlow.collect(this)
            }
        }
        .map { it.feedbackByProperty[property] ?: emptyList() }
}

fun <STATE : ItemModelState<ITEM, FEEDBACK>, ITEM, FEEDBACK : Feedback<ITEM>, PROPERTY>
        StateContainer<STATE>.mutableItemModelProperty(
    getFn: ITEM.() -> PROPERTY,
    updateFn: ITEM.(PROPERTY) -> ITEM,
    property: KProperty1<ITEM, PROPERTY>
): MutableItemModelPropertyDelegate<STATE, ITEM, FEEDBACK, PROPERTY> {
    return MutableItemModelPropertyDelegate(this, getFn, updateFn, property)
}
