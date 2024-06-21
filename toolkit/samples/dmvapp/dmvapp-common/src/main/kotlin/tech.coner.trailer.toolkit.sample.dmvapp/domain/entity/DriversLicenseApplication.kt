package tech.coner.trailer.toolkit.sample.dmvapp.domain.entity

import kotlin.reflect.KProperty1
import tech.coner.trailer.toolkit.sample.dmvapp.domain.validation.DriversLicenseApplicationFeedback

data class DriversLicenseApplication(
    val name: String,
    val age: Int,
    val licenseType: tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType,
) {

    data class Outcome(
        val driversLicense: tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicense?,
        val feedback: Map<KProperty1<tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicenseApplication, *>?, List<DriversLicenseApplicationFeedback>>
    )
}
