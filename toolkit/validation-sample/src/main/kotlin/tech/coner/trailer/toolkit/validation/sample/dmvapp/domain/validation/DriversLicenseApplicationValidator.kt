package tech.coner.trailer.toolkit.validation.sample.dmvapp.domain.validation

import tech.coner.trailer.toolkit.validation.Validator
import tech.coner.trailer.toolkit.validation.sample.dmvapp.domain.entity.DriversLicenseApplication
import tech.coner.trailer.toolkit.validation.sample.dmvapp.domain.entity.DriversLicenseApplication.LicenseType.GraduatedLearnerPermit
import tech.coner.trailer.toolkit.validation.sample.dmvapp.domain.validation.DriversLicenseApplicationFeedback.TooOld
import tech.coner.trailer.toolkit.validation.sample.dmvapp.domain.validation.DriversLicenseApplicationFeedback.TooYoung

typealias DriversLicenseClerk = Validator<Unit, DriversLicenseApplication, DriversLicenseApplicationFeedback>

val driversLicenseClerk: DriversLicenseClerk get() = Validator {
    DriversLicenseApplication::age { age ->
        when {
            age < GraduatedLearnerPermit.MIN_AGE ->
                TooYoung(
                    suggestOtherLicenseType = GraduatedLearnerPermit.takeIf { input.licenseType != it },
                    reapplyWhenAge = GraduatedLearnerPermit.MIN_AGE
                )

            age in GraduatedLearnerPermit.AGE_RANGE && input.licenseType != GraduatedLearnerPermit ->
                TooYoung(
                    suggestOtherLicenseType = GraduatedLearnerPermit,
                )

            age in 18..Int.MAX_VALUE && input.licenseType == GraduatedLearnerPermit ->
                TooOld(
                    suggestOtherLicenseType = DriversLicenseApplication.LicenseType.LearnerPermit
                )

            else -> null
        }
    }
}