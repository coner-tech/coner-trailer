package tech.coner.trailer.toolkit.validation.context

import kotlin.reflect.KProperty1

interface PropertyValidationRuleContext<CONTEXT, INPUT> : ValidationRuleContext<CONTEXT, INPUT> {
    val property: KProperty1<INPUT, *>
}