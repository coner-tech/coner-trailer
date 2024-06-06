package tech.coner.trailer.toolkit.validation

import assertk.Assert
import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import tech.coner.trailer.toolkit.validation.DriversLicenseApplication.LicenseType.GraduatedLearnerPermit
import tech.coner.trailer.toolkit.validation.DriversLicenseApplication.LicenseType.LearnerPermit
import tech.coner.trailer.toolkit.validation.DriversLicenseApplicationFeedback.TooOld
import tech.coner.trailer.toolkit.validation.DriversLicenseApplicationFeedback.TooYoung

class ValidatorTest {

    enum class DriversLicenseApplicationScenario(
        val input: DriversLicenseApplication,
        val expectedFeedback: List<DriversLicenseApplicationFeedback>,
        val expectedValid: Boolean
    ) {
        UNDERAGE_FULL(
            input = DriversLicenseApplication(
                age = GraduatedLearnerPermit.MIN_AGE - 1,
                licenseType = DriversLicenseApplication.LicenseType.FullLicense
            ),
            expectedFeedback = listOf(
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
            expectedFeedback = listOf(
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
            expectedFeedback = listOf(
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
                licenseType = DriversLicenseApplication.LicenseType.FullLicense
            ),
            expectedFeedback = listOf(
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
            expectedFeedback = emptyList(),
            expectedValid = true
        ),
        ELIGIBLE_UPPER_GRADUATED_LEARNER_PERMIT(
            input = DriversLicenseApplication(
                age = GraduatedLearnerPermit.MAX_AGE_INCLUSIVE,
                licenseType = GraduatedLearnerPermit
            ),
            expectedFeedback = emptyList(),
            expectedValid = true
        ),
        ELIGIBLE_LOWER_LEARNER_PERMIT(
            input = DriversLicenseApplication(
                age = LearnerPermit.MIN_AGE,
                licenseType = LearnerPermit
            ),
            expectedFeedback = emptyList(),
            expectedValid = true
        ),
        ELIGIBLE_UPPER_LEARNER_PERMIT(
            input = DriversLicenseApplication(
                age = Int.MAX_VALUE,
                licenseType = LearnerPermit
            ),
            expectedFeedback = emptyList(),
            expectedValid = true
        ),
        ELIGIBLE_LOWER_FULL_LICENSE(
            input = DriversLicenseApplication(
                age = DriversLicenseApplication.LicenseType.FullLicense.MIN_AGE,
                licenseType = DriversLicenseApplication.LicenseType.FullLicense
            ),
            expectedFeedback = emptyList(),
            expectedValid = true
        )
    }

    @ParameterizedTest
    @EnumSource
    fun driversLicenseClerkShouldValidateApplications(scenario: DriversLicenseApplicationScenario) {
        val actual = driversLicenseClerk(scenario.input)

        assertThat(actual).all {
            feedback().isEqualTo(scenario.expectedFeedback)
            isValid().isEqualTo(scenario.expectedValid)
            isInvalid().isEqualTo(!scenario.expectedValid)
        }
    }
}


data class DriversLicenseApplication(
    val age: Int,
    val licenseType: LicenseType,
) {

    sealed class LicenseType {
        data object GraduatedLearnerPermit : LicenseType() {
            const val MIN_AGE = 15
            const val MAX_AGE_INCLUSIVE = 17
            val AGE_RANGE = MIN_AGE..MAX_AGE_INCLUSIVE
        }
        data object LearnerPermit : LicenseType() {
            const val MIN_AGE = 18
        }
        data object FullLicense : LicenseType() {
            const val MIN_AGE = 18
        }
    }
}

sealed class DriversLicenseApplicationFeedback : Feedback {
    data class TooYoung(
        val suggestOtherLicenseType: DriversLicenseApplication.LicenseType? = null,
        val reapplyWhenAge: Int? = null
    ) : DriversLicenseApplicationFeedback() {
        override val severity: Severity = Severity.Error
    }

    data class TooOld(
        val suggestOtherLicenseType: DriversLicenseApplication.LicenseType?
    ) : DriversLicenseApplicationFeedback() {
        override val severity: Severity = Severity.Error
    }
}

val driversLicenseClerk = Validator<DriversLicenseApplication, DriversLicenseApplicationFeedback> { application ->
    when {
        application.age < GraduatedLearnerPermit.MIN_AGE ->
            TooYoung(
                suggestOtherLicenseType = if (application.licenseType != GraduatedLearnerPermit) GraduatedLearnerPermit else null,
                reapplyWhenAge = GraduatedLearnerPermit.MIN_AGE
            )

        application.age in GraduatedLearnerPermit.AGE_RANGE && application.licenseType != GraduatedLearnerPermit ->
            TooYoung(
                suggestOtherLicenseType = GraduatedLearnerPermit,
            )

        application.age in 18..Int.MAX_VALUE && application.licenseType == GraduatedLearnerPermit ->
            TooOld(
                suggestOtherLicenseType = LearnerPermit
            )

        else -> null
    }?.also { give(it) }
}

fun <FEEDBACK : Feedback> Assert<ValidationResult<FEEDBACK>>.feedback() = prop(ValidationResult<FEEDBACK>::feedback)

fun Assert<ValidationResult<*>>.isValid() = prop(ValidationResult<*>::isValid)
fun Assert<ValidationResult<*>>.isInvalid() = prop(ValidationResult<*>::isInvalid)
