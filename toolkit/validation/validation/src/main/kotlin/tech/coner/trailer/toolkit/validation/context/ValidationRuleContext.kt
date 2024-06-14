package tech.coner.trailer.toolkit.validation.context

interface ValidationRuleContext<CONTEXT, INPUT> {

    val context: CONTEXT
    val input: INPUT
}