package org.coner.trailer.io

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.nio.file.Files
import java.nio.file.Path

class ConfigurationService(
        private val configDir: Path,
        private val objectMapper: ObjectMapper
) {

    val configFile by lazy {
        configDir.resolve("config.json")
    }

    fun setup() {
        if (!Files.exists(configDir)) {
            Files.createDirectories(configDir)
        }
        check(Files.isDirectory(configDir)) { "$configDir is not a directory" }
    }

    val noDatabase: DatabaseConfiguration by lazy { DatabaseConfiguration(
            name = "no database",
            crispyFishDatabase = configDir,
            snoozleDatabase = configDir,
            motorsportReg = DatabaseConfiguration.MotorsportReg(
                    username = null,
                    organizationId = null
            ),
            default = false
    ) }

    private fun loadConfig(): Configuration {
        return when {
            Files.isReadable(configFile) -> objectMapper.readValue(Files.newInputStream(configFile))
            else -> Configuration(
                    databases = mutableMapOf(),
                    defaultDatabaseName = null
            )
        }
    }

    fun configureDatabase(dbConfig: DatabaseConfiguration) {
        val config = loadConfig()
        config.databases[dbConfig.name] = dbConfig
        if (dbConfig.default) {
            config.defaultDatabaseName = dbConfig.name
        }
        objectMapper.writeValue(Files.newOutputStream(configFile), config)
    }

    fun listDatabases(): List<DatabaseConfiguration> {
        return loadConfig().databases.values.toList()
                .sortedWith(
                        compareByDescending(DatabaseConfiguration::default)
                                .thenByDescending(DatabaseConfiguration::name)
                )
    }

    fun listDatabasesByName() = listDatabases()
            .map { it.name to it }
            .toMap()

    fun removeDatabase(dbConfig: DatabaseConfiguration) {
        val config = loadConfig()
        config.databases.remove(dbConfig.name)
        objectMapper.writeValue(Files.newOutputStream(configFile), config)
    }

    fun getDefaultDatabase() : DatabaseConfiguration? {
        val config = loadConfig()
        return config.defaultDatabaseName?.let { config.databases[it] }
    }

}