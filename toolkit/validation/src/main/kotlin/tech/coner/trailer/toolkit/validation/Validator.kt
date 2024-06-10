package tech.coner.trailer.toolkit.validation

import kotlin.reflect.KProperty1
import tech.coner.trailer.toolkit.validation.impl.ValidatorImpl


fun <INPUT, FEEDBACK : Feedback> Validator(function: Validator.Builder<INPUT, FEEDBACK>.() -> Unit): Validator<INPUT, FEEDBACK> {
    return ValidatorImpl(function)
}

interface Validator<INPUT, FEEDBACK : Feedback> {
    operator fun invoke(input: INPUT): ValidationResult<FEEDBACK>


    interface Builder<INPUT, FEEDBACK : Feedback> {

        fun <PROPERTY> on(
            property: KProperty1<INPUT, PROPERTY>,
            ruleFn: INPUT.(PROPERTY) -> FEEDBACK?
        )

        fun <PROPERTY> on(
            property: KProperty1<INPUT, PROPERTY>,
            vararg ruleFns: INPUT.(PROPERTY) -> FEEDBACK?
        )
    }
}