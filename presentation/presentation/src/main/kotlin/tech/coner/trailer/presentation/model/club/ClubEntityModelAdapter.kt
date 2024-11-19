package tech.coner.trailer.presentation.model.club

import tech.coner.trailer.domain.entity.Club
import tech.coner.trailer.toolkit.presentation.adapter.EntityModelAdapter

class ClubEntityModelAdapter : EntityModelAdapter<Club, ClubDetailModel>() {
    override val entityToModelAdapter: (Club) -> ClubDetailModel = {
        ClubDetailModel(
            name = it.name
        )
    }
    override val modelToEntityAdapter: (ClubDetailModel) -> Club = {
        Club(
            name = it.name
        )
    }
}