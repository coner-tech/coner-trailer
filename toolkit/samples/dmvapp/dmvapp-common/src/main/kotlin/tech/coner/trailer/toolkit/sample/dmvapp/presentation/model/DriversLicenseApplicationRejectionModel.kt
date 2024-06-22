package tech.coner.trailer.toolkit.sample.dmvapp.presentation.model

import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.Sass
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback
import tech.coner.trailer.toolkit.validation.ValidationOutcome

sealed interface DriversLicenseApplicationRejectionModel {

    data class Invalid(
        val validationOutcome: ValidationOutcome<DriversLicenseApplicationModel, DriversLicenseApplicationModelFeedback>,
    ) : DriversLicenseApplicationRejectionModel

    data class Sassed(
        val sass: Sass
    ) : DriversLicenseApplicationRejectionModel

    data object LegallyProhibited : DriversLicenseApplicationRejectionModel
}