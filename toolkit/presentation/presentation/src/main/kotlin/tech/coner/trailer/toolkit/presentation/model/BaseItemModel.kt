package tech.coner.trailer.toolkit.presentation.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.ValidationResult
import tech.coner.trailer.toolkit.validation.Validator

abstract class BaseItemModel<ITEM, VALIDATOR_CONTEXT, VALIDATOR_FEEDBACK>
    : ItemModel<ITEM, VALIDATOR_FEEDBACK>
    where VALIDATOR_FEEDBACK : Feedback {

    abstract val validator: Validator<VALIDATOR_CONTEXT, ITEM, VALIDATOR_FEEDBACK>
    abstract val validatorContext: VALIDATOR_CONTEXT

    abstract val initialItem: ITEM
    private val _itemFlow by lazy { MutableStateFlow(initialItem) }
    final override val itemFlow by lazy { _itemFlow.asStateFlow() }
    final override val item: ITEM
        get() = _itemFlow.value

    private val _pendingItemFlow by lazy { MutableStateFlow(initialItem) }
    final override val pendingItemFlow by lazy { _pendingItemFlow.asStateFlow() }
    final override var pendingItem: ITEM
        get() = pendingItemFlow.value
        set(value) = mutatePendingItem { value }

    private val _pendingItemValidationFlow by lazy {
        MutableStateFlow<ValidationResult<ITEM, VALIDATOR_FEEDBACK>>(
            ValidationResult(emptyMap())
        )
    }
    final override val pendingItemValidationFlow by lazy { _pendingItemValidationFlow.asStateFlow() }
    final override val pendingItemValidation get() = _pendingItemValidationFlow.value

    final override fun mutatePendingItem(forceValidate: Boolean?, mutatePendingItemFn: (ITEM) -> ITEM) {
        _pendingItemFlow.update { pending -> mutatePendingItemFn(pending) }
        if (forceValidate == true) {
            validate()
        }
    }

    final override val isPendingItemValid
        get() = _pendingItemValidationFlow.value.isValid

    final override val isPendingItemDirty
        get() = item != pendingItem

    final override fun validate(): ValidationResult<ITEM, VALIDATOR_FEEDBACK> {
        return validator(validatorContext, pendingItem)
            .also { _pendingItemValidationFlow.value = it }
    }

    override fun commit(requireValid: Boolean): CommitOutcome<ITEM, VALIDATOR_FEEDBACK> {
        val validationResult = validate()
        if (requireValid) {
            if (!validationResult.isValid) {
                return CommitOutcome.Failure(pendingItem, validationResult)
            }
        }
        return pendingItem
            .also { _itemFlow.value = it }
            .let { CommitOutcome.Success(it, validationResult) }
    }
}