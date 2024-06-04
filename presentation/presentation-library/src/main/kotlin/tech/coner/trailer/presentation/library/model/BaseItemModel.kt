package tech.coner.trailer.presentation.library.model

import kotlin.reflect.KProperty1
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import tech.coner.trailer.toolkit.konstraints.CompositeConstraint
import tech.coner.trailer.toolkit.konstraints.ConstraintViolationException

abstract class BaseItemModel<I, C : CompositeConstraint<I>> : ItemModel<I>, CoroutineScope {
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
        set(value) {
            update { value }
        }
    private val _workingViolationsFlow by lazy { MutableStateFlow(emptyList<Violation>()) }
    final override val workingValidationViolationsFlow by lazy { _workingViolationsFlow.asStateFlow() }

    final override fun update(forceValidate: Boolean, fn: (I) -> I) {
        _pendingItemFlow.update { old -> fn(old) }
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

    final override val isValid
        get() = _workingViolationsFlow.value.isEmpty()

    final override val isDirty
        get() = item != pendingItem

    final override suspend fun validate(): List<Violation> {
        return constraints.all
            .mapNotNull { it.invoke(pendingItem).exceptionOrNull() as? ConstraintViolationException }
            .map { Violation(it) }
            .also { _workingViolationsFlow.emit(it) }
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

    private fun List<Violation>.isValid() = isEmpty()
}