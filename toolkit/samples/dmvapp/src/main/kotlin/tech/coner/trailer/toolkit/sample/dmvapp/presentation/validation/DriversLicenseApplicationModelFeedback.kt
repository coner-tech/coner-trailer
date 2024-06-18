package tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation

import tech.coner.trailer.toolkit.sample.dmvapp.domain.validation.DriversLicenseApplicationFeedback
import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.Severity
import tech.coner.trailer.toolkit.validation.Severity.Error

sealed class DriversLicenseApplicationModelFeedback : Feedback {

    data object NameIsRequired : DriversLicenseApplicationModelFeedback() {
        override val severity = Error
    }
    data object AgeIsRequired : DriversLicenseApplicationModelFeedback() {
        override val severity = Error
    }
    data object LicenseTypeIsRequired : DriversLicenseApplicationModelFeedback() {
        override val severity = Error
    }

    data class DelegatedFeedback(
        val feedback: DriversLicenseApplicationFeedback
    ) : DriversLicenseApplicationModelFeedback() {
        override val severity: Severity
            get() = feedback.severity
    }
}
