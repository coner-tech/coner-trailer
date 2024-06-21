package tech.coner.trailer.toolkit.sample.dmvapp.domain.entity

sealed class LicenseType {
    data object GraduatedLearnerPermit : tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType() {
        const val MIN_AGE = 15
        const val MAX_AGE_INCLUSIVE = 17
        val AGE_RANGE = tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType.GraduatedLearnerPermit.MIN_AGE..tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType.GraduatedLearnerPermit.MAX_AGE_INCLUSIVE
    }
    data object LearnerPermit : tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType() {
        const val MIN_AGE = 18
    }
    data object FullLicense : tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType() {
        const val MIN_AGE = 18
    }
}