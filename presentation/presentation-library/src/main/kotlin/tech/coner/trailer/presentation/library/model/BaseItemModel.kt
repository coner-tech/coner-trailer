package tech.coner.trailer.presentation.library.model

import kotlin.reflect.KProperty1
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import tech.coner.trailer.toolkit.konstraints.CompositeConstraint

abstract class BaseItemModel<I, C : CompositeConstraint<I>> : ItemModel<I> {
    abstract val constraints: C

    override val validatedItemFlow by lazy { MutableStateFlow(Validated(original, emptyList())) }
    override val itemValueFlow by lazy { validatedItemFlow.map { it.value } }
    override var itemValue: I
        get() = validatedItemFlow.value.value
        set(value) {
            update { value }
        }

    override fun update(fn: (I) -> I) {
        validatedItemFlow.update { old ->
            val newValue = fn(old.value)
            Validated(
                newValue,
                constraints.all
                    .mapNotNull { constraint -> constraint(newValue).exceptionOrNull()?.message }
                    .map { Violation(it) }
            )
        }
    }

    override fun <P> validatedPropertyFlow(property: KProperty1<I, *>, fn: (I) -> P): Flow<Validated<P>> {
        val constraints = constraints.propertyConstraints[property]
            ?: throw Exception("propertyConstraints does not contain any constraints for property: $property")
        return validatedItemFlow.map { item ->
            Validated(
                fn(item.value),
                constraints
                    .mapNotNull { constraint -> constraint(item.value).exceptionOrNull()?.message }
                    .map { Violation(it) }
            )
        }
    }

    override val isValid
        get() = validatedItemFlow.value.isValid

    override val isDirty
        get() = original != itemValue
}