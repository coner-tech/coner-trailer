package tech.coner.trailer.toolkit.validation.impl

import tech.coner.trailer.toolkit.validation.*
import tech.coner.trailer.toolkit.validation.context.ValidationRuleContext
import tech.coner.trailer.toolkit.validation.impl.entry.ValidationEntry
import tech.coner.trailer.toolkit.validation.impl.rule.ObjectValidationRuleImpl
import tech.coner.trailer.toolkit.validation.impl.rule.PropertyValidationRuleImpl
import kotlin.reflect.KProperty1

internal class ValidatorImpl<CONTEXT, INPUT, FEEDBACK : Feedback>(
    builder: Validator.Builder<CONTEXT, INPUT, FEEDBACK>.() -> Unit
) : Validator<CONTEXT, INPUT, FEEDBACK> {

    private val entries: List<ValidationEntry<CONTEXT, INPUT, FEEDBACK>>

    init {
        BuilderImpl<CONTEXT, INPUT, FEEDBACK>()
            .apply(builder)
            .also { entries = it.entries }
    }

    override fun invoke(context: CONTEXT, input: INPUT): ValidationResult<INPUT, FEEDBACK> {
        val feedback: MutableMap<KProperty1<INPUT, *>?, MutableList<FEEDBACK>> = mutableMapOf()
        for (entry in entries) {
            when (entry) {
                is ValidationEntry.InputPropertySingleFeedback<CONTEXT, INPUT, *, FEEDBACK> -> {
                    entry(context, input)
                        ?.also { feedback.createOrAppend(entry.property, it) }
                }

                is ValidationEntry.InputObjectSingleFeedback -> {
                    entry(context, input)
                        ?.also { feedback.createOrAppend(null, it) }
                }

                is ValidationEntry.InputPropertyDelegatesToValidator<CONTEXT, INPUT, *, *, *, FEEDBACK> -> {
                    entry(context, input)
                        .also { feedback.createOrAppend(it.feedback) }
                }

                is ValidationEntry.InputObjectDelegatesToValidator<CONTEXT, INPUT, *, *, *, FEEDBACK> -> {
                    entry(context, input)
                        .also { feedback.createOrAppend(it.feedback) }
                }

                is ValidationEntry.ReturnEarlyIfAny<CONTEXT, INPUT, FEEDBACK> -> {
                    if (entry(feedback.toMap())) {
                        break
                    }
                }
            }
        }
        return ValidationResult(feedback.toMap())
    }

    internal class BuilderImpl<CONTEXT, INPUT, FEEDBACK : Feedback> : Validator.Builder<CONTEXT, INPUT, FEEDBACK> {

        internal val entries: MutableList<ValidationEntry<CONTEXT, INPUT, FEEDBACK>> = mutableListOf()

        override fun <PROPERTY> KProperty1<INPUT, PROPERTY>.invoke(
            ruleFn: ValidationRuleContext<CONTEXT, INPUT>.(PROPERTY) -> FEEDBACK?
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

        override fun <PROPERTY, DELEGATE_CONTEXT, DELEGATE_FEEDBACK : Feedback> KProperty1<INPUT, PROPERTY>.invoke(
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

        override fun <DELEGATE_CONTEXT, DELEGATE_INPUT, DELEGATE_FEEDBACK : Feedback> input(
            validator: Validator<DELEGATE_CONTEXT, DELEGATE_INPUT, DELEGATE_FEEDBACK>,
            mapContextFn: CONTEXT.(INPUT) -> DELEGATE_CONTEXT,
            mapInputFn: CONTEXT.(INPUT) -> DELEGATE_INPUT,
            mapFeedbackKeys: Map<KProperty1<DELEGATE_INPUT, *>?, KProperty1<INPUT, *>?>,
            mapFeedbackObjectFn: (DELEGATE_FEEDBACK) -> FEEDBACK
        ) {
            entries += ValidationEntry.InputObjectDelegatesToValidator(
                validator = validator,
                mapContextFn = mapContextFn,
                mapInputFn = mapInputFn,
                mapFeedbackKeys = mapFeedbackKeys,
                mapFeedbackObjectFn = mapFeedbackObjectFn
            )
        }

        override fun returnEarlyIfAny(matchFn: (FEEDBACK) -> Boolean) {
            entries += ValidationEntry.ReturnEarlyIfAny(matchFn)
        }
    }
}
