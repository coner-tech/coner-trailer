package tech.coner.trailer.toolkit.validation

import kotlin.reflect.KProperty1
import tech.coner.trailer.toolkit.validation.context.ValidationRuleContext
import tech.coner.trailer.toolkit.validation.impl.ValidatorImpl


fun <CONTEXT, INPUT, FEEDBACK : Feedback> Validator(
    function: Validator.Builder<CONTEXT, INPUT, FEEDBACK>.() -> Unit
): Validator<CONTEXT, INPUT, FEEDBACK> {
    return ValidatorImpl(function)
}

interface Validator<CONTEXT, INPUT, FEEDBACK : Feedback> {
    operator fun invoke(context: CONTEXT, input: INPUT): ValidationResult<INPUT, FEEDBACK>

    interface Builder<CONTEXT, INPUT, FEEDBACK : Feedback> {

        operator fun <PROPERTY> KProperty1<INPUT, PROPERTY>.invoke(
            ruleFn: ValidationRuleContext<CONTEXT, INPUT>.(PROPERTY) -> FEEDBACK?
        )

        operator fun <PROPERTY> KProperty1<INPUT, PROPERTY>.invoke(
            vararg ruleFns: ValidationRuleContext<CONTEXT, INPUT>.(PROPERTY) -> FEEDBACK?
        )

        operator fun <PROPERTY, DELEGATE_CONTEXT, DELEGATE_FEEDBACK : Feedback> KProperty1<INPUT, PROPERTY>.invoke(
            validator: Validator<DELEGATE_CONTEXT, PROPERTY, DELEGATE_FEEDBACK>,
            mapContextFn: CONTEXT.(INPUT) -> DELEGATE_CONTEXT,
            mapFeedbackFn: (DELEGATE_FEEDBACK) -> FEEDBACK
        )

        fun input(
            ruleFn: ValidationRuleContext<CONTEXT, INPUT>.(INPUT) -> FEEDBACK?
        )

        fun input(
            vararg ruleFns: ValidationRuleContext<CONTEXT, INPUT>.(INPUT) -> FEEDBACK?
        )
    }
}

operator fun <INPUT, FEEDBACK : Feedback> Validator<Unit, INPUT, FEEDBACK>.invoke(input: INPUT): ValidationResult<INPUT, FEEDBACK> {
    return invoke(Unit, input)
}
