package tech.coner.trailer.toolkit.sample.dmvapp.domain.validation

import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicenseApplication
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType
import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.Severity
import kotlin.reflect.KProperty1

sealed class DriversLicenseApplicationFeedback : Feedback<DriversLicenseApplication> {
    data object NameMustNotBeBlank : DriversLicenseApplicationFeedback() {
        override val property = DriversLicenseApplication::name
        override val severity = Severity.Error
    }
    data class TooYoung(
        val suggestOtherLicenseType: LicenseType? = null,
        val reapplyWhenAge: Int? = null
    ) : DriversLicenseApplicationFeedback() {
        override val property = DriversLicenseApplication::age
        override val severity = Severity.Error
    }

    data class TooOld(
        val suggestOtherLicenseType: LicenseType?
    ) : DriversLicenseApplicationFeedback() {
        override val property = DriversLicenseApplication::age
        override val severity = Severity.Error
    }
}