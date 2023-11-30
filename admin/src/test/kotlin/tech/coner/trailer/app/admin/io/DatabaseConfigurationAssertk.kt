package tech.coner.trailer.app.admin.io

import assertk.Assert
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import tech.coner.trailer.io.DatabaseConfiguration

fun Assert<DatabaseConfiguration>.name() = prop("name") { it.name }
fun Assert<DatabaseConfiguration>.hasName(expected: String) = name().isEqualTo(expected)