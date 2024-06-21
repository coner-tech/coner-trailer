package tech.coner.trailer.toolkit.sample.dmvapp.domain.entity

data class DriversLicense(
    val name: String,
    val ageWhenApplied: Int,
    val licenseType: tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType
)