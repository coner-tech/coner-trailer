package tech.coner.trailer.toolkit.validation.testsupport

import assertk.Assert
import assertk.assertions.prop
import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.ValidationResult


fun <FEEDBACK : Feedback> Assert<ValidationResult<FEEDBACK>>.feedback() = prop(tech.coner.trailer.toolkit.validation.ValidationResult<FEEDBACK>::feedback)

fun Assert<ValidationResult<*>>.isValid() = prop(tech.coner.trailer.toolkit.validation.ValidationResult<*>::isValid)
fun Assert<ValidationResult<*>>.isInvalid() = prop(tech.coner.trailer.toolkit.validation.ValidationResult<*>::isInvalid)
