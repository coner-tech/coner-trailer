package tech.coner.trailer.toolkit.sample.dmvapp.presentation.state

import arrow.core.Either
import tech.coner.trailer.toolkit.presentation.state.ItemModelState
import tech.coner.trailer.toolkit.presentation.state.State
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicense
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.model.DriversLicenseApplicationItemModel
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.model.DriversLicenseApplicationModel
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.model.DriversLicenseApplicationRejectionModel
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback

data class DriversLicenseApplicationState(
    override val itemModel: DriversLicenseApplicationItemModel,
    val processing: Boolean = false,
    val outcome: Result<Either<DriversLicenseApplicationRejectionModel, DriversLicense>>? = null
) : State,
    ItemModelState<DriversLicenseApplicationModel, DriversLicenseApplicationModelFeedback> {

    val fieldsEditable: Boolean get() = !processing && (outcome == null || outcome.isFailure || outcome.getOrNull()?.isLeft() == true)

    val canApply: Boolean get() = !processing && (outcome == null || outcome.isFailure || outcome.getOrNull()?.isLeft() == true) && itemModel.pendingItemValidation.isValid
}
