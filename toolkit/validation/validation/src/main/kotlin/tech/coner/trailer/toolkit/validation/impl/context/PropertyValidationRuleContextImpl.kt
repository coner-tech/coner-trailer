package tech.coner.trailer.toolkit.validation.impl.context

import tech.coner.trailer.toolkit.validation.context.PropertyValidationRuleContext
import kotlin.reflect.KProperty1

data class PropertyValidationRuleContextImpl<CONTEXT, INPUT>(
    override val context: CONTEXT,
    override val input: INPUT,
    override val property: KProperty1<INPUT, *>
) : PropertyValidationRuleContext<CONTEXT, INPUT>
