package tech.coner.trailer.toolkit.validation.adapter

import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.ValidationOutcome

fun interface ValidationOutcomeAdapter<INPUT, FEEDBACK : Feedback<INPUT>, NEW_INPUT, NEW_FEEDBACK : Feedback<NEW_INPUT>> {
    operator fun invoke(validationOutcome: ValidationOutcome<INPUT, FEEDBACK>): ValidationOutcome<NEW_INPUT, NEW_FEEDBACK>
}

fun <INPUT, FEEDBACK : Feedback<INPUT>, NEW_INPUT, NEW_FEEDBACK : Feedback<NEW_INPUT>> ValidationOutcome<INPUT, FEEDBACK>.map(fn: (FEEDBACK) -> NEW_FEEDBACK): ValidationOutcome<NEW_INPUT, NEW_FEEDBACK> {
    return ValidationOutcome(
        feedback = feedback.map { fn(it) }
    )
}