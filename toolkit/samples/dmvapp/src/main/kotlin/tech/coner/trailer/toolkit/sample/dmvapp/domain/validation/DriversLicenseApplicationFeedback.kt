package tech.coner.trailer.toolkit.sample.dmvapp.domain.validation

import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicenseApplication.LicenseType
import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.Severity

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