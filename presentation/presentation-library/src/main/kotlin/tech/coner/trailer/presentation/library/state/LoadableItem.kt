package tech.coner.trailer.presentation.library.state

sealed class LoadableItem<ITEM> {

    /**
     * The initial state of the presenter
     */
    class Empty<ITEM> : LoadableItem<ITEM>() {
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
     * Presenter is loading an item.
     *
     * @property prior the item that was previously loaded, if any.
     */
    data class Loading<ITEM>(
        val prior: ITEM? = null
    ) : LoadableItem<ITEM>()

    /**
     * Presenter has loaded the item.
     *
     * @property item the item resulting from the load operation.
     */
    data class Loaded<ITEM>(val item: ITEM) : LoadableItem<ITEM>()

    /**
     * Presenter failed to load the item.
     *
     * @property priorItem the item that was loaded previously, prior to the load attempt which failed, if any
     * @property cause the cause of the failure, if known
     */
    data class LoadFailed<I>(
        val priorItem: I?,
        val cause: Throwable?
    ) : LoadableItem<I>()

}

fun <I, R> LoadableItem<I>?.whenLoading(fn: (LoadableItem.Loading<I>) -> R): R? {
    return when (this) {
        is LoadableItem.Loading<I> -> fn(this)
        else -> { null }
    }
}

fun <I, R> LoadableItem<I>?.whenLoaded(fn: (LoadableItem.Loaded<I>) -> R): R? {
    return when (this) {
        is LoadableItem.Loaded<I> -> fn(this)
        else -> { null }
    }
}

fun <I, R> LoadableItem<I>?.whenLoadFailed(fn: (LoadableItem.LoadFailed<I>) -> R): R? {
    return when (this) {
        is LoadableItem.LoadFailed<I> -> fn(this)
        else -> { null }
    }
}