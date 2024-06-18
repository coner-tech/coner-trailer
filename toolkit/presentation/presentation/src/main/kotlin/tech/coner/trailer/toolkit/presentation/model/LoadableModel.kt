package tech.coner.trailer.toolkit.presentation.model

import tech.coner.trailer.toolkit.validation.Feedback

sealed class LoadableModel<ITEM, ITEM_MODEL : ItemModel<ITEM, FEEDBACK>, FEEDBACK : Feedback>
    : Model {

    /**
     * Initial model corresponding to the presenter having initial state,
     * prior to starting to load anything, or if it was fully reset.
     */
    class Empty<ITEM, ITEM_MODEL : ItemModel<ITEM, FEEDBACK>, FEEDBACK : Feedback> : LoadableModel<ITEM, ITEM_MODEL, FEEDBACK>() {
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
    data class Loading<ITEM, ITEM_MODEL : ItemModel<ITEM, FEEDBACK>, FEEDBACK : Feedback>(
        val partial: ITEM_MODEL? = null
    ) : LoadableModel<ITEM, ITEM_MODEL, FEEDBACK>()

    /**
     * Model indicates the item has loaded.
     *
     * @property item the item resulting from the load operation.
     */
    data class Loaded<ITEM, ITEM_MODEL : ItemModel<ITEM, FEEDBACK>, FEEDBACK : Feedback>(
        val item: ITEM_MODEL
    ) : LoadableModel<ITEM, ITEM_MODEL, FEEDBACK>()

    /**
     * Model indicates the load operation failed.
     *
     * @property cause the cause of the failure, if known
     */
    data class LoadFailed<ITEM, ITEM_MODEL : ItemModel<ITEM, FEEDBACK>, FEEDBACK : Feedback>(
        val cause: Throwable
    ) : LoadableModel<ITEM, ITEM_MODEL, FEEDBACK>()

}