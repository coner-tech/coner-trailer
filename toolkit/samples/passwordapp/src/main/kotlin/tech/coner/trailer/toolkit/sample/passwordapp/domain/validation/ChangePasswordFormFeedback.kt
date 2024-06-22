package tech.coner.trailer.toolkit.sample.passwordapp.domain.validation

import tech.coner.trailer.toolkit.sample.passwordapp.domain.state.ChangePasswordFormState
import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.Severity
import kotlin.reflect.KProperty1

sealed class ChangePasswordFormFeedback : Feedback<ChangePasswordFormState> {
    data class MustNotBeEmpty(override val property: KProperty1<ChangePasswordFormState, *>) : ChangePasswordFormFeedback() {
        override val severity = Severity.Error
    }
    data object NewPasswordSameAsCurrentPassword : ChangePasswordFormFeedback() {
        override val property = ChangePasswordFormState::newPassword
        override val severity = Severity.Error
    }
    data class NewPasswordFeedback(val feedback: PasswordFeedback) : ChangePasswordFormFeedback() {
        override val property = ChangePasswordFormState::newPassword
        override val severity: Severity get() = feedback.severity
    }
    data object RepeatPasswordMismatch : ChangePasswordFormFeedback() {
        override val property = ChangePasswordFormState::newPasswordRepeated
        override val severity = Severity.Error
    }
}