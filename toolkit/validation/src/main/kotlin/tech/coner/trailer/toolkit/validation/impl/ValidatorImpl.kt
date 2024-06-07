package tech.coner.trailer.toolkit.validation.impl

import tech.coner.trailer.toolkit.validation.ValidationContext
import tech.coner.trailer.toolkit.validation.Validator
import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.ValidationResult

internal class ValidatorImpl<INPUT, FEEDBACK : Feedback>(
    private val function: ValidationContext<INPUT, FEEDBACK>.(INPUT) -> FEEDBACK?
) : Validator<INPUT, FEEDBACK> {

    override fun invoke(input: INPUT): ValidationResult<FEEDBACK> {
        val context = ValidationContextImpl<INPUT, FEEDBACK>(null, input)
        function(context, input)
            ?.also { context.give(it) }
        return ValidationResult(context.feedback.toMap())
    }
}
