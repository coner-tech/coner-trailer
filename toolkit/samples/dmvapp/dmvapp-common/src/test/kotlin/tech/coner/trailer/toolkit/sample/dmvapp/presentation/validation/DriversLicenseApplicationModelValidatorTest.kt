package tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlin.reflect.KProperty1
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType.FullLicense
import tech.coner.trailer.toolkit.sample.dmvapp.domain.validation.DriversLicenseApplicationFeedback.TooYoung
import tech.coner.trailer.toolkit.sample.dmvapp.domain.validation.DriversLicenseClerk
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.adapter.DriversLicenseApplicationModelAdapters
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.model.DriversLicenseApplicationModel
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback.AgeIsRequired
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback.DelegatedFeedback
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback.LicenseTypeIsRequired
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback.NameIsRequired
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelValidator
import tech.coner.trailer.toolkit.validation.invoke
import tech.coner.trailer.toolkit.validation.testsupport.feedback
import tech.coner.trailer.toolkit.validation.testsupport.isInvalid
import tech.coner.trailer.toolkit.validation.testsupport.isValid

class DriversLicenseApplicationModelValidatorTest {

    private val validator = DriversLicenseApplicationModelValidator(
        driversLicenseClerk = DriversLicenseClerk(),
        driversLicenseApplicationModelAdapters = DriversLicenseApplicationModelAdapters()
    )

    enum class Scenario(
        val input: DriversLicenseApplicationModel,
        val expectedFeedback: List<DriversLicenseApplicationModelFeedback> = emptyList(),
        val expectedIsValid: Boolean
    ) {
        FAIL_MODEL_FROM_DEFAULT_CONSTRUCTOR(
            input = DriversLicenseApplicationModel(),
            expectedFeedback = listOf(
                NameIsRequired,
                AgeIsRequired,
                LicenseTypeIsRequired,
            ),
            expectedIsValid = false
        ),

        FAIL_MODEL_WITH_NAME_NULL(
            input = DriversLicenseApplicationModel(
                name = null,
                age = 42,
                licenseType = FullLicense
            ),
            expectedFeedback = listOf(
                NameIsRequired
            ),
            expectedIsValid = false
        ),

        FAIL_MODEL_WITH_AGE_NULL(
            input = DriversLicenseApplicationModel(
                name = "not null",
                age = null,
                licenseType = FullLicense
            ),
            expectedFeedback = listOf(
                AgeIsRequired
            ),
            expectedIsValid = false
        ),

        FAIL_MODEL_WITH_LICENSE_TYPE_NULL(
            input = DriversLicenseApplicationModel(
                name = "not null",
                age = 42,
                licenseType = null
            ),
            expectedFeedback = listOf(
                LicenseTypeIsRequired
            ),
            expectedIsValid = false
        ),

        FAIL_MAPPED_DELEGATED_TO_DOMAIN_VALIDATOR_FAILED(
            input = DriversLicenseApplicationModel(
                name = "some kiddo",
                age = 15,
                licenseType = FullLicense
            ),
            expectedFeedback = listOf(
                DelegatedFeedback(TooYoung(suggestOtherLicenseType = LicenseType.GraduatedLearnerPermit))
            ),
            expectedIsValid = false
        ),

        PASS_MAPPED_DELEGATED_TO_DOMAIN_VALIDATOR_PASSED(
            input = DriversLicenseApplicationModel(
                name = "experienced driver",
                age = 40,
                licenseType = FullLicense
            ),
            expectedFeedback = emptyList(),
            expectedIsValid = true
        )
    }

    @ParameterizedTest
    @EnumSource
    fun itShouldValidateDriversLicenseApplicationModels(scenario: Scenario) {
        val actual = validator(scenario.input)

        assertThat(actual).all {
            feedback().isEqualTo(scenario.expectedFeedback)
            isValid().isEqualTo(scenario.expectedIsValid)
            isInvalid().isEqualTo(!scenario.expectedIsValid)
        }
    }
}