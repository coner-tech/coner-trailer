package tech.coner.trailer.presentation.model.club

import tech.coner.trailer.domain.validation.ClubValidator
import tech.coner.trailer.toolkit.presentation.model.BaseItemModel
import tech.coner.trailer.toolkit.validation.Validator

class ClubDetailItemModel(
    override val initialItem: ClubDetailModel,
    private val adapter: ClubEntityModelAdapter = ClubEntityModelAdapter()
) : BaseItemModel<ClubDetailModel, Unit, ClubDetailModelFeedback>() {

    override val validator: Validator<Unit, ClubDetailModel, ClubDetailModelFeedback> = Validator {
        input(
            otherTypeValidator = ClubValidator(),
            mapContextFn = {},
            mapInputFn = { adapter.modelToEntityAdapter(it) },
            mapFeedbackObjectFn = { ClubDetailModelFeedback(it) }
        )
    }
    override val validatorContext = Unit
}