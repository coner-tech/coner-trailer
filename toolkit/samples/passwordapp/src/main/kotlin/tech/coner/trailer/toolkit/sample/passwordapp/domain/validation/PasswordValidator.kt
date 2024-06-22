package tech.coner.trailer.toolkit.sample.passwordapp.domain.validation

import tech.coner.trailer.toolkit.sample.passwordapp.domain.entity.Password
import tech.coner.trailer.toolkit.sample.passwordapp.domain.entity.PasswordPolicy
import tech.coner.trailer.toolkit.sample.passwordapp.domain.validation.PasswordFeedback.InsufficientLength
import tech.coner.trailer.toolkit.sample.passwordapp.domain.validation.PasswordFeedback.InsufficientLetterLowercase
import tech.coner.trailer.toolkit.sample.passwordapp.domain.validation.PasswordFeedback.InsufficientLetterUppercase
import tech.coner.trailer.toolkit.sample.passwordapp.domain.validation.PasswordFeedback.InsufficientNumeric
import tech.coner.trailer.toolkit.sample.passwordapp.domain.validation.PasswordFeedback.InsufficientSpecial
import tech.coner.trailer.toolkit.validation.Severity
import tech.coner.trailer.toolkit.validation.Severity.Error
import tech.coner.trailer.toolkit.validation.Severity.Warning
import tech.coner.trailer.toolkit.validation.Validator

typealias PasswordValidator = Validator<PasswordPolicy, Password, PasswordFeedback>

fun PasswordValidator(): PasswordValidator = Validator {
    input(
        { context.lengthThreshold(it.value.length, ::InsufficientLength) },
        { context.letterLowercaseThreshold(it.value.count { char -> char.isLowerCase() }, ::InsufficientLetterLowercase ) },
        { context.letterUppercaseThreshold(it.value.count { char -> char.isUpperCase() }, ::InsufficientLetterUppercase ) },
        { context.numericThreshold(it.value.count { char -> char.isDigit() }, ::InsufficientNumeric ) },
        { context.specialThreshold(it.value.count { char -> !char.isLetterOrDigit() }, ::InsufficientSpecial ) }
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
