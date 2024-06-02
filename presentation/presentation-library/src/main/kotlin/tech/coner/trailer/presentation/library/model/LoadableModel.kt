package tech.coner.trailer.presentation.library.model

sealed class LoadableModel<ITEM, ITEM_MODEL : ItemModel<ITEM>>
    : Model {

    /**
     * Initial model corresponding to the presenter having initial state,
     * prior to starting to load anything, or if it was fully reset.
     */
    class Empty<ITEM, ITEM_MODEL : ItemModel<ITEM>> : LoadableModel<ITEM, ITEM_MODEL>() {
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
    data class Loading<ITEM, ITEM_MODEL : ItemModel<ITEM>>(
        val partial: ITEM_MODEL? = null
    ) : LoadableModel<ITEM, ITEM_MODEL>()

    /**
     * Model indicates the item has loaded.
     *
     * @property item the item resulting from the load operation.
     */
    data class Loaded<ITEM, ITEM_MODEL : ItemModel<ITEM>>(
        val item: ITEM_MODEL
    ) : LoadableModel<ITEM, ITEM_MODEL>()

    /**
     * Model indicates the load operation failed.
     *
     * @property cause the cause of the failure, if known
     */
    data class LoadFailed<ITEM, ITEM_MODEL : ItemModel<ITEM>>(
        val cause: Throwable?
    ) : LoadableModel<ITEM, ITEM_MODEL>()

}