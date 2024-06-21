package tech.coner.trailer.toolkit.sample.dmvapp.presentation.adapter

import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicenseApplication
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.model.DriversLicenseApplicationModel

fun DriversLicenseApplicationModel.toDomainEntity(): DriversLicenseApplication? {
    return when {
        name != null && age != null && licenseType != null -> DriversLicenseApplication(
            name = name,
            age = age,
            licenseType = licenseType
        )
        else -> null
    }
}