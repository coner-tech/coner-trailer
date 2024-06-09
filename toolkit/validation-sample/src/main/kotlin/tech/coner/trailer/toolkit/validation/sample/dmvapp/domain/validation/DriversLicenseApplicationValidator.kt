package tech.coner.trailer.toolkit.validation.sample.dmvapp.domain.validation

import tech.coner.trailer.toolkit.validation.Validator
import tech.coner.trailer.toolkit.validation.sample.dmvapp.domain.entity.DriversLicenseApplication
import tech.coner.trailer.toolkit.validation.sample.dmvapp.domain.entity.DriversLicenseApplication.LicenseType.GraduatedLearnerPermit
import tech.coner.trailer.toolkit.validation.sample.dmvapp.domain.validation.DriversLicenseApplicationFeedback.TooOld
import tech.coner.trailer.toolkit.validation.sample.dmvapp.domain.validation.DriversLicenseApplicationFeedback.TooYoung


fun driversLicenseClerk() = Validator { application: DriversLicenseApplication ->
    on(DriversLicenseApplication::age) { age: Int ->
        when {
            age < GraduatedLearnerPermit.MIN_AGE ->
                TooYoung(
                    suggestOtherLicenseType = if (application.licenseType != GraduatedLearnerPermit) GraduatedLearnerPermit else null,
                    reapplyWhenAge = GraduatedLearnerPermit.MIN_AGE
                )

            age in GraduatedLearnerPermit.AGE_RANGE && application.licenseType != GraduatedLearnerPermit ->
                TooYoung(
                    suggestOtherLicenseType = GraduatedLearnerPermit,
                )

            age in 18..Int.MAX_VALUE && application.licenseType == GraduatedLearnerPermit ->
                TooOld(
                    suggestOtherLicenseType = DriversLicenseApplication.LicenseType.LearnerPermit
                )

            else -> null
        }
    }
    null
}
