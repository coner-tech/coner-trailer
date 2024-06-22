package tech.coner.trailer.toolkit.validation.presentation.localization

import tech.coner.trailer.toolkit.validation.Severity

interface ValidationStrings {
    operator fun get(severity: Severity): String
}