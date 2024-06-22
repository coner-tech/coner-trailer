package tech.coner.trailer.toolkit.sample.dmvapp.presentation.localization

import tech.coner.trailer.toolkit.validation.presentation.localization.EnglishUsValidationTranslation
import tech.coner.trailer.toolkit.validation.presentation.localization.ValidationStringsImpl

class EnglishUsTranslation() : Translation {

    override val conerLogoContentDescription: String get() = "Coner Logo"
    override val dmvLabel: String get() = "Division of Motor Vehicles"
    override val dmvMotto: String get() = "Officiale sine auctoritate"

    override val settings get() = "Settings"
    override val menuContentDescription get() = "Menu"
    override val settingsThemeTitle: String get() = "Theme"
    override val settingsThemeModeTitle: String get() = "Mode"
    override val settingsThemeModeAuto: String get() = "Auto"
    override val settingsThemeModeLight: String get() = "Use Light Theme"
    override val settingsThemeModeDark: String get() = "Use Dark Theme"

    override val driversLicenseGranted: String get() = "Granted drivers license!"
    override val driversLicenseHeading: String get() = "Drivers License"
    override val driversLicenseNameField: String get() = "Name"
    override val driversLicenseNameFeedbackRequired get() = "Name is required"
    override val driversLicenseNameFeedbackNotBlank get() = "Name must not be blank"
    override val driversLicenseAgeField: String get() = "Age"
    override val driversLicenseAgeFeedbackRequired get() = "Age is required"
    override val driversLicenseAgeFeedbackTooYoung get() = "You are too young."
    override val driversLicenseAgeFeedbackTooYoungSuggestOtherLicenseTypeWhenAgedFormat
        get() = "Apply for %1\$s when you turn %2\$d"
    override val driversLicenseAgeFeedbackTooYoungSuggestReapplyWhenAgedFormat
        get() = "Reapply when you turn %1\$s."
    override val driversLicenseAgeFeedbackTooYoungSuggestOtherLicenseTypeFormat
        get() = "Reapply for %1\$s."
    override val driversLicenseAgeFeedbackTooYoungSuggestionFormat
        get() = "%1\$s %2\$s"
    override val driversLicenseAgeFeedbackTooOld get() = "You are too old."
    override val driversLicenseAgeFeedbackTooOldSuggestOtherLicenseTypeFormat: String
        get() = "%1\$s Reapply for %2\$s."
    override val driversLicenseLicenseTypeField: String get() = "License Type"
    override val driversLicenseLicenseTypeGraduatedLearnerPermit get() = "Graduated Learner Permit"
    override val driversLicenseLicenseTypeLearnerPermit get() = "Learner Permit"
    override val driversLicenseLicenseTypeFullLicense get() = "Full License"
    override val driversLicenseLicenseTypeFeedbackRequired get() = "License Type is required"
    override val driversLicensePhotoPlaceholder: String get() = "[photo]"
    override val driversLicenseApplicationHeading: String get() = "Drivers License Application"
    override val driversLicenseApplicationServiceSettings get() = "Drivers License Application Service Settings"
    override val driversLicenseApplicationServiceBuildingOnFireChance get() = "Building On Fire Chance"
    override val driversLicenseApplicationServiceSassChance get() = "Sass Chance"
    override val driversLicenseApplicationServiceLegallyProhibitedChance get() = "Legally Prohibited Chance"
    override val driversLicenseApplicationFormReset: String get() = "Reset"
    override val driversLicenseApplicationFormApply: String get() = "Apply"
    override val driversLicenseApplicationRejectionTitle: String get() = "Rejected"
    override val sassFormat get() = "%1\$s Try again another time."
    override val sassLeavingForBreak get() = "The clerk was just stepping out for a break."
    override val sassLooksAtBreakSignTapsReadsMagazine get() = "The clerk taps the \"On Break\" sign and continues reading a magazine."

    override val sassMealDeliveryJustArrived: String get() = "The clerk's meal delivery just arrived and they're not about to let it get cold."
    override val driversLicenseApplicationRejectionLegallyProhibited get() = "You are legally prohibited from obtaining a drivers license."

    override val validation = ValidationStringsImpl(EnglishUsValidationTranslation())

    override val ok get() = "OK"
}