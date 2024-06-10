package tech.coner.trailer.toolkit.validation.impl

import kotlin.reflect.KProperty1
import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.ValidationResult
import tech.coner.trailer.toolkit.validation.ValidationRule
import tech.coner.trailer.toolkit.validation.Validator

internal class ValidatorImpl<INPUT, FEEDBACK : Feedback>(
    builder: Validator.Builder<INPUT, FEEDBACK>.() -> Unit
) : Validator<INPUT, FEEDBACK> {

    private val rules: List<ValidationRule<INPUT, FEEDBACK>>

    init {
        BuilderImpl<INPUT, FEEDBACK>()
            .apply(builder)
            .also { rules = it.rules }
    }

    override fun invoke(input: INPUT): ValidationResult<FEEDBACK> {
        val feedback: MutableMap<KProperty1<*, *>?, MutableList<FEEDBACK>> = mutableMapOf()
        rules
            .forEach { rule ->
                rule(input)
                    ?.also {
                        feedback.createOrAppend(
                            key = when (rule) {
                                is PropertyValidationRule<INPUT, *, FEEDBACK> -> rule.property
                                else -> null
                            },
                            it
                        )
                    }
            }
        return ValidationResult(feedback.toMap())
    }

    internal class BuilderImpl<INPUT, FEEDBACK : Feedback> : Validator.Builder<INPUT, FEEDBACK> {

        internal val rules: MutableList<ValidationRule<INPUT, FEEDBACK>> = mutableListOf()

        override fun <PROPERTY> on(
            property: KProperty1<INPUT, PROPERTY>,
            ruleFn: INPUT.(PROPERTY) -> FEEDBACK?
        ) {
            rules += PropertyValidationRuleImpl(property, ruleFn)
        }

        override fun <PROPERTY> on(
            property: KProperty1<INPUT, PROPERTY>,
            vararg ruleFns: INPUT.(PROPERTY) -> FEEDBACK?
        ) {
            rules += ruleFns
                .map { PropertyValidationRuleImpl(property, it) }
        }

    }
}
