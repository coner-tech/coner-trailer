package tech.coner.trailer.toolkit.presentation.model

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.ValidationOutcome
import tech.coner.trailer.toolkit.validation.Validator

abstract class BaseItemModel<ITEM, VALIDATOR_CONTEXT, VALIDATOR_FEEDBACK>
    : ItemModel<ITEM, VALIDATOR_FEEDBACK>
    where VALIDATOR_FEEDBACK : Feedback<ITEM> {

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
        set(value) = updatePendingItem { value }

    private val _pendingItemValidationFlow by lazy {
        MutableStateFlow<ValidationOutcome<ITEM, VALIDATOR_FEEDBACK>>(ValidationOutcome(emptyList()))
    }
    final override val pendingItemValidationFlow: StateFlow<ValidationOutcome<ITEM, VALIDATOR_FEEDBACK>> by lazy {
        _pendingItemValidationFlow.asStateFlow()
    }
    final override val pendingItemValidation get() = _pendingItemValidationFlow.value

    final override fun updatePendingItem(forceValidate: Boolean?, updateFn: (ITEM) -> ITEM) {
        val pendingItemHadFeedback = _pendingItemValidationFlow.value.feedback.isNotEmpty()
        _pendingItemFlow.update { pending ->
            updateFn(pending)
        }
        if (forceValidate == true || pendingItemHadFeedback) {
            validate()
        }
    }

    final override val isPendingItemValid
        get() = _pendingItemValidationFlow.value.isValid
    final override val isPendingItemValidFlow by lazy {
        _pendingItemValidationFlow.map { it.isValid }
    }

    final override val isPendingItemDirty
        get() = item != pendingItem
    final override val isPendingItemDirtyFlow: Flow<Boolean> by lazy {
        pendingItemFlow
            .map { item != it }
    }

    override val canReset: Boolean get() = pendingItem != item || _pendingItemValidationFlow.value.feedback.isNotEmpty()
    override val canResetFlow: Flow<Boolean> by lazy {
        combine(
            _pendingItemFlow,
            _pendingItemValidationFlow,
        ) { pendingItem, pendingItemValidation ->
            pendingItem != item || pendingItemValidation.feedback.isNotEmpty() || item != initialItem
        }
    }

    final override fun validate(): ValidationOutcome<ITEM, VALIDATOR_FEEDBACK> {
        return validator(validatorContext, pendingItem)
            .also { _pendingItemValidationFlow.value = it }
    }



    override fun commit(requireValid: Boolean): Either<ValidationOutcome<ITEM, VALIDATOR_FEEDBACK>, ITEM> = either {
        val validationResult = validate()
        if (requireValid) {
            ensure(validationResult.isValid) {
                validationResult
            }
        }
        pendingItem
            .also { _itemFlow.value = it }
    }

    override fun reset() {
        _itemFlow.value = initialItem
        _pendingItemFlow.value = initialItem
        _pendingItemValidationFlow.value = ValidationOutcome(emptyList())
    }
}
