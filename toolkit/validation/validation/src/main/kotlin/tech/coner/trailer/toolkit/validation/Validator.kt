package tech.coner.trailer.toolkit.validation

import tech.coner.trailer.toolkit.validation.context.PropertyValidationRuleContext
import kotlin.reflect.KProperty1
import tech.coner.trailer.toolkit.validation.context.ValidationRuleContext
import tech.coner.trailer.toolkit.validation.impl.ValidatorImpl


fun <CONTEXT, INPUT, FEEDBACK : Feedback<INPUT>> Validator(
    function: Validator.Builder<CONTEXT, INPUT, FEEDBACK>.() -> Unit
): Validator<CONTEXT, INPUT, FEEDBACK> {
    return ValidatorImpl(function)
}

interface Validator<CONTEXT, INPUT, FEEDBACK : Feedback<INPUT>> {
    operator fun invoke(context: CONTEXT, input: INPUT): ValidationOutcome<INPUT, FEEDBACK>

    interface Builder<CONTEXT, INPUT, FEEDBACK : Feedback<INPUT>> {

        operator fun <PROPERTY> KProperty1<INPUT, PROPERTY>.invoke(
            ruleFn: PropertyValidationRuleContext<CONTEXT, INPUT>.(PROPERTY) -> FEEDBACK?
        )

        operator fun <PROPERTY> KProperty1<INPUT, PROPERTY>.invoke(
            vararg ruleFns: ValidationRuleContext<CONTEXT, INPUT>.(PROPERTY) -> FEEDBACK?
        )

        operator fun <PROPERTY, DELEGATE_CONTEXT, DELEGATE_FEEDBACK : Feedback<PROPERTY>> KProperty1<INPUT, PROPERTY>.invoke(
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

        fun input(
            validator: Validator<CONTEXT, INPUT, FEEDBACK>
        )

        fun <DELEGATE_CONTEXT, DELEGATE_INPUT, DELEGATE_FEEDBACK : Feedback<DELEGATE_INPUT>> input(
            otherTypeValidator: Validator<DELEGATE_CONTEXT, DELEGATE_INPUT, DELEGATE_FEEDBACK>,
            mapContextFn: CONTEXT.(INPUT) -> DELEGATE_CONTEXT,
            mapInputFn: CONTEXT.(INPUT) -> DELEGATE_INPUT,
            mapFeedbackObjectFn: (DELEGATE_FEEDBACK) -> FEEDBACK
        )

        fun returnEarlyIfAny(matchFn: (FEEDBACK) -> Boolean)
    }
}

/**
 * Convenience for invoking Unit-context validators, omitting the redundant Unit context parameter
 */
operator fun <INPUT, FEEDBACK : Feedback<INPUT>> Validator<Unit, INPUT, FEEDBACK>.invoke(input: INPUT): ValidationOutcome<INPUT, FEEDBACK> {
    return invoke(Unit, input)
}

/**
 * Convenience for delegating object validation from one Unit-context validator to another
 */
fun <INPUT, FEEDBACK : Feedback<INPUT>, DELEGATE_INPUT, DELEGATE_FEEDBACK : Feedback<DELEGATE_INPUT>> Validator.Builder<Unit, INPUT, FEEDBACK>.input(
    validator: Validator<Unit, DELEGATE_INPUT, DELEGATE_FEEDBACK>,
    mapInputFn: Unit.(INPUT) -> DELEGATE_INPUT,
    mapFeedbackObjectFn: (DELEGATE_FEEDBACK) -> FEEDBACK
) = input(
    otherTypeValidator = validator,
    mapContextFn = { },
    mapInputFn = mapInputFn,
    mapFeedbackObjectFn = mapFeedbackObjectFn
)