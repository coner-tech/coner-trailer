package tech.coner.trailer.presentation.model.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import tech.coner.trailer.io.constraint.CompositeConstraint
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.model.Model
import kotlin.reflect.KProperty1

abstract class ItemModel<I, C : CompositeConstraint<I>, A : Adapter<I, *>> : Model {
    abstract val original: I
    abstract val constraints: C
    abstract val adapter: A

    val validatedItemFlow by lazy { MutableStateFlow(Validated(original, emptyList())) }
    val itemValueFlow by lazy { validatedItemFlow.map { it.value } }
    var itemValue: I
        get() = validatedItemFlow.value.value
        protected set(value) {
            updateItem { value }
        }

    protected fun updateItem(fn: (I) -> I) {
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

    protected fun <P> validatedPropertyFlow(property: KProperty1<I, *>, fn: (I) -> P): Flow<Validated<P>> {
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

    val isValid
        get() = validatedItemFlow.value.isValid

    val isDirty
        get() = original == itemValue
}