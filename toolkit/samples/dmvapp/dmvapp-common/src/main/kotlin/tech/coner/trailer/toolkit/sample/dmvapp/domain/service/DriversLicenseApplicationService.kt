package tech.coner.trailer.toolkit.sample.dmvapp.domain.service

import arrow.core.Either
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicense
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicenseApplication
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicenseApplicationRejection

interface DriversLicenseApplicationService {

    suspend fun submit(application: DriversLicenseApplication): Result<Either<DriversLicenseApplicationRejection, DriversLicense>>

}