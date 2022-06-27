package tech.coner.trailer

import assertk.Assert
import assertk.assertions.isEqualTo
import assertk.assertions.prop

fun Assert<Signage>.classing() = prop(Signage::classing)

fun Assert<Signage>.number() = prop(Signage::number)
fun Assert<Signage>.hasNumber(expected: String) = number().isEqualTo(expected)