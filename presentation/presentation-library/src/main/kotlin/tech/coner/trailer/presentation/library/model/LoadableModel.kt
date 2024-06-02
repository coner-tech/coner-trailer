package tech.coner.trailer.presentation.library.model

sealed class LoadableModel<ITEM, ARGUMENT_MODEL, ITEM_MODEL : ItemModel<ITEM>>
    : Model {

    /**
     * Model represents presenter arguments
     */
    abstract val argument: ARGUMENT_MODEL?

    /**
     * Initial model corresponding to the presenter having initial state,
     * prior to starting to load anything, or if it was fully reset.
     */
    data class Empty<ITEM, ARGUMENT_MODEL, ITEM_MODEL : ItemModel<ITEM>>(
        override val argument: ARGUMENT_MODEL?
    ) : LoadableModel<ITEM, ARGUMENT_MODEL, ITEM_MODEL>()

    /**
     * Model indicates the item is loading.
     *
     * @property partial Partial model representing incomplete loading
     * state. For long or complex load operations, use to convey details
     * of loading in progress. Adapters may optionally specify this value.
     * It may be helpful to implement the partial model with a different
     * type than the loaded item.
     */
    data class Loading<ITEM, ARGUMENT_MODEL, ITEM_MODEL : ItemModel<ITEM>>(
        override val argument: ARGUMENT_MODEL?,
        val partial: ITEM_MODEL? = null
    ) : LoadableModel<ITEM, ARGUMENT_MODEL, ITEM_MODEL>()

    /**
     * Model indicates the item has loaded.
     *
     * @property item the item resulting from the load operation.
     */
    data class Loaded<ITEM, ARGUMENT_MODEL, ITEM_MODEL : ItemModel<ITEM>>(
        override val argument: ARGUMENT_MODEL?,
        val item: ITEM_MODEL
    ) : LoadableModel<ITEM, ARGUMENT_MODEL, ITEM_MODEL>()

    /**
     * Model indicates the load operation failed.
     *
     * @property cause the cause of the failure, if known
     */
    data class LoadFailed<ITEM, ARGUMENT_MODEL, ITEM_MODEL : ItemModel<ITEM>>(
        override val argument: ARGUMENT_MODEL?,
        val cause: Throwable?
    ) : LoadableModel<ITEM, ARGUMENT_MODEL, ITEM_MODEL>()

}