package tech.coner.trailer.toolkit.sample.dmvapp.domain.service.impl

import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.delay
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicense
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicenseApplication
import tech.coner.trailer.toolkit.sample.dmvapp.domain.service.DriversLicenseApplicationService
import tech.coner.trailer.toolkit.sample.dmvapp.domain.validation.DriversLicenseClerk
import tech.coner.trailer.toolkit.validation.invoke

internal class DriversLicenseApplicationServiceImpl(
    private val clerk: DriversLicenseClerk = DriversLicenseClerk()
) : DriversLicenseApplicationService {

    override suspend fun process(application: DriversLicenseApplication): DriversLicenseApplication.Outcome {
        return clerk(application)
            .also { if (it.isInvalid) delay(3.seconds) }
            .let {
                DriversLicenseApplication.Outcome(
                    driversLicense = it.whenValid {
                        DriversLicense(
                            name = application.name,
                            ageWhenApplied = application.age,
                            licenseType = application.licenseType
                        )
                    },
                    feedback = it.feedback
                )
            }
    }
}