package tech.coner.trailer.toolkit.sample.dmvapp.presentation.localization

import tech.coner.trailer.toolkit.validation.presentation.localization.ValidationStrings

interface Translation {

    val conerLogoContentDescription: String
    val dmvLabel: String
    val dmvMotto: String

    val driversLicenseHeading: String
    val driversLicenseNameField: String
    val driversLicenseNameFeedbackRequired: String
    val driversLicenseNameFeedbackNotBlank: String
    val driversLicenseAgeField: String
    val driversLicenseAgeFeedbackRequired: String
    val driversLicenseAgeFeedbackTooYoung: String
    val driversLicenseAgeFeedbackTooYoungSuggestOtherLicenseTypeWhenAgedFormat: String
    val driversLicenseAgeFeedbackTooYoungSuggestReapplyWhenAgedFormat: String
    val driversLicenseAgeFeedbackTooYoungSuggestOtherLicenseTypeFormat: String
    val driversLicenseAgeFeedbackTooYoungSuggestionFormat: String
    val driversLicenseAgeFeedbackTooOld: String
    val driversLicenseAgeFeedbackTooOldSuggestOtherLicenseTypeFormat: String
    val driversLicenseLicenseTypeField: String
    val driversLicenseLicenseTypeGraduatedLearnerPermit: String
    val driversLicenseLicenseTypeLearnerPermit: String
    val driversLicenseLicenseTypeFullLicense: String
    val driversLicenseLicenseTypeFeedbackRequired: String
    val driversLicensePhotoPlaceholder: String
    val driversLicenseApplicationHeading: String
    val driversLicenseApplicationServiceSettings: String
    val driversLicenseApplicationServiceBuildingOnFireChance: String
    val driversLicenseApplicationServiceSassChance: String
    val driversLicenseApplicationServiceLegallyProhibitedChance: String
    val driversLicenseApplicationFormReset: String
    val driversLicenseApplicationFormApply: String

    val driversLicenseApplicationRejectionTitle: String

    val sassFormat: String
    val sassLeavingForBreak: String
    val sassLooksAtBreakSignTapsReadsMagazine: String
    val sassMealDeliveryJustArrived: String

    val driversLicenseApplicationRejectionLegallyProhibited: String

    val driversLicenseGranted: String

    val menuContentDescription: String
    val settings: String
    val settingsThemeTitle: String
    val settingsThemeModeTitle: String
    val settingsThemeModeAuto: String
    val settingsThemeModeLight: String
    val settingsThemeModeDark: String

    val validation: ValidationStrings

    val ok: String
}