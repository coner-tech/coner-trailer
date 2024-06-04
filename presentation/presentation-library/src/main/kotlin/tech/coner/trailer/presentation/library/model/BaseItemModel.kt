package tech.coner.trailer.presentation.library.model

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import tech.coner.trailer.toolkit.konstraints.CompositeConstraint
import tech.coner.trailer.toolkit.konstraints.ConstraintViolationException
import kotlin.reflect.KProperty1

abstract class BaseItemModel<I, C : CompositeConstraint<I>> : ItemModel<I> {
    abstract val constraints: C

    abstract val initialItem: I
    private val _itemFlow by lazy { MutableStateFlow(initialItem) }
    final override val itemFlow by lazy { _itemFlow.asStateFlow() }
    final override val item: I
        get() = _itemFlow.value

    private val _pendingItemFlow by lazy { MutableStateFlow(initialItem) }
    final override val pendingItemFlow by lazy { _pendingItemFlow.asStateFlow() }
    final override var pendingItem: I
        get() = pendingItemFlow.value
        set(value) = runBlocking { mutatePendingItem { value } }
    private val _pendingItemValidationFlow by lazy { MutableStateFlow(emptyList<ValidationContent>()) }
    final override val pendingItemValidationFlow by lazy { _pendingItemValidationFlow.asStateFlow() }
    final override val pendingItemValidation get() = _pendingItemValidationFlow.value

    final override suspend fun mutatePendingItem(forceValidate: Boolean?, mutatePendingItemFn: (I) -> I) {
        _pendingItemFlow.update { pending -> mutatePendingItemFn(pending) }
        if (forceValidate == true) {
            validate()
        }
    }

    override fun <P> validatedPropertyFlow(property: KProperty1<I, *>, fn: (I) -> P): Flow<Validated<P>> {
        val constraints = constraints.propertyConstraints[property]
            ?: throw Exception("propertyConstraints does not contain any constraints for property: $property")
        return pendingItemFlow.map { item ->
            Validated(
                fn(item),
                constraints
                    .mapNotNull { constraint -> constraint(item).exceptionOrNull() as ConstraintViolationException? }
                    .map { Violation(it) }
            )
        }
    }

    final override val isPendingItemValid
        get() = _pendingItemValidationFlow.value.isValid()

    final override val isPendingItemDirty
        get() = item != pendingItem

    final override suspend fun validate(): List<ValidationContent> {
        return constraints.all
            .mapNotNull { it.invoke(pendingItem).exceptionOrNull() as? ConstraintViolationException }
            .map { ValidationContent.Error(it.message ?: "Invalid" /* TODO not hard-code english string */) }
            .also { _pendingItemValidationFlow.emit(it) }
    }

    override suspend fun commit(forceValidate: Boolean): Result<I> {
        if (forceValidate) {
            validate()
                .also {
                    if (!it.isValid()) {
                        return Result.failure(ModelNotReadyToCommitException())
                    }
                }
        }
        return pendingItem
            .also { _itemFlow.emit(it) }
            .let { Result.success(it) }
    }
}