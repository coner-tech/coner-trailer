package tech.coner.trailer.toolkit.sample.dmvapp.presentation.localization

import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType.FullLicense
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType.GraduatedLearnerPermit
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType.LearnerPermit
import tech.coner.trailer.toolkit.sample.dmvapp.domain.validation.DriversLicenseApplicationFeedback
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback.AgeIsRequired
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback.DelegatedFeedback
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback.LicenseTypeIsRequired
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback.NameIsRequired

class EnglishUsLocalization : Localization {

    override val dmvLabel: String get() = "Coner Trailer DMV"
    override val dmvMotto: String get() = "Officiale sine auctoritate"
    override val licenseTypeLabels = listOf(
        GraduatedLearnerPermit to "Graduated Learner Permit" ,
        LearnerPermit to "Learner Permit",
        FullLicense to "Full License"
    )
    override val licenseTypesByObject = licenseTypeLabels.toMap()

    override fun label(model: DriversLicenseApplicationModelFeedback) = model.label

    private val DriversLicenseApplicationModelFeedback.label: String
        get() = when (this) {
            NameIsRequired -> "Name is required"
            AgeIsRequired -> "Age is required"
            LicenseTypeIsRequired -> "License Type is required"
            is DelegatedFeedback -> feedback.label
        }

    private val DriversLicenseApplicationFeedback.label: String
        get() = when (this) {
            DriversLicenseApplicationFeedback.NameMustNotBeBlank -> "Name must not be blank"
            is DriversLicenseApplicationFeedback.TooYoung -> {
                val suggestion = when {
                    reapplyWhenAge != null && suggestOtherLicenseType != null -> "Apply for ${licenseTypesByObject[suggestOtherLicenseType]} when you turn $reapplyWhenAge."
                    reapplyWhenAge != null -> "Reapply when you turn $reapplyWhenAge."
                    suggestOtherLicenseType != null -> "Reapply for ${licenseTypesByObject[suggestOtherLicenseType]}."
                    else -> null
                }
                val tooYoung = "You are too young."
                when {
                    suggestion != null -> "$tooYoung $suggestion"
                    else -> tooYoung
                }
            }
            is DriversLicenseApplicationFeedback.TooOld -> {
                val tooOld = "You are too old."
                when {
                    suggestOtherLicenseType != null -> "$tooOld Reapply for ${licenseTypesByObject[suggestOtherLicenseType]}."
                    else -> tooOld
                }
            }
        }

    override val driversLicenseGranted: String get() = "Granted drivers license!"
    override val driversLicenseHeading: String get() = "Drivers License"
    override val driversLicensePhotoPlaceholder: String get() = "[photo]"
    override val driversLicenseNameField: String get() = "Name"
    override val driversLicenseAgeWhenAppliedField: String get() = "Age"
    override val driversLicenseLicenseTypeField: String get() = "License Type"

}