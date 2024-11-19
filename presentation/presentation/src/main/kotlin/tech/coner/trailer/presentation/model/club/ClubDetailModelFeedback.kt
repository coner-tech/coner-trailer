package tech.coner.trailer.presentation.model.club

import tech.coner.trailer.domain.entity.Club
import tech.coner.trailer.domain.validation.ClubFeedback
import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.FeedbackDelegate
import tech.coner.trailer.toolkit.validation.adapter.propertyAdapterOf

data class ClubDetailModelFeedback(
    val source: ClubFeedback
) : Feedback<ClubDetailModel> by FeedbackDelegate(
    feedback = source,
    propertyAdapter = propertyAdapterOf(
        Club::name to ClubDetailModel::name
    )
)
