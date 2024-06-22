package tech.coner.trailer.toolkit.validation.impl.rule

import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.context.ValidationRuleContext
import tech.coner.trailer.toolkit.validation.rule.ObjectValidationRule
import tech.coner.trailer.toolkit.validation.impl.context.ValidationRuleContextImpl

class ObjectValidationRuleImpl<CONTEXT, INPUT, FEEDBACK : Feedback<INPUT>>(
    private val ruleFn: ValidationRuleContext<CONTEXT, INPUT>.(INPUT) -> FEEDBACK?
)
    : ObjectValidationRule<CONTEXT, INPUT, FEEDBACK> {
    override fun invoke(context: CONTEXT, input: INPUT): FEEDBACK? {
        return ruleFn(ValidationRuleContextImpl(context, input), input)
    }
}