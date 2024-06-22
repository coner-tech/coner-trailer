package tech.coner.trailer.toolkit.sample.dmvapp.presentation.presenter

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isSuccess
import assertk.assertions.prop
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.kodein.di.DIAware
import org.kodein.di.instance
import tech.coner.trailer.assertk.arrowkt.isRight
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicense
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType.FullLicense
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.model.DriversLicenseApplicationModel
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.state.DriversLicenseApplicationState
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback.*
import tech.coner.trailer.toolkit.sample.dmvapp.testDi
import tech.coner.trailer.toolkit.validation.testsupport.feedbackByProperty
import tech.coner.trailer.toolkit.validation.testsupport.isInvalid

class DriversLicenseApplicationPresenterTest: DIAware by testDi {

    private val presenter: DriversLicenseApplicationPresenter by instance()

    @Test
    fun itShouldProcessValidApplication() = runTest {
        val license = DriversLicense(
            name = "experienced driver",
            age = 42,
            licenseType = FullLicense
        )
        with(presenter) {
            name.value = license.name
            age.value = license.age
            licenseType.value = license.licenseType

            submitApplication()
        }

        assertThat(presenter.state)
            .prop(DriversLicenseApplicationState::outcome)
            .isNotNull()
            .isSuccess()
            .isRight()
            .isEqualTo(license)
    }

    @Test
    fun itShouldHandleInvalidApplication() = runTest {
        with(presenter) {
            // model's initial values aren't valid

            submitApplication()
            runCurrent()
        }

        assertThat(presenter.validationResult)
            .isNotNull()
            .all {
                isInvalid()
                feedbackByProperty().isEqualTo(
                    mapOf(
                        DriversLicenseApplicationModel::name to listOf(NameIsRequired),
                        DriversLicenseApplicationModel::age to listOf(AgeIsRequired),
                        DriversLicenseApplicationModel::licenseType to listOf(LicenseTypeIsRequired)
                    )
                )
        }
    }
}