package tech.coner.trailer.toolkit.sample.passwordapp.domain.validation

import tech.coner.trailer.toolkit.sample.passwordapp.domain.entity.Password
import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.Severity

sealed class PasswordFeedback : Feedback<Password> {
    override val property = null
    data class InsufficientLength(override val severity: Severity) : PasswordFeedback()
    data class InsufficientLetterLowercase(override val severity: Severity) : PasswordFeedback()
    data class InsufficientLetterUppercase(override val severity: Severity) : PasswordFeedback()
    data class InsufficientNumeric(override val severity: Severity) : PasswordFeedback()
    data class InsufficientSpecial(override val severity: Severity) : PasswordFeedback()
}