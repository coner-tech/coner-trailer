package tech.coner.trailer.toolkit.sample.dmvapp.presentation.model

import tech.coner.trailer.toolkit.presentation.model.BaseItemModel
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback
import tech.coner.trailer.toolkit.validation.Validator

class DriversLicenseApplicationItemModel(
    override val validator: Validator<Unit, DriversLicenseApplicationModel, DriversLicenseApplicationModelFeedback>,
) : BaseItemModel<DriversLicenseApplicationModel, Unit, DriversLicenseApplicationModelFeedback>() {

    override val initialItem: DriversLicenseApplicationModel = DriversLicenseApplicationModel()
    override val validatorContext = Unit

}
