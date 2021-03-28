package org.coner.trailer.cli.io

import assertk.Assert
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import org.coner.trailer.io.DatabaseConfiguration

fun Assert<DatabaseConfiguration>.name() = prop("name") { it.name }
fun Assert<DatabaseConfiguration>.hasName(expected: String) = name().isEqualTo(expected)