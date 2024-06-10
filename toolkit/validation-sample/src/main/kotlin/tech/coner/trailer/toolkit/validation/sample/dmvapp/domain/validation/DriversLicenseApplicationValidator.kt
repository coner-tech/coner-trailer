package tech.coner.trailer.toolkit.validation.sample.dmvapp.domain.validation

import tech.coner.trailer.toolkit.validation.Validator
import tech.coner.trailer.toolkit.validation.sample.dmvapp.domain.entity.DriversLicenseApplication
import tech.coner.trailer.toolkit.validation.sample.dmvapp.domain.entity.DriversLicenseApplication.LicenseType.GraduatedLearnerPermit
import tech.coner.trailer.toolkit.validation.sample.dmvapp.domain.validation.DriversLicenseApplicationFeedback.TooOld
import tech.coner.trailer.toolkit.validation.sample.dmvapp.domain.validation.DriversLicenseApplicationFeedback.TooYoung


fun driversLicenseClerk() = Validator<DriversLicenseApplication, DriversLicenseApplicationFeedback> {
    on(DriversLicenseApplication::age) {
        when {
            it < GraduatedLearnerPermit.MIN_AGE ->
                TooYoung(
                    suggestOtherLicenseType = if (licenseType != GraduatedLearnerPermit) GraduatedLearnerPermit else null,
                    reapplyWhenAge = GraduatedLearnerPermit.MIN_AGE
                )

            it in GraduatedLearnerPermit.AGE_RANGE && licenseType != GraduatedLearnerPermit ->
                TooYoung(
                    suggestOtherLicenseType = GraduatedLearnerPermit,
                )

            it in 18..Int.MAX_VALUE && licenseType == GraduatedLearnerPermit ->
                TooOld(
                    suggestOtherLicenseType = DriversLicenseApplication.LicenseType.LearnerPermit
                )

            else -> null
        }
    }
}
