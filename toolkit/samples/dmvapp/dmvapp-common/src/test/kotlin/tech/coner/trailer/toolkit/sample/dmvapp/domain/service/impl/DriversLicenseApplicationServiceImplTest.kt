package tech.coner.trailer.toolkit.sample.dmvapp.domain.service.impl

import arrow.core.Either
import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicense
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicenseApplication
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicenseApplicationRejection
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType.FullLicense
import tech.coner.trailer.toolkit.sample.dmvapp.domain.service.DriversLicenseApplicationService
import tech.coner.trailer.toolkit.sample.dmvapp.domain.validation.DriversLicenseApplicationFeedback.NameMustNotBeBlank
import tech.coner.trailer.toolkit.sample.dmvapp.domain.validation.DriversLicenseClerk
import tech.coner.trailer.toolkit.validation.ValidationOutcome

class DriversLicenseApplicationServiceImplTest {

    private val service: DriversLicenseApplicationService = DriversLicenseApplicationServiceImpl(
        clerk = DriversLicenseClerk()
    )

    enum class ProcessScenario(
        val application: DriversLicenseApplication,
        val expected: Result<Either<DriversLicenseApplicationRejection, DriversLicense>>
    ) {
        VALID(
            application = DriversLicenseApplication(
                name = "not blank",
                age = 18,
                licenseType = FullLicense
            ),
            expected = Result.success(
                Either.Right(
                    DriversLicense(
                        name = "not blank",
                        age = 18,
                        licenseType = FullLicense
                    ),
                )
            )
        ),
        INVALID(
            application = DriversLicenseApplication(
                name = "",
                age = 18,
                licenseType = FullLicense
            ),
            expected = Result.success(
                Either.Left(
                    DriversLicenseApplicationRejection.Invalid(
                        ValidationOutcome(
                            listOf(NameMustNotBeBlank)
                        )
                    )
                )
            )
        )
    }

    @ParameterizedTest
    @EnumSource(ProcessScenario::class)
    fun itShouldSubmitDriversLicenseApplication(scenario: ProcessScenario) = runTest {
        val actual = service.submit(scenario.application)

        assertThat(actual).isEqualTo(scenario.expected)
    }
}