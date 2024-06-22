package tech.coner.trailer.toolkit.sample.dmvapp.presentation.localization

import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.Sass
import tech.coner.trailer.toolkit.sample.dmvapp.domain.validation.DriversLicenseApplicationFeedback
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback

interface Strings : Translation {

    val licenseTypeLabels: List<Pair<LicenseType, String>>
    val licenseTypesByObject: Map<LicenseType, String>
    operator fun get(licenseType: LicenseType): String
    fun getNullable(licenseType: LicenseType?): String

    operator fun get(feedback: DriversLicenseApplicationModelFeedback): String
    operator fun get(feedback: DriversLicenseApplicationFeedback): String
    operator fun get(sass: Sass): String
}