package tech.coner.trailer.toolkit.presentation.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.ValidationResult

interface ItemModel<ITEM, FEEDBACK : Feedback> : Model {
    val itemFlow: StateFlow<ITEM>
    val item: ITEM

    val pendingItemFlow: StateFlow<ITEM>
    var pendingItem: ITEM

    val isPendingItemDirty: Boolean

    val pendingItemValidationFlow: Flow<ValidationResult<ITEM, FEEDBACK>>
    val pendingItemValidation: ValidationResult<ITEM, FEEDBACK>

    val isPendingItemValid: Boolean

    fun mutatePendingItem(forceValidate: Boolean? = null, mutatePendingItemFn: (ITEM) -> ITEM)

    fun validate(): ValidationResult<ITEM, FEEDBACK>

    fun commit(requireValid: Boolean = true): CommitOutcome<ITEM, FEEDBACK>
}