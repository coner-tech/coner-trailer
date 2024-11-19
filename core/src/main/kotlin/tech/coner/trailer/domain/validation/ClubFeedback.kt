package tech.coner.trailer.domain.validation

import tech.coner.trailer.domain.entity.Club
import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.Severity

sealed class ClubFeedback : Feedback<Club> {

    override val severity = Severity.Error

    data object NameMustNotBeBlank : ClubFeedback() {
        override val property = Club::name
    }

    data object NameMustNotExceedMaxLength : ClubFeedback() {
        override val property = Club::name
    }
}