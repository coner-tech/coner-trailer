package tech.coner.trailer.presentation.library.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import tech.coner.trailer.presentation.library.adapter.Adapter
import kotlin.reflect.KProperty1

interface ItemModel<I> : Model {
    val original: I
    val validatedItemFlow: MutableStateFlow<Validated<I>>
    val itemValueFlow: Flow<I>
    var itemValue: I
    val isValid: Boolean
    val isDirty: Boolean

    fun updateItem(fn: (I) -> I)
    fun <P> validatedPropertyFlow(property: KProperty1<I, *>, fn: (I) -> P): Flow<Validated<P>>
}