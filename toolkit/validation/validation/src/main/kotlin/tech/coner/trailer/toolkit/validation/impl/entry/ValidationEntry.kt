package tech.coner.trailer.toolkit.validation.impl.entry

import kotlin.reflect.KProperty1
import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.ValidationResult
import tech.coner.trailer.toolkit.validation.Validator
import tech.coner.trailer.toolkit.validation.rule.PropertyValidationRule
import tech.coner.trailer.toolkit.validation.rule.ValidationRule

internal sealed class ValidationEntry<CONTEXT, INPUT, FEEDBACK : Feedback> {

    internal class InputPropertySingleFeedback<CONTEXT, INPUT, PROPERTY, FEEDBACK : Feedback>(
        val property: KProperty1<INPUT, PROPERTY>,
        private val rule: PropertyValidationRule<CONTEXT, INPUT, PROPERTY, FEEDBACK>
    )
        : ValidationEntry<CONTEXT, INPUT, FEEDBACK>() {

        operator fun invoke(context: CONTEXT, input: INPUT): FEEDBACK? {
            return rule(context, input)
        }
    }

    internal class InputObjectSingleFeedback<CONTEXT, INPUT, FEEDBACK : Feedback>(
        private val rule: ValidationRule<CONTEXT, INPUT, FEEDBACK>
    ) : ValidationEntry<CONTEXT, INPUT, FEEDBACK>() {

        operator fun invoke(context: CONTEXT, input: INPUT): FEEDBACK? {
            return rule(context, input)
        }
    }

    internal class InputPropertyDelegatesToValidator<CONTEXT, INPUT, PROPERTY, DELEGATE_CONTEXT, DELEGATE_FEEDBACK : Feedback, FEEDBACK : Feedback>(
        private val property: KProperty1<INPUT, PROPERTY>,
        private val validator: Validator<DELEGATE_CONTEXT, PROPERTY, DELEGATE_FEEDBACK>,
        private val mapContextFn: CONTEXT.(INPUT) -> DELEGATE_CONTEXT,
        private val mapFeedbackFn: (DELEGATE_FEEDBACK) -> FEEDBACK,
    )
        : ValidationEntry<CONTEXT, INPUT, FEEDBACK>() {

        operator fun invoke(context: CONTEXT, input: INPUT): ValidationResult<INPUT, FEEDBACK> {
            return ValidationResult(
                validator(
                    context = mapContextFn(context, input),
                    input = property.get(input)
                )
                    .feedback
                    .flatMap { it.value.map(mapFeedbackFn) }
                    .let {
                        if (it.isNotEmpty()) {
                            mapOf(property to it)
                        } else {
                            emptyMap()
                        }
                    }
            )
        }
    }

    internal class InputObjectDelegatesToValidator<CONTEXT, INPUT, DELEGATE_CONTEXT, DELEGATE_INPUT, DELEGATE_FEEDBACK : Feedback, FEEDBACK : Feedback>(
        private val validator: Validator<DELEGATE_CONTEXT, DELEGATE_INPUT, DELEGATE_FEEDBACK>,
        private val mapContextFn: CONTEXT.(INPUT) -> DELEGATE_CONTEXT,
        private val mapInputFn: CONTEXT.(INPUT) -> DELEGATE_INPUT,
        private val mapFeedbackKeys: Map<KProperty1<DELEGATE_INPUT, *>?, KProperty1<INPUT, *>?>,
        private val mapFeedbackObjectFn: (DELEGATE_FEEDBACK) -> FEEDBACK
    ) : ValidationEntry<CONTEXT, INPUT, FEEDBACK>() {
        operator fun invoke(context: CONTEXT, input: INPUT): ValidationResult<INPUT, FEEDBACK> {
            return ValidationResult(
                validator(
                    context = mapContextFn(context, input),
                    input = mapInputFn(context, input)
                )
                    .feedback
                    .map { mapFeedbackKeys[it.key] to it.value.map(mapFeedbackObjectFn) }
                    .toMap()
            )
        }
    }

    internal class ReturnEarlyIfAny<CONTEXT, INPUT, FEEDBACK : Feedback>(
        private val matchFn: (FEEDBACK) -> Boolean
    )
        : ValidationEntry<CONTEXT, INPUT, FEEDBACK>() {

        operator fun invoke(feedback: Map<KProperty1<INPUT, *>?, List<FEEDBACK>>): Boolean {
            return feedback
                .values
                .flatten()
                .any { matchFn(it) }
        }
    }
}