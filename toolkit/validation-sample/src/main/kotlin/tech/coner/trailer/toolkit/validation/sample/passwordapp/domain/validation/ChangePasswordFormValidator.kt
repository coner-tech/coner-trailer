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
    on(ChangePasswordFormState::currentPassword) {
        if (it.isEmpty()) MustNotBeEmpty
        else null
    }
    on(ChangePasswordFormState::newPassword) {
        if (state.currentPassword.isNotEmpty() && it == state.currentPassword) NewPasswordSameAsCurrentPassword
        else null
    }
    on(ChangePasswordFormState::newPassword) {
        state.passwordPolicy.lengthThreshold(it.length, ::InsufficientLength)
    }
    on(ChangePasswordFormState::newPassword) {
        state.passwordPolicy.letterLowercaseThreshold(it.count { char -> char.isLowerCase() }, ::InsufficientLetterLowercase)
    }
    on(ChangePasswordFormState::newPassword) {
        state.passwordPolicy.letterUppercaseThreshold(it.count { char -> char.isUpperCase() }, ::InsufficientLetterUppercase)
    }
    on(ChangePasswordFormState::newPassword) {
        state.passwordPolicy.numericThreshold(it.count { char -> char.isDigit() }, ::InsufficientNumeric)
    }
    on(ChangePasswordFormState::newPassword) {
        state.passwordPolicy.specialThreshold(it.count { char -> !char.isLetterOrDigit() }, ::InsufficientSpecial)
    }
    on(ChangePasswordFormState::newPasswordRepeated) {
        if (state.newPassword != it) RepeatPasswordMismatch
        else null
    }
    null
}
