package tech.coner.trailer.toolkit.sample.dmvapp.domain.entity

import tech.coner.trailer.toolkit.sample.dmvapp.domain.validation.DriversLicenseApplicationFeedback
import tech.coner.trailer.toolkit.validation.ValidationOutcome

sealed interface DriversLicenseApplicationRejection {

    data class Invalid(
        val validationOutcome: ValidationOutcome<DriversLicenseApplication, DriversLicenseApplicationFeedback>
    ) : DriversLicenseApplicationRejection

    data class Sassed(
        val sass: Sass
    ) : DriversLicenseApplicationRejection

    data object LegallyProhibited : DriversLicenseApplicationRejection
}