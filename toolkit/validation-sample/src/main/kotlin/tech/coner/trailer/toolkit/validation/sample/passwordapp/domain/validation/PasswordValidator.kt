package tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.validation

import tech.coner.trailer.toolkit.validation.Severity
import tech.coner.trailer.toolkit.validation.Severity.Error
import tech.coner.trailer.toolkit.validation.Severity.Warning
import tech.coner.trailer.toolkit.validation.Validator
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.entity.PasswordPolicy
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.validation.PasswordFeedback.*

typealias PasswordValidator = Validator<PasswordPolicy, String, PasswordFeedback>

val passwordValidator: PasswordValidator get() = Validator {
    input(
        { context.lengthThreshold(it.length, ::InsufficientLength) },
        { context.letterLowercaseThreshold(it.count { char -> char.isLowerCase() }, ::InsufficientLetterLowercase ) },
        { context.letterUppercaseThreshold(it.count { char -> char.isUpperCase() }, ::InsufficientLetterUppercase ) },
        { context.numericThreshold(it.count { char -> char.isDigit() }, ::InsufficientNumeric ) },
        { context.specialThreshold(it.count { char -> !char.isLetterOrDigit() }, ::InsufficientSpecial ) }
    )
}

private operator fun PasswordPolicy.MinimumThreshold.invoke(
    value: Int,
    feedbackFn: (Severity) -> PasswordFeedback
): PasswordFeedback? {
    val severity = when {
        value < minForError -> Error
        value < minForWarning -> Warning
        else -> null
    }
    return severity?.let(feedbackFn)
}
