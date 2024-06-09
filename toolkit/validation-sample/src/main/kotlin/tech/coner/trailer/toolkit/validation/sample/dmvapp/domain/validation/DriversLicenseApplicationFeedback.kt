package tech.coner.trailer.toolkit.validation.sample.dmvapp.domain.validation

import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.Severity
import tech.coner.trailer.toolkit.validation.sample.dmvapp.domain.entity.DriversLicenseApplication.LicenseType

sealed class DriversLicenseApplicationFeedback : Feedback {
    data class TooYoung(
        val suggestOtherLicenseType: LicenseType? = null,
        val reapplyWhenAge: Int? = null
    ) : DriversLicenseApplicationFeedback() {
        override val severity: Severity = Severity.Error
    }

    data class TooOld(
        val suggestOtherLicenseType: LicenseType?
    ) : DriversLicenseApplicationFeedback() {
        override val severity: Severity = Severity.Error
    }
}