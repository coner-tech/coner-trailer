package tech.coner.trailer.toolkit.validation.impl

import kotlin.reflect.KProperty1
import tech.coner.trailer.toolkit.validation.Feedback

internal class PropertyValidationRuleImpl<INPUT, PROPERTY, FEEDBACK : Feedback>(
    override val property: KProperty1<INPUT, PROPERTY>,
    private val validationRule: INPUT.(PROPERTY) -> FEEDBACK?
) : PropertyValidationRule<INPUT, PROPERTY, FEEDBACK> {

    override fun invoke(input: INPUT): FEEDBACK? {
        return validationRule(input, property.get(input))
    }
}