package tech.coner.trailer.toolkit.validation.sample.dmvapp.validation


import assertk.all
import assertk.assertThat
import assertk.assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import tech.coner.trailer.toolkit.validation.sample.dmvapp.domain.entity.DriversLicenseApplication
import tech.coner.trailer.toolkit.validation.sample.dmvapp.domain.entity.DriversLicenseApplication.LicenseType.*
import tech.coner.trailer.toolkit.validation.sample.dmvapp.domain.validation.DriversLicenseApplicationFeedback
import tech.coner.trailer.toolkit.validation.sample.dmvapp.domain.validation.DriversLicenseApplicationFeedback.TooYoung
import tech.coner.trailer.toolkit.validation.sample.dmvapp.domain.validation.driversLicenseClerk
import tech.coner.trailer.toolkit.validation.testsupport.feedback
import tech.coner.trailer.toolkit.validation.testsupport.isInvalid
import tech.coner.trailer.toolkit.validation.testsupport.isValid

class DriversLicenseValidatorTest {

    enum class DriversLicenseApplicationScenario(
        val input: DriversLicenseApplication,
        val expectedAgeFeedback: List<DriversLicenseApplicationFeedback>? = null,
        val expectedValid: Boolean
    ) {
        UNDERAGE_FULL(
            input = DriversLicenseApplication(
                age = GraduatedLearnerPermit.MIN_AGE - 1,
                licenseType = FullLicense
            ),
            expectedAgeFeedback = listOf(
                TooYoung(
                    suggestOtherLicenseType = GraduatedLearnerPermit,
                    reapplyWhenAge = GraduatedLearnerPermit.MIN_AGE
                )
            ),
            expectedValid = false
        ),
        UNDERAGE_GRADUATED_LEARNER_PERMIT(
            input = DriversLicenseApplication(
                age = GraduatedLearnerPermit.MIN_AGE - 1,
                licenseType = GraduatedLearnerPermit
            ),
            expectedAgeFeedback = listOf(
                TooYoung(
                    reapplyWhenAge = GraduatedLearnerPermit.MIN_AGE
                )
            ),
            expectedValid = false
        ),
        UNDERAGE_LEARNER_PERMIT(
            input = DriversLicenseApplication(
                age = GraduatedLearnerPermit.MIN_AGE - 1,
                licenseType = LearnerPermit
            ),
            expectedAgeFeedback = listOf(
                TooYoung(
                    suggestOtherLicenseType = GraduatedLearnerPermit,
                    reapplyWhenAge = GraduatedLearnerPermit.MIN_AGE
                )
            ),
            expectedValid = false
        ),
        UNDERAGE_FULL_LICENSE(
            input = DriversLicenseApplication(
                age = 14,
                licenseType = FullLicense
            ),
            expectedAgeFeedback = listOf(
                TooYoung(
                    suggestOtherLicenseType = GraduatedLearnerPermit,
                    reapplyWhenAge = GraduatedLearnerPermit.MIN_AGE
                )
            ),
            expectedValid = false
        ),
        ELIGIBLE_LOWER_GRADUATED_LEARNER_PERMIT(
            input = DriversLicenseApplication(
                age = 15,
                licenseType = GraduatedLearnerPermit
            ),
            expectedValid = true
        ),
        ELIGIBLE_UPPER_GRADUATED_LEARNER_PERMIT(
            input = DriversLicenseApplication(
                age = GraduatedLearnerPermit.MAX_AGE_INCLUSIVE,
                licenseType = GraduatedLearnerPermit
            ),
            expectedValid = true
        ),
        ELIGIBLE_LOWER_LEARNER_PERMIT(
            input = DriversLicenseApplication(
                age = LearnerPermit.MIN_AGE,
                licenseType = LearnerPermit
            ),
            expectedValid = true
        ),
        ELIGIBLE_UPPER_LEARNER_PERMIT(
            input = DriversLicenseApplication(
                age = Int.MAX_VALUE,
                licenseType = LearnerPermit
            ),
            expectedValid = true
        ),
        ELIGIBLE_LOWER_FULL_LICENSE(
            input = DriversLicenseApplication(
                age = FullLicense.MIN_AGE,
                licenseType = FullLicense
            ),
            expectedValid = true
        )
    }

    @ParameterizedTest
    @EnumSource
    fun itShouldValidateDriversLicenseApplications(scenario: DriversLicenseApplicationScenario) {
        val actual = driversLicenseClerk(scenario.input)

        assertThat(actual).all {
            feedback().all {
                hasSize(
                    when {
                        scenario.expectedAgeFeedback != null -> 1
                        else -> 0
                    }
                )
                transform { it[DriversLicenseApplication::age] }
                    .isEqualTo(scenario.expectedAgeFeedback)
            }

            isValid().isEqualTo(scenario.expectedValid)
            isInvalid().isEqualTo(!scenario.expectedValid)
        }
    }
}
