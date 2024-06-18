package tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType.FullLicense
import tech.coner.trailer.toolkit.sample.dmvapp.domain.validation.DriversLicenseApplicationFeedback.TooYoung
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.model.DriversLicenseApplicationModel
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback.*
import tech.coner.trailer.toolkit.validation.invoke
import tech.coner.trailer.toolkit.validation.testsupport.feedback
import tech.coner.trailer.toolkit.validation.testsupport.isInvalid
import tech.coner.trailer.toolkit.validation.testsupport.isValid
import kotlin.reflect.KProperty1

class DriversLicenseApplicationModelValidatorTest {

    private val validator = DriversLicenseApplicationModelValidator()

    enum class Scenario(
        val input: DriversLicenseApplicationModel,
        val expectedFeedback: Map<KProperty1<DriversLicenseApplicationModel, *>, List<DriversLicenseApplicationModelFeedback>> = emptyMap(),
        val expectedIsValid: Boolean
    ) {
        FAIL_MODEL_FROM_DEFAULT_CONSTRUCTOR(
            input = DriversLicenseApplicationModel(),
            expectedFeedback = mapOf(
                DriversLicenseApplicationModel::name to listOf(NameIsRequired),
                DriversLicenseApplicationModel::age to listOf(AgeIsRequired),
                DriversLicenseApplicationModel::licenseType to listOf(LicenseTypeIsRequired)
            ),
            expectedIsValid = false
        ),

        FAIL_MODEL_WITH_NAME_NULL(
            input = DriversLicenseApplicationModel(
                name = null,
                age = 42,
                licenseType = FullLicense
            ),
            expectedFeedback = mapOf(
                DriversLicenseApplicationModel::name to listOf(NameIsRequired)
            ),
            expectedIsValid = false
        ),

        FAIL_MODEL_WITH_AGE_NULL(
            input = DriversLicenseApplicationModel(
                name = "not null",
                age = null,
                licenseType = FullLicense
            ),
            expectedFeedback = mapOf(
                DriversLicenseApplicationModel::age to listOf(AgeIsRequired)
            ),
            expectedIsValid = false
        ),

        FAIL_MODEL_WITH_LICENSE_TYPE_NULL(
            input = DriversLicenseApplicationModel(
                name = "not null",
                age = 42,
                licenseType = null
            ),
            expectedFeedback = mapOf(
                DriversLicenseApplicationModel::licenseType to listOf(LicenseTypeIsRequired)
            ),
            expectedIsValid = false
        ),

        FAIL_MAPPED_DELEGATED_TO_DOMAIN_VALIDATOR_FAILED(
            input = DriversLicenseApplicationModel(
                name = "some kiddo",
                age = 15,
                licenseType = FullLicense
            ),
            expectedFeedback = mapOf(
                DriversLicenseApplicationModel::age to listOf(
                    DelegatedFeedback(TooYoung(suggestOtherLicenseType = LicenseType.GraduatedLearnerPermit))
                )
            ),
            expectedIsValid = false
        ),

        PASS_MAPPED_DELEGATED_TO_DOMAIN_VALIDATOR_PASSED(
            input = DriversLicenseApplicationModel(
                name = "experienced driver",
                age = 40,
                licenseType = FullLicense
            ),
            expectedFeedback = emptyMap(),
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