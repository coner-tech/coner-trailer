package tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.validation

import tech.coner.trailer.toolkit.validation.Severity
import tech.coner.trailer.toolkit.validation.Severity.Error
import tech.coner.trailer.toolkit.validation.Severity.Warning
import tech.coner.trailer.toolkit.validation.Validator
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.entity.PasswordPolicy
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.state.ChangePasswordFormState
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.validation.ChangePasswordFormFeedback.InsufficientLength
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.validation.ChangePasswordFormFeedback.InsufficientLetterLowercase
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.validation.ChangePasswordFormFeedback.InsufficientLetterUppercase
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.validation.ChangePasswordFormFeedback.InsufficientNumeric
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.validation.ChangePasswordFormFeedback.InsufficientSpecial
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.validation.ChangePasswordFormFeedback.MustNotBeEmpty
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.validation.ChangePasswordFormFeedback.NewPasswordSameAsCurrentPassword
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.validation.ChangePasswordFormFeedback.RepeatPasswordMismatch

typealias ChangePasswordFormValidator = Validator<ChangePasswordFormState, ChangePasswordFormFeedback>

val changePasswordFormValidator: ChangePasswordFormValidator get() = Validator {
    on(ChangePasswordFormState::currentPassword) { currentPassword ->
        MustNotBeEmpty.takeIf { currentPassword.isEmpty() }
    }
    on(
        ChangePasswordFormState::newPassword,
        { newPassword ->
            NewPasswordSameAsCurrentPassword.takeIf { currentPassword.isNotEmpty() && newPassword == currentPassword }
        },
        { passwordPolicy.lengthThreshold(it.length, ::InsufficientLength) },
        { passwordPolicy.letterLowercaseThreshold(it.count { char -> char.isLowerCase() }, ::InsufficientLetterLowercase) },
        { passwordPolicy.letterUppercaseThreshold(it.count { char -> char.isUpperCase() }, ::InsufficientLetterUppercase) },
        { passwordPolicy.numericThreshold(it.count { char -> char.isDigit() }, ::InsufficientNumeric) },
        { passwordPolicy.specialThreshold(it.count { char -> !char.isLetterOrDigit() }, ::InsufficientSpecial) }
    )
    on(ChangePasswordFormState::newPasswordRepeated) { newPasswordRepeated ->
        RepeatPasswordMismatch.takeIf { newPassword != newPasswordRepeated }
    }
}

private operator fun PasswordPolicy.MinimumThreshold.invoke(
    value: Int,
    feedbackFn: (Severity) -> ChangePasswordFormFeedback
): ChangePasswordFormFeedback? {
    val severity = when {
        value < minForError -> Error
        value < minForWarning -> Warning
        else -> null
    }
    return severity?.let(feedbackFn)
}