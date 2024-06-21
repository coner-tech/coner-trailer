package tech.coner.trailer.toolkit.sample.dmvapp.presentation.localization

import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback

interface Localization {

    fun label(model: DriversLicenseApplicationModelFeedback): String
    val dmvLabel: String
    val dmvMotto: String
    val licenseTypeLabels: List<Pair<LicenseType, String>>
    val licenseTypesByObject: Map<LicenseType, String>
    val driversLicenseGranted: String
    val driversLicenseHeading: String
    val driversLicensePhotoPlaceholder: String
    val driversLicenseNameField: String
    val driversLicenseAgeWhenAppliedField: String
    val driversLicenseLicenseTypeField: String
}