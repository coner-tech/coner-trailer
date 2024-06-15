package tech.coner.trailer.toolkit.validation.testsupport

import assertk.Assert
import assertk.assertions.prop
import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.ValidationResult


fun <INPUT, FEEDBACK : Feedback> Assert<ValidationResult<INPUT, FEEDBACK>>.feedback() = prop(ValidationResult<INPUT, FEEDBACK>::feedback)

fun Assert<ValidationResult<*, *>>.isValid() = prop(ValidationResult<*, *>::isValid)
fun Assert<ValidationResult<*, *>>.isInvalid() = prop(ValidationResult<*, *>::isInvalid)
