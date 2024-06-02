package tech.coner.trailer.presentation.library.state

data class LoadableItemState<I>(
    val loadable: LoadableItem<I>
) : State
