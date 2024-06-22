package tech.coner.trailer.toolkit.presentation.model

import arrow.core.Either
import tech.coner.trailer.toolkit.validation.Feedback

sealed interface Loadable<FAILURE, ITEM, ITEM_MODEL : ItemModel<ITEM, FEEDBACK>, FEEDBACK : Feedback<ITEM>>
    : Model {

    /**
     * Initial model corresponding to the presenter having initial state,
     * prior to starting to load anything, or if it was fully reset.
     */
    class Empty<FAILURE, ITEM, ITEM_MODEL : ItemModel<ITEM, FEEDBACK>, FEEDBACK : Feedback<ITEM>>
        : Loadable<FAILURE, ITEM, ITEM_MODEL, FEEDBACK> {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            return true
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }

    /**
     * Model indicates the item is loading.
     *
     * @property partial Partial model representing incomplete loading
     * state. For long or complex load operations, use to convey details
     * of loading in progress. Adapters may optionally specify this value.
     * It may be helpful to implement the partial model with a different
     * type than the loaded item.
     */
    data class Loading<FAILURE, ITEM, ITEM_MODEL : ItemModel<ITEM, FEEDBACK>, FEEDBACK : Feedback<ITEM>>(
        val partial: ITEM_MODEL? = null
    ) : Loadable<FAILURE, ITEM, ITEM_MODEL, FEEDBACK>

    /**
     * Indicates a load operation completed, either in failure or success.
     */
    data class Loaded<FAILURE, ITEM, ITEM_MODEL : ItemModel<ITEM, FEEDBACK>, FEEDBACK : Feedback<ITEM>>(
        val either: Either<FAILURE, ITEM_MODEL>
    )
        : Loadable<FAILURE, ITEM, ITEM_MODEL, FEEDBACK>

    data class FailedExceptionally<FAILURE, ITEM, ITEM_MODEL : ItemModel<ITEM, FEEDBACK>, FEEDBACK : Feedback<ITEM>>(
        val throwable: Throwable
    )
        : Loadable<FAILURE, ITEM, ITEM_MODEL, FEEDBACK>
}

inline fun <R, FAILURE, ITEM, ITEM_MODEL : ItemModel<ITEM, FEEDBACK>, FEEDBACK : Feedback<ITEM>> Loadable<FAILURE, ITEM, ITEM_MODEL, FEEDBACK>.letLoadedSuccess(
    block: (ITEM_MODEL) -> R
): R? {
    return when (this) {
        is Loadable.Loaded<FAILURE, ITEM, ITEM_MODEL, FEEDBACK> -> either.getOrNull()?.let(block)
        is Loadable.Empty,
        is Loadable.Loading,
        is Loadable.FailedExceptionally -> null
    }
}

inline fun <FAILURE, ITEM, ITEM_MODEL : ItemModel<ITEM, FEEDBACK>, FEEDBACK : Feedback<ITEM>> Loadable<FAILURE, ITEM, ITEM_MODEL, FEEDBACK>.whenLoadedSuccess(
    block: (ITEM_MODEL) -> Unit
) {
    when (this) {
        is Loadable.Loaded<FAILURE, ITEM, ITEM_MODEL, FEEDBACK> -> either.getOrNull()?.also(block)
        is Loadable.Empty,
        is Loadable.Loading,
        is Loadable.FailedExceptionally -> { /* no-op */ }
    }
}