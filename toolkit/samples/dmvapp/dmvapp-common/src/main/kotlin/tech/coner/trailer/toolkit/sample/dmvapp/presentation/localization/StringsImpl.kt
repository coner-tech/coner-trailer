package tech.coner.trailer.toolkit.sample.dmvapp.presentation.localization

import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType.FullLicense
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType.GraduatedLearnerPermit
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType.LearnerPermit
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.Sass
import tech.coner.trailer.toolkit.sample.dmvapp.domain.validation.DriversLicenseApplicationFeedback
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback.AgeIsRequired
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback.DelegatedFeedback
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback.LicenseTypeIsRequired
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback.NameIsRequired

class StringsImpl(translation: Translation) : Strings,
    Translation by translation {

    override val licenseTypeLabels = listOf(
        GraduatedLearnerPermit to  driversLicenseLicenseTypeGraduatedLearnerPermit,
        LearnerPermit to driversLicenseLicenseTypeLearnerPermit,
        FullLicense to driversLicenseLicenseTypeFullLicense
    )
    override val licenseTypesByObject = licenseTypeLabels.toMap()

    override fun get(licenseType: LicenseType) = licenseTypesByObject[licenseType]
        ?: throw Exception("No string defined for $licenseType")

    override fun getNullable(licenseType: LicenseType?) = licenseType?.let { get(it) } ?: ""

    override fun get(feedback: DriversLicenseApplicationModelFeedback) = when (feedback) {
        NameIsRequired -> driversLicenseNameFeedbackRequired
        AgeIsRequired -> driversLicenseAgeFeedbackRequired
        LicenseTypeIsRequired -> driversLicenseLicenseTypeFeedbackRequired
        is DelegatedFeedback -> get(feedback.feedback)
    }

    override fun get(feedback: DriversLicenseApplicationFeedback) = with (feedback) {
        when (this) {
            DriversLicenseApplicationFeedback.NameMustNotBeBlank -> driversLicenseNameFeedbackNotBlank
            is DriversLicenseApplicationFeedback.TooYoung -> {
                val suggestion = when {
                    reapplyWhenAge != null && suggestOtherLicenseType != null -> 
                        driversLicenseAgeFeedbackTooYoungSuggestOtherLicenseTypeWhenAgedFormat.format(
                            get(suggestOtherLicenseType),
                            reapplyWhenAge
                        )
                    reapplyWhenAge != null -> 
                        driversLicenseAgeFeedbackTooYoungSuggestReapplyWhenAgedFormat.format(
                            reapplyWhenAge
                        )
                    suggestOtherLicenseType != null ->
                        driversLicenseAgeFeedbackTooYoungSuggestOtherLicenseTypeFormat.format(
                            get(suggestOtherLicenseType)
                        )
                    else -> null
                }
                val tooYoung = driversLicenseAgeFeedbackTooYoung
                suggestion
                    ?.let { driversLicenseAgeFeedbackTooYoungSuggestionFormat.format(tooYoung, suggestion) }
                    ?: tooYoung
            }

            is DriversLicenseApplicationFeedback.TooOld -> {
                val tooOld = driversLicenseAgeFeedbackTooOld
                suggestOtherLicenseType
                    ?.let {
                        driversLicenseAgeFeedbackTooOldSuggestOtherLicenseTypeFormat.format(tooOld, get(suggestOtherLicenseType))
                    }
                    ?: tooOld
            }
        }
    }

    override fun get(sass: Sass): String = sassFormat.format(
        when (sass) {
            Sass.LEAVING_FOR_BREAK -> sassLeavingForBreak
            Sass.LOOKS_AT_BREAK_SIGN_TAPS_READS_MAGAZINE -> sassLooksAtBreakSignTapsReadsMagazine
            Sass.MEAL_DELIVERY_JUST_ARRIVED -> sassMealDeliveryJustArrived
        }
    )
}
