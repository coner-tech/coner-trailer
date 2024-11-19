package tech.coner.trailer.presentation.state.club

import tech.coner.trailer.io.service.ClubService
import tech.coner.trailer.presentation.model.club.ClubDetailItemModel
import tech.coner.trailer.presentation.model.club.ClubDetailModel
import tech.coner.trailer.presentation.model.club.ClubDetailModelFeedback
import tech.coner.trailer.toolkit.presentation.model.Loadable
import tech.coner.trailer.toolkit.presentation.state.LoadableState

data class ClubDetailState(
    override val loadable: Loadable<ClubService.GetFailure, ClubDetailModel, ClubDetailItemModel, ClubDetailModelFeedback> = Loadable.Empty()
) : LoadableState<ClubService.GetFailure, ClubDetailModel, ClubDetailItemModel, ClubDetailModelFeedback>
