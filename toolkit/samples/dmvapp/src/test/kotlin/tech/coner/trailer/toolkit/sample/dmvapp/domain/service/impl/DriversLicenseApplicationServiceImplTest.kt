package tech.coner.trailer.toolkit.sample.dmvapp.domain.service.impl

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicense
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicenseApplication
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType.FullLicense
import tech.coner.trailer.toolkit.sample.dmvapp.domain.service.DriversLicenseApplicationService
import tech.coner.trailer.toolkit.sample.dmvapp.domain.validation.DriversLicenseApplicationFeedback.NameMustNotBeBlank
import tech.coner.trailer.toolkit.validation.ValidationResult

class DriversLicenseApplicationServiceImplTest {

    private val service: DriversLicenseApplicationService = DriversLicenseApplicationServiceImpl()

    enum class ProcessScenario(
        val application: DriversLicenseApplication,
        val expected: DriversLicenseApplication.Outcome
    ) {
        VALID(
            application = DriversLicenseApplication(
                name = "not blank",
                age = 18,
                licenseType = FullLicense
            ),
            expected = DriversLicenseApplication.Outcome(
                driversLicense = DriversLicense(
                    name = "not blank",
                    ageWhenApplied = 18,
                    licenseType = FullLicense
                ),
                feedback = emptyMap()
            )
        ),
        INVALID(
            application = DriversLicenseApplication(
                name = "",
                age = 18,
                licenseType = FullLicense
            ),
            expected = DriversLicenseApplication.Outcome(
                driversLicense = null,
                feedback = mapOf(
                    DriversLicenseApplication::name to listOf(NameMustNotBeBlank)
                )
            )
        )
    }

    @ParameterizedTest
    @EnumSource(ProcessScenario::class)
    fun itShouldProcessDriversLicenseApplication(scenario: ProcessScenario) = runTest {
        val actual = service.process(scenario.application)

        assertThat(actual).isEqualTo(scenario.expected)
    }
}