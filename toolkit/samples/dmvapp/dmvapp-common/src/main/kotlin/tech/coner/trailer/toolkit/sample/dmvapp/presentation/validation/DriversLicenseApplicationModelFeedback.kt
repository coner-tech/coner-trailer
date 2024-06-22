package tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation

import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicenseApplication
import tech.coner.trailer.toolkit.sample.dmvapp.domain.validation.DriversLicenseApplicationFeedback
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.model.DriversLicenseApplicationModel
import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.FeedbackDelegate
import tech.coner.trailer.toolkit.validation.Severity
import tech.coner.trailer.toolkit.validation.Severity.Error
import tech.coner.trailer.toolkit.validation.adapter.propertyAdapterOf
import kotlin.reflect.KProperty1

sealed class DriversLicenseApplicationModelFeedback : Feedback<DriversLicenseApplicationModel> {

    data object NameIsRequired : DriversLicenseApplicationModelFeedback() {
        override val property = DriversLicenseApplicationModel::name
        override val severity = Error
    }
    data object AgeIsRequired : DriversLicenseApplicationModelFeedback() {
        override val property = DriversLicenseApplicationModel::age
        override val severity = Error
    }
    data object LicenseTypeIsRequired : DriversLicenseApplicationModelFeedback() {
        override val property = DriversLicenseApplicationModel::licenseType
        override val severity = Error
    }

    data class DelegatedFeedback(
        val feedback: DriversLicenseApplicationFeedback
    ) : DriversLicenseApplicationModelFeedback(),
        Feedback<DriversLicenseApplicationModel> by FeedbackDelegate(
            feedback,
            propertyAdapterOf(
                DriversLicenseApplication::name to DriversLicenseApplicationModel::name,
                DriversLicenseApplication::age to DriversLicenseApplicationModel::age,
                DriversLicenseApplication::licenseType to DriversLicenseApplicationModel::licenseType,
                null to null
            )
        )
}
