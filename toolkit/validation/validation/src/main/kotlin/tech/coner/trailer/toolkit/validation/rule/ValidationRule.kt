package tech.coner.trailer.toolkit.validation.rule

import tech.coner.trailer.toolkit.validation.Feedback

fun interface ValidationRule<CONTEXT, INPUT, FEEDBACK : Feedback> {

    operator fun invoke(context: CONTEXT, input: INPUT): FEEDBACK?
}