package tech.coner.trailer.presentation.library.state

interface LoadableItemState<I> : State {
    val loadable: LoadableItem<I>
}