package tech.coner.trailer.toolkit.sample.dmvapp.domain.validation

import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicenseApplication
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType.GraduatedLearnerPermit
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType.LearnerPermit
import tech.coner.trailer.toolkit.sample.dmvapp.domain.validation.DriversLicenseApplicationFeedback.NameMustNotBeBlank
import tech.coner.trailer.toolkit.sample.dmvapp.domain.validation.DriversLicenseApplicationFeedback.TooOld
import tech.coner.trailer.toolkit.sample.dmvapp.domain.validation.DriversLicenseApplicationFeedback.TooYoung
import tech.coner.trailer.toolkit.validation.Validator

typealias DriversLicenseClerk = Validator<Unit, DriversLicenseApplication, DriversLicenseApplicationFeedback>

val driversLicenseClerk: DriversLicenseClerk
    get() = Validator {
        DriversLicenseApplication::name { name ->
            NameMustNotBeBlank.takeIf { name.isBlank() }
        }
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
                        suggestOtherLicenseType = LearnerPermit
                    )

                else -> null
            }
        }
}
