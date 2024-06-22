package tech.coner.trailer.toolkit.sample.dmvapp.presentation.model

import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType

data class DriversLicenseApplicationModel(
    val name: String? = null,
    val age: Int? = null,
    val licenseType: LicenseType? = null
)

