package tech.coner.trailer.io

import assertk.Assert
import assertk.assertions.prop

fun Assert<Configuration>.databases() = prop("databases") { it.databases }
fun Assert<Configuration>.defaultDatabaseName() = prop("defaultDatabaseName") { it.defaultDatabaseName }