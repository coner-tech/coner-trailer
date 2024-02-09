package tech.coner.trailer.presentation.state

import tech.coner.trailer.Club
import tech.coner.trailer.presentation.library.state.LoadableItem
import tech.coner.trailer.presentation.library.state.LoadableItemState

data class ClubPresenterState(
    override val loadable: LoadableItem<Club>
) : LoadableItemState<Club> {
}