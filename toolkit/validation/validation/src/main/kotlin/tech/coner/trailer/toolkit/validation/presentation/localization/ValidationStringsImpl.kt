package tech.coner.trailer.toolkit.validation.presentation.localization

import tech.coner.trailer.toolkit.validation.Severity

class ValidationStringsImpl(translation: ValidationTranslation)
    : ValidationStrings,
    ValidationTranslation by translation {

    override fun get(severity: Severity) = when (severity) {
        Severity.Error -> severityError
        Severity.Warning -> severityWarning
        Severity.Success -> severitySuccess
        Severity.Info -> severityInfo
    }
}