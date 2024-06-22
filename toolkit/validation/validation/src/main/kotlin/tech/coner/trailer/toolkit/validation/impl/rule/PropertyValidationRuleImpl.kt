package tech.coner.trailer.toolkit.validation.impl.rule

import kotlin.reflect.KProperty1
import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.context.PropertyValidationRuleContext
import tech.coner.trailer.toolkit.validation.context.ValidationRuleContext
import tech.coner.trailer.toolkit.validation.impl.context.PropertyValidationRuleContextImpl
import tech.coner.trailer.toolkit.validation.impl.context.ValidationRuleContextImpl
import tech.coner.trailer.toolkit.validation.rule.PropertyValidationRule

internal class PropertyValidationRuleImpl<CONTEXT, INPUT, PROPERTY, FEEDBACK : Feedback<INPUT>>(
    override val property: KProperty1<INPUT, PROPERTY>,
    private val validationRule: PropertyValidationRuleContext<CONTEXT, INPUT>.(PROPERTY) -> FEEDBACK?
) : PropertyValidationRule<CONTEXT, INPUT, PROPERTY, FEEDBACK> {

    override fun invoke(context: CONTEXT, input: INPUT): FEEDBACK? {
        return PropertyValidationRuleContextImpl(context, input, property)
            .validationRule(property.get(input))
    }
}