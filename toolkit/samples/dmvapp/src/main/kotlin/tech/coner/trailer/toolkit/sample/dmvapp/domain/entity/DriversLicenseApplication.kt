package tech.coner.trailer.toolkit.sample.dmvapp.domain.entity

import kotlin.reflect.KProperty1
import tech.coner.trailer.toolkit.sample.dmvapp.domain.validation.DriversLicenseApplicationFeedback

data class DriversLicenseApplication(
    val name: String,
    val age: Int,
    val licenseType: LicenseType,
) {

    data class Outcome(
        val driversLicense: DriversLicense?,
        val feedback: Map<KProperty1<DriversLicenseApplication, *>?, List<DriversLicenseApplicationFeedback>>
    )
}
