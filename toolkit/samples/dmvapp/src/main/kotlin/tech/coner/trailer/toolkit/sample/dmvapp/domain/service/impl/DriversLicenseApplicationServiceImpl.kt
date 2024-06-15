package tech.coner.trailer.toolkit.sample.dmvapp.domain.service.impl

import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicense
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicenseApplication
import tech.coner.trailer.toolkit.sample.dmvapp.domain.service.DriversLicenseApplicationService
import tech.coner.trailer.toolkit.sample.dmvapp.domain.validation.DriversLicenseClerk
import tech.coner.trailer.toolkit.validation.invoke

internal class DriversLicenseApplicationServiceImpl(
    private val clerk: DriversLicenseClerk
) : DriversLicenseApplicationService {
    override fun process(application: DriversLicenseApplication): DriversLicenseApplication.Outcome {
        return clerk(application)
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