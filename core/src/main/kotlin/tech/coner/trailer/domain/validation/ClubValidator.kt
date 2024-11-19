package tech.coner.trailer.domain.validation

import tech.coner.trailer.domain.entity.Club
import tech.coner.trailer.domain.validation.ClubFeedback.NameMustNotBeBlank
import tech.coner.trailer.domain.validation.ClubFeedback.NameMustNotExceedMaxLength
import tech.coner.trailer.toolkit.validation.Validator

typealias ClubValidator = Validator<Unit, Club, ClubFeedback>

fun ClubValidator() = Validator<Unit, Club, ClubFeedback> {
    Club::name { name -> NameMustNotBeBlank.takeUnless { name.isNotBlank() } }
    Club::name { name -> NameMustNotExceedMaxLength.takeUnless { name.length <= Club.NAME_MAX_LENGTH } }
}
