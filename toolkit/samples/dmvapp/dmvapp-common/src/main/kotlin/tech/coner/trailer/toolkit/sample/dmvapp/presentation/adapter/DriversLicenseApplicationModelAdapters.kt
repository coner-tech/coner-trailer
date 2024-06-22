package tech.coner.trailer.toolkit.sample.dmvapp.presentation.adapter

import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicenseApplication
import tech.coner.trailer.toolkit.sample.dmvapp.domain.validation.DriversLicenseApplicationFeedback
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.model.DriversLicenseApplicationModel
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback.DelegatedFeedback
import tech.coner.trailer.toolkit.validation.ValidationOutcome
import tech.coner.trailer.toolkit.validation.adapter.map


class DriversLicenseApplicationModelAdapters {

    /**
     * Adapter function converts presentation model to domain entity
     *
     * The conversion can fail if the presentation model is invalid. Validate first.
     */
    fun toDomainEntity(model: DriversLicenseApplicationModel): Result<DriversLicenseApplication> = runCatching {
        DriversLicenseApplication(
            name = model.name!!,
            age = model.age!!,
            licenseType = model.licenseType!!
        )
    }

    val domainEntityValidationAdapter: (ValidationOutcome<DriversLicenseApplication, DriversLicenseApplicationFeedback>) -> ValidationOutcome<DriversLicenseApplicationModel, DriversLicenseApplicationModelFeedback> = {
        it.map { feedback -> DelegatedFeedback(feedback) }
    }
}