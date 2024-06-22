package tech.coner.trailer.toolkit.presentation.model

import arrow.core.Either
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.ValidationOutcome

interface ItemModel<ITEM, FEEDBACK : Feedback<ITEM>> : Model {
    val item: ITEM
    val itemFlow: StateFlow<ITEM>

    var pendingItem: ITEM
    val pendingItemFlow: StateFlow<ITEM>

    val isPendingItemDirty: Boolean
    val isPendingItemDirtyFlow: Flow<Boolean>

    val pendingItemValidation: ValidationOutcome<ITEM, FEEDBACK>
    val pendingItemValidationFlow: StateFlow<ValidationOutcome<ITEM, FEEDBACK>>

    val isPendingItemValid: Boolean
    val isPendingItemValidFlow: Flow<Boolean>

    val canReset: Boolean
    val canResetFlow: Flow<Boolean>

    fun updatePendingItem(forceValidate: Boolean? = null, updateFn: (ITEM) -> ITEM)

    fun validate(): ValidationOutcome<ITEM, FEEDBACK>

    fun commit(requireValid: Boolean = true): Either<ValidationOutcome<ITEM, FEEDBACK>, ITEM>

    fun reset()
}