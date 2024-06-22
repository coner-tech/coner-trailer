package tech.coner.trailer.toolkit.validation.impl

import kotlin.reflect.KProperty1
import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.ValidationOutcome
import tech.coner.trailer.toolkit.validation.Validator
import tech.coner.trailer.toolkit.validation.context.PropertyValidationRuleContext
import tech.coner.trailer.toolkit.validation.context.ValidationRuleContext
import tech.coner.trailer.toolkit.validation.impl.entry.ValidationEntry
import tech.coner.trailer.toolkit.validation.impl.rule.ObjectValidationRuleImpl
import tech.coner.trailer.toolkit.validation.impl.rule.PropertyValidationRuleImpl

internal class ValidatorImpl<CONTEXT, INPUT, FEEDBACK : Feedback<INPUT>>(
    builder: Validator.Builder<CONTEXT, INPUT, FEEDBACK>.() -> Unit
) : Validator<CONTEXT, INPUT, FEEDBACK> {

    private val entries: List<ValidationEntry<CONTEXT, INPUT, FEEDBACK>>

    init {
        BuilderImpl<CONTEXT, INPUT, FEEDBACK>()
            .apply(builder)
            .also { entries = it.entries }
    }

    override fun invoke(context: CONTEXT, input: INPUT): ValidationOutcome<INPUT, FEEDBACK> {
        val feedback: MutableList<FEEDBACK> = mutableListOf()
        for (entry in entries) {
            when (entry) {
                is ValidationEntry.InputPropertySingleFeedback<CONTEXT, INPUT, *, FEEDBACK> -> {
                    entry(context, input)
                        ?.also { feedback += it }
                }

                is ValidationEntry.InputObjectSingleFeedback -> {
                    entry(context, input)
                        ?.also { feedback += it }
                }

                is ValidationEntry.InputPropertyDelegatesToValidator<CONTEXT, INPUT, *, *, *, FEEDBACK> -> {
                    entry(context, input)
                        .also { feedback.addAll(it.feedback) }
                }

                is ValidationEntry.InputObjectDelegatesToOtherTypeValidator<CONTEXT, INPUT, *, *, *, FEEDBACK> -> {
                    entry(context, input)
                        .also { feedback += it.feedback }
                }

                is ValidationEntry.InputObjectDelegatesToSameTypeValidator<CONTEXT, INPUT, FEEDBACK> -> {
                    entry(context, input)
                        .also { feedback += it.feedback }
                }

                is ValidationEntry.ReturnEarlyIfAny<CONTEXT, INPUT, FEEDBACK> -> {
                    if (entry(feedback)) {
                        break
                    }
                }
            }
        }
        return ValidationOutcome(feedback)
    }

    internal class BuilderImpl<CONTEXT, INPUT, FEEDBACK : Feedback<INPUT>> : Validator.Builder<CONTEXT, INPUT, FEEDBACK> {

        internal val entries: MutableList<ValidationEntry<CONTEXT, INPUT, FEEDBACK>> = mutableListOf()

        override fun <PROPERTY> KProperty1<INPUT, PROPERTY>.invoke(
            ruleFn: PropertyValidationRuleContext<CONTEXT, INPUT>.(PROPERTY) -> FEEDBACK?
        ) {
            entries += ValidationEntry.InputPropertySingleFeedback(
                property = this,
                rule = PropertyValidationRuleImpl(this, ruleFn),
            )
        }

        override fun <PROPERTY> KProperty1<INPUT, PROPERTY>.invoke(
            vararg ruleFns: ValidationRuleContext<CONTEXT, INPUT>.(PROPERTY) -> FEEDBACK?
        ) {
            entries += ruleFns
                .map {
                    ValidationEntry.InputPropertySingleFeedback(
                        property = this,
                        rule = PropertyValidationRuleImpl(this, it)
                    )
                }
        }

        override fun <PROPERTY, DELEGATE_CONTEXT, DELEGATE_FEEDBACK : Feedback<PROPERTY>> KProperty1<INPUT, PROPERTY>.invoke(
            validator: Validator<DELEGATE_CONTEXT, PROPERTY, DELEGATE_FEEDBACK>,
            mapContextFn: CONTEXT.(INPUT) -> DELEGATE_CONTEXT,
            mapFeedbackFn: (DELEGATE_FEEDBACK) -> FEEDBACK
        ) {
            entries += ValidationEntry.InputPropertyDelegatesToValidator(
                property = this,
                validator = validator,
                mapContextFn = mapContextFn,
                mapFeedbackFn = mapFeedbackFn
            )
        }

        override fun input(ruleFn: ValidationRuleContext<CONTEXT, INPUT>.(INPUT) -> FEEDBACK?) {
            entries += ValidationEntry.InputObjectSingleFeedback(
                ObjectValidationRuleImpl(ruleFn)
            )
        }

        override fun input(vararg ruleFns: ValidationRuleContext<CONTEXT, INPUT>.(INPUT) -> FEEDBACK?) {
            entries += ruleFns
                .map {
                    ValidationEntry.InputObjectSingleFeedback(
                        ObjectValidationRuleImpl(it)
                    )
                }
        }

        override fun <DELEGATE_CONTEXT, DELEGATE_INPUT, DELEGATE_FEEDBACK : Feedback<DELEGATE_INPUT>> input(
            otherTypeValidator: Validator<DELEGATE_CONTEXT, DELEGATE_INPUT, DELEGATE_FEEDBACK>,
            mapContextFn: CONTEXT.(INPUT) -> DELEGATE_CONTEXT,
            mapInputFn: CONTEXT.(INPUT) -> DELEGATE_INPUT,
            mapFeedbackObjectFn: (DELEGATE_FEEDBACK) -> FEEDBACK
        ) {
            entries += ValidationEntry.InputObjectDelegatesToOtherTypeValidator(
                validator = otherTypeValidator,
                mapContextFn = mapContextFn,
                mapInputFn = mapInputFn,
                mapFeedbackObjectFn = mapFeedbackObjectFn
            )
        }

        override fun input(validator: Validator<CONTEXT, INPUT, FEEDBACK>) {
            entries += ValidationEntry.InputObjectDelegatesToSameTypeValidator(validator)
        }

        override fun returnEarlyIfAny(matchFn: (FEEDBACK) -> Boolean) {
            entries += ValidationEntry.ReturnEarlyIfAny(matchFn)
        }
    }
}
