package tech.coner.trailer.presentation.library.model

import kotlin.reflect.KProperty1
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ItemModel<I> : Model {
    val itemFlow: StateFlow<I>
    val item: I
    val pendingItemFlow: StateFlow<I>
    var pendingItem: I
    val workingValidationViolationsFlow: Flow<List<Violation>>
    val isValid: Boolean
    val isDirty: Boolean

    fun update(forceValidate: Boolean = false, fn: (I) -> I)
    fun <P> validatedPropertyFlow(property: KProperty1<I, *>, fn: (I) -> P): Flow<Validated<P>>

    suspend fun validate(): List<Violation>

    suspend fun commit(forceValidate: Boolean = true): Result<I>
}