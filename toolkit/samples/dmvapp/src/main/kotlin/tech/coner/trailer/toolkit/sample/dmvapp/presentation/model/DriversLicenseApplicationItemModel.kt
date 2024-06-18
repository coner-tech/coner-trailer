package tech.coner.trailer.toolkit.sample.dmvapp.presentation.model

import kotlinx.coroutines.flow.map
import tech.coner.trailer.toolkit.presentation.model.BaseItemModel
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback
import tech.coner.trailer.toolkit.validation.Validator

class DriversLicenseApplicationItemModel(
    override val validator: Validator<Unit, DriversLicenseApplicationModel, DriversLicenseApplicationModelFeedback>,
) : BaseItemModel<DriversLicenseApplicationModel, Unit, DriversLicenseApplicationModelFeedback>() {

    override val initialItem: DriversLicenseApplicationModel = DriversLicenseApplicationModel()
    override val validatorContext = Unit

    var name: String?
        get() = pendingItem.name
        set(value) = mutatePendingItem { it.copy(name = value) }
    val nameFlow = pendingItemFlow.map { it.name }

    var age: Int?
        get() = pendingItem.age
        set(value) = mutatePendingItem { it.copy(age = value) }
    val ageFlow = pendingItemFlow.map { it.age }

    var licenseType: LicenseType?
        get() = pendingItem.licenseType
        set(value) = mutatePendingItem { it.copy(licenseType = value) }
    val licenseTypeFlow = pendingItemFlow.map { it.licenseType }

}
