package org.coner.trailer

import assertk.Assert
import assertk.assertions.isEqualTo
import assertk.assertions.prop

fun Assert<Time?>.value() = prop("value") { it?.value }
fun Assert<Time?>.isEqualTo(expected: String?) = value().transform { it.toString() }.isEqualTo(expected)