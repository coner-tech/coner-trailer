package tech.coner.trailer.toolkit.sample.dmvapp.domain.entity

data class DriversLicenseApplication(
    val age: Int,
    val licenseType: LicenseType,
) {

    sealed class LicenseType {
        data object GraduatedLearnerPermit : LicenseType() {
            const val MIN_AGE = 15
            const val MAX_AGE_INCLUSIVE = 17
            val AGE_RANGE = MIN_AGE..MAX_AGE_INCLUSIVE
        }
        data object LearnerPermit : LicenseType() {
            const val MIN_AGE = 18
        }
        data object FullLicense : LicenseType() {
            const val MIN_AGE = 18
        }
    }
}
