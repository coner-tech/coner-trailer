package tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.validation

import tech.coner.trailer.toolkit.validation.Severity
import tech.coner.trailer.toolkit.validation.Severity.Error
import tech.coner.trailer.toolkit.validation.Severity.Warning
import tech.coner.trailer.toolkit.validation.Validator
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.entity.PasswordPolicy
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.state.ChangePasswordFormState
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.validation.ChangePasswordFormFeedback.*

typealias ChangePasswordFormValidator = Validator<Unit, ChangePasswordFormState, ChangePasswordFormFeedback>

val changePasswordFormValidator: ChangePasswordFormValidator get() = Validator {
    ChangePasswordFormState::currentPassword { currentPassword ->
        MustNotBeEmpty.takeIf { currentPassword.isEmpty() }
    }
    ChangePasswordFormState::newPassword { newPassword: String ->
        NewPasswordSameAsCurrentPassword.takeIf { input.currentPassword.isNotEmpty() && newPassword == input.currentPassword }
    }
    ChangePasswordFormState::newPassword.invoke(passwordValidator, { it.passwordPolicy }, ::NewPasswordFeedback)
    ChangePasswordFormState::newPasswordRepeated { newPasswordRepeated ->
        RepeatPasswordMismatch.takeIf { input.newPassword != newPasswordRepeated }
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