package tech.coner.trailer.toolkit.validation.impl

import tech.coner.trailer.toolkit.validation.ValidationContext
import tech.coner.trailer.toolkit.validation.Validator
import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.ValidationResult

internal class ValidatorImpl<INPUT, FEEDBACK : Feedback>(
    private val function: ValidationContext<FEEDBACK>.(INPUT) -> Unit
) : Validator<INPUT, FEEDBACK> {

    override fun invoke(input: INPUT): ValidationResult<FEEDBACK> {
        val context = ValidationContextImpl<FEEDBACK>()
        function(context, input)
        return ValidationResult(context.feedback.toList())
    }
}
