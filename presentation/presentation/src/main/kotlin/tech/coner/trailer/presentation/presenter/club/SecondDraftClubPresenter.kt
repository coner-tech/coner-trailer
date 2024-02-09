package tech.coner.trailer.presentation.presenter.club

import tech.coner.trailer.Club
import tech.coner.trailer.presentation.library.adapter.LoadableItemAdapter
import tech.coner.trailer.presentation.library.presenter.LoadableItemPresenter
import tech.coner.trailer.presentation.model.ClubModel
import tech.coner.trailer.presentation.state.ClubPresenterState

class SecondDraftClubPresenter(
    override val initialState: ClubPresenterState,
    override val adapter: tech.coner.trailer.presentation.library.adapter.LoadableItemAdapter<Club, ClubModel>
) : LoadableItemPresenter<ClubPresenterState, Club, ClubModel>() {
}