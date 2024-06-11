package tech.coner.trailer.toolkit.validation.impl.context

import tech.coner.trailer.toolkit.validation.context.ValidationRuleContext

data class ValidationRuleContextImpl<CONTEXT, INPUT>(
    override val context: CONTEXT,
    override val input: INPUT,
) : ValidationRuleContext<CONTEXT, INPUT>
