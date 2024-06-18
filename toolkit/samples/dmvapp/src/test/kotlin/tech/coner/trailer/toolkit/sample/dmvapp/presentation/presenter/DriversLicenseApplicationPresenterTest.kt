package tech.coner.trailer.toolkit.sample.dmvapp.presentation.presenter

import assertk.all
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNotNull
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicense
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType.FullLicense
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.driversLicense
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.feedback
import tech.coner.trailer.toolkit.sample.dmvapp.domain.service.impl.DriversLicenseApplicationServiceImpl
import tech.coner.trailer.toolkit.sample.dmvapp.domain.validation.DriversLicenseClerk
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.DriversLicenseApplicationPresenter
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.model.DriversLicenseApplicationModel
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback.AgeIsRequired
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback.LicenseTypeIsRequired
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback.NameIsRequired
import tech.coner.trailer.toolkit.validation.testsupport.feedback
import tech.coner.trailer.toolkit.validation.testsupport.isValid

class DriversLicenseApplicationPresenterTest {

    private val presenter = DriversLicenseApplicationPresenter(
        service = DriversLicenseApplicationServiceImpl(
            clerk = DriversLicenseClerk()
        )
    )

    @Test
    fun itShouldProcessValidApplication() = runTest {
        val license = DriversLicense(
            name = "experienced driver",
            ageWhenApplied = 42,
            licenseType = FullLicense
        )
        with(presenter.state.model) {
            name = license.name
            age = license.ageWhenApplied
            licenseType = license.licenseType

            commit()
                .onSuccess {
                    runBlocking {
                        presenter.processApplication()
                    }
                }
        }

        assertThat(presenter.state.outcome)
            .isNotNull()
            .all {
                driversLicense().isEqualTo(license)
                feedback().isEmpty()
            }
    }

    @Test
    fun itShouldHandleInvalidApplication() = runTest {
        with(presenter.state.model) {
            // model's initial values aren't valid

            commit()
                .onSuccess {
                    fail("unexpected: commit invoked successFn")
                }
        }

        assertThat(presenter.state.model.pendingItemValidation).all {
            isValid().isFalse()
            feedback().isEqualTo(
                mapOf(
                    DriversLicenseApplicationModel::name to listOf(NameIsRequired),
                    DriversLicenseApplicationModel::age to listOf(AgeIsRequired),
                    DriversLicenseApplicationModel::licenseType to listOf(LicenseTypeIsRequired)
                )
            )
        }
    }
}