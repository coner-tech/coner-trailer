package tech.coner.trailer.toolkit.validation.rule

import tech.coner.trailer.toolkit.validation.Feedback

internal interface ObjectValidationRule<CONTEXT, INPUT, FEEDBACK : Feedback<INPUT>>
    : ValidationRule<CONTEXT, INPUT, FEEDBACK> {
}