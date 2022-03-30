package tech.coner.trailer.io.payload

import tech.coner.trailer.io.Configuration
import tech.coner.trailer.io.DatabaseConfiguration

data class ConfigSetDefaultDatabaseOutcome(
    val configuration: Configuration,
    val defaultDbConfig: DatabaseConfiguration
)
