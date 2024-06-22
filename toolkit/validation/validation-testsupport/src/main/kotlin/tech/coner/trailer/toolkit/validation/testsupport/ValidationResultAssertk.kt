package tech.coner.trailer.toolkit.validation.testsupport

import assertk.Assert
import assertk.assertions.prop
import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.ValidationOutcome


fun <INPUT, FEEDBACK : Feedback<INPUT>> Assert<ValidationOutcome<INPUT, FEEDBACK>>.feedback() = prop(ValidationOutcome<INPUT, FEEDBACK>::feedback)

fun <INPUT, FEEDBACK : Feedback<INPUT>> Assert<ValidationOutcome<INPUT, FEEDBACK>>.feedbackByProperty() = prop(ValidationOutcome<INPUT, FEEDBACK>::feedbackByProperty)

fun Assert<ValidationOutcome<*, *>>.isValid() = prop(ValidationOutcome<*, *>::isValid)
fun Assert<ValidationOutcome<*, *>>.isInvalid() = prop(ValidationOutcome<*, *>::isInvalid)
