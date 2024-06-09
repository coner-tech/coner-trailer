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
    data class InsufficientLength(override val severity: Severity) : ChangePasswordFormFeedback()
    data class InsufficientLetterLowercase(override val severity: Severity) : ChangePasswordFormFeedback()
    data class InsufficientLetterUppercase(override val severity: Severity) : ChangePasswordFormFeedback()
    data class InsufficientNumeric(override val severity: Severity) : ChangePasswordFormFeedback()
    data class InsufficientSpecial(override val severity: Severity) : ChangePasswordFormFeedback()
    data object RepeatPasswordMismatch : ChangePasswordFormFeedback() {
        override val severity = Severity.Error
    }
}