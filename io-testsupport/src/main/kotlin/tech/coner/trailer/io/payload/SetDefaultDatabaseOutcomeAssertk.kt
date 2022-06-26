package tech.coner.trailer.io.payload

import assertk.Assert
import assertk.assertions.prop

fun Assert<ConfigSetDefaultDatabaseOutcome>.configuration() = prop("configuration") { it.configuration }
fun Assert<ConfigSetDefaultDatabaseOutcome>.defaultDbConfig() = prop("defaultDbConfig") { it.defaultDbConfig }
