package tech.coner.trailer.toolkit.validation.impl.entry

import kotlin.reflect.KProperty1
import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.ValidationOutcome
import tech.coner.trailer.toolkit.validation.Validator
import tech.coner.trailer.toolkit.validation.rule.PropertyValidationRule
import tech.coner.trailer.toolkit.validation.rule.ValidationRule

internal sealed class ValidationEntry<CONTEXT, INPUT, FEEDBACK : Feedback<INPUT>> {

    internal class InputPropertySingleFeedback<CONTEXT, INPUT, PROPERTY, FEEDBACK : Feedback<INPUT>>(
        val property: KProperty1<INPUT, PROPERTY>,
        private val rule: PropertyValidationRule<CONTEXT, INPUT, PROPERTY, FEEDBACK>
    )
        : ValidationEntry<CONTEXT, INPUT, FEEDBACK>() {

        operator fun invoke(context: CONTEXT, input: INPUT): FEEDBACK? {
            return rule(context, input)
        }
    }

    internal class InputObjectSingleFeedback<CONTEXT, INPUT, FEEDBACK : Feedback<INPUT>>(
        private val rule: ValidationRule<CONTEXT, INPUT, FEEDBACK>
    ) : ValidationEntry<CONTEXT, INPUT, FEEDBACK>() {

        operator fun invoke(context: CONTEXT, input: INPUT): FEEDBACK? {
            return rule(context, input)
        }
    }

    internal class InputPropertyDelegatesToValidator<CONTEXT, INPUT, PROPERTY, DELEGATE_CONTEXT, DELEGATE_FEEDBACK : Feedback<PROPERTY>, FEEDBACK : Feedback<INPUT>>(
        private val property: KProperty1<INPUT, PROPERTY>,
        private val validator: Validator<DELEGATE_CONTEXT, PROPERTY, DELEGATE_FEEDBACK>,
        private val mapContextFn: CONTEXT.(INPUT) -> DELEGATE_CONTEXT,
        private val mapFeedbackFn: (DELEGATE_FEEDBACK) -> FEEDBACK,
    )
        : ValidationEntry<CONTEXT, INPUT, FEEDBACK>() {

        operator fun invoke(context: CONTEXT, input: INPUT): ValidationOutcome<INPUT, FEEDBACK> {
            return ValidationOutcome(
                validator(
                    context = mapContextFn(context, input),
                    input = property.get(input)
                )
                    .feedback
                    .map { mapFeedbackFn(it) }
            )
        }
    }

    internal class InputObjectDelegatesToOtherTypeValidator<CONTEXT, INPUT, DELEGATE_CONTEXT, DELEGATE_INPUT, DELEGATE_FEEDBACK : Feedback<DELEGATE_INPUT>, FEEDBACK : Feedback<INPUT>>(
        private val validator: Validator<DELEGATE_CONTEXT, DELEGATE_INPUT, DELEGATE_FEEDBACK>,
        private val mapContextFn: CONTEXT.(INPUT) -> DELEGATE_CONTEXT,
        private val mapInputFn: CONTEXT.(INPUT) -> DELEGATE_INPUT,
        private val mapFeedbackObjectFn: (DELEGATE_FEEDBACK) -> FEEDBACK
    ) : ValidationEntry<CONTEXT, INPUT, FEEDBACK>() {
        operator fun invoke(context: CONTEXT, input: INPUT): ValidationOutcome<INPUT, FEEDBACK> {
            return ValidationOutcome(
                validator(
                    context = mapContextFn(context, input),
                    input = mapInputFn(context, input)
                )
                    .feedback
                    .map { mapFeedbackObjectFn(it) }
            )
        }
    }

    internal class InputObjectDelegatesToSameTypeValidator<CONTEXT, INPUT, FEEDBACK : Feedback<INPUT>>(
        private val validator: Validator<CONTEXT, INPUT, FEEDBACK>,
    ) : ValidationEntry<CONTEXT, INPUT, FEEDBACK>() {
        operator fun invoke(context: CONTEXT, input: INPUT): ValidationOutcome<INPUT, FEEDBACK> {
            return validator(context, input)
        }
    }

    internal class ReturnEarlyIfAny<CONTEXT, INPUT, FEEDBACK : Feedback<INPUT>>(
        private val matchFn: (FEEDBACK) -> Boolean
    )
        : ValidationEntry<CONTEXT, INPUT, FEEDBACK>() {

        operator fun invoke(feedback: List<FEEDBACK>): Boolean {
            return feedback
                .any { matchFn(it) }
        }
    }
}