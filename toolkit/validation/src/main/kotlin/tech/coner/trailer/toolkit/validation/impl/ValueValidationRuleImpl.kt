package tech.coner.trailer.toolkit.validation.impl

import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.ValidationRule

internal class ValueValidationRuleImpl<INPUT, FEEDBACK : Feedback>(
    val validationRule: ValidationRule<INPUT, FEEDBACK>
) : ValidationRule<INPUT, FEEDBACK> {

    override fun invoke(input: INPUT): FEEDBACK? {
        return validationRule(input)
    }
}
