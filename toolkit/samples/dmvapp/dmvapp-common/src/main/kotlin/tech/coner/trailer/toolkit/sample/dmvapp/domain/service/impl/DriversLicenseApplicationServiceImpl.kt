package tech.coner.trailer.toolkit.sample.dmvapp.domain.service.impl

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicense
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicenseApplication
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicenseApplicationRejection
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.Sass
import tech.coner.trailer.toolkit.sample.dmvapp.domain.exception.BuildingOnFireException
import tech.coner.trailer.toolkit.sample.dmvapp.domain.service.DriversLicenseApplicationService
import tech.coner.trailer.toolkit.sample.dmvapp.domain.validation.DriversLicenseClerk
import tech.coner.trailer.toolkit.validation.invoke

class DriversLicenseApplicationServiceImpl(
    private val clerk: DriversLicenseClerk
) : DriversLicenseApplicationService {

    var buildingOnFireChance: Float = 0f
        set(value) {
            require(value in 0f..1f)
            field = value
        }
    var sassChance: Float = 0f
        set(value) {
            require(value in 0f..1f)
            field = value
        }
    var legallyProhibitedChance: Float = 0f
        set(value) {
            require(value in 0f..1f)
            field = value
        }

    override suspend fun submit(application: DriversLicenseApplication): Result<Either<DriversLicenseApplicationRejection, DriversLicense>> = coroutineScope {
        runCatching {
            either {
                if (Random.nextFloat() <= buildingOnFireChance) {
                    throw BuildingOnFireException()
                }
                delay(Random.nextDouble(100.0, 10000.0).milliseconds)
                ensure(Random.nextFloat() >= sassChance) {
                    DriversLicenseApplicationRejection.Sassed(Sass.values().random())
                }
                clerk(application)
                    .also { if (it.isInvalid) delay(5.seconds) }
                    .also {
                        ensure(it.isValid) {
                            DriversLicenseApplicationRejection.Invalid(it)
                        }
                    }
                    .also {
                        ensure(Random.nextFloat() >= legallyProhibitedChance) {
                            DriversLicenseApplicationRejection.LegallyProhibited
                        }
                    }
                    .let {
                        DriversLicense(
                            name = application.name,
                            age = application.age,
                            licenseType = application.licenseType
                        )
                    }
            }
        }
    }

}