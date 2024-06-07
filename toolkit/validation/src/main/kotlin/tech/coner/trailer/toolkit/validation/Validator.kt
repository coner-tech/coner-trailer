package tech.coner.trailer.toolkit.validation

import tech.coner.trailer.toolkit.validation.impl.ValidatorImpl

fun <INPUT, FEEDBACK : Feedback> Validator(function: ValidationContext<INPUT, FEEDBACK>.(INPUT) -> FEEDBACK?): Validator<INPUT, FEEDBACK> {
    return ValidatorImpl(function)
}

interface Validator<INPUT, FEEDBACK : Feedback> {
    operator fun invoke(input: INPUT): ValidationResult<FEEDBACK>
}