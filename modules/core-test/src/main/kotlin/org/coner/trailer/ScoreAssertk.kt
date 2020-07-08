package org.coner.trailer

import assertk.Assert
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import org.coner.trailer.eventresults.Score

fun Assert<Score>.value() = prop("value") { it.value }
fun Assert<Score>.hasValue(expected: Int) = value().isEqualTo(expected)