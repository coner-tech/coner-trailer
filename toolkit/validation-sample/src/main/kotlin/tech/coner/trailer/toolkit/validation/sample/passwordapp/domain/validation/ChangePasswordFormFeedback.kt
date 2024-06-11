package tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.validation

import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.Severity

sealed class ChangePasswordFormFeedback : Feedback {
    data object MustNotBeEmpty : ChangePasswordFormFeedback() {
        override val severity = Severity.Error
    }
    data object NewPasswordSameAsCurrentPassword : ChangePasswordFormFeedback() {
        override val severity = Severity.Error
    }
    data class NewPasswordFeedback(val feedback: PasswordFeedback) : ChangePasswordFormFeedback() {
        override val severity: Severity get() = feedback.severity
    }
    data object RepeatPasswordMismatch : ChangePasswordFormFeedback() {
        override val severity = Severity.Error
    }
}