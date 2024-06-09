package tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.validation

import tech.coner.trailer.toolkit.validation.Severity
import tech.coner.trailer.toolkit.validation.Severity.Error
import tech.coner.trailer.toolkit.validation.Severity.Warning
import tech.coner.trailer.toolkit.validation.Validator
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.entity.PasswordPolicy
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.validation.ChangePasswordFormFeedback.*
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.state.ChangePasswordFormState

fun changePasswordFormValidator() = Validator<ChangePasswordFormState, ChangePasswordFormFeedback> { state ->
    operator fun PasswordPolicy.MinimumThreshold.invoke(
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
    on(ChangePasswordFormState::currentPassword) { currentPassword ->
        MustNotBeEmpty.takeIf { currentPassword.isEmpty() }
    }
    on(
        ChangePasswordFormState::newPassword,
        { newPassword ->
            NewPasswordSameAsCurrentPassword.takeIf { state.currentPassword.isNotEmpty() && newPassword == state.currentPassword }
        },
        { state.passwordPolicy.lengthThreshold(it.length, ::InsufficientLength) },
        { state.passwordPolicy.letterLowercaseThreshold(it.count { char -> char.isLowerCase() }, ::InsufficientLetterLowercase) },
        { state.passwordPolicy.letterUppercaseThreshold(it.count { char -> char.isUpperCase() }, ::InsufficientLetterUppercase) },
        { state.passwordPolicy.numericThreshold(it.count { char -> char.isDigit() }, ::InsufficientNumeric) },
        { state.passwordPolicy.specialThreshold(it.count { char -> !char.isLetterOrDigit() }, ::InsufficientSpecial) }
    )
    on(ChangePasswordFormState::newPasswordRepeated) { newPasswordRepeated ->
        RepeatPasswordMismatch.takeIf { state.newPassword != newPasswordRepeated }
    }
    null
}
