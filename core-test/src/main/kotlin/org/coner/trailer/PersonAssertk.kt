package org.coner.trailer

import assertk.Assert
import assertk.assertions.isEqualTo
import assertk.assertions.prop

fun Assert<Person>.id() = prop("id") { it.id }
fun Assert<Person>.hasSameIdAs(expected: Person) = id().isEqualTo(expected.id)