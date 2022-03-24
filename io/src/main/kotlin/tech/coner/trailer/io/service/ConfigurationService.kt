package tech.coner.trailer.io.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import tech.coner.trailer.io.Configuration
import tech.coner.trailer.io.DatabaseConfiguration
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

    private fun loadConfig(): Configuration {
        return when {
            Files.isReadable(configFile) -> objectMapper.readValue(Files.newInputStream(configFile))
            else -> Configuration(
                    databases = mutableMapOf(),
                    defaultDatabaseName = null
            )
        }
    }

    private fun saveConfig(config: Configuration) {
        objectMapper.writeValue(Files.newOutputStream(configFile), config)
    }

    fun setDefaultDatabase(name: String): Result<DatabaseConfiguration> {
        val config = loadConfig()
        val dbConfig = config.databases[name]
            ?.copy(default = true)
            ?: return Result.failure(NotFoundException("Database not found with name"))
        val newConfig = config.copy(
            databases = config.databases
                .mapValues {
                    when (it.value.name) {
                        name -> dbConfig
                        else -> it.value.copy(default = false)
                    }
                },
            defaultDatabaseName = name
        )
        saveConfig(newConfig)
        return Result.success(dbConfig)
    }

    fun listDatabases(): List<DatabaseConfiguration> {
        return loadConfig().databases.values.toList()
                .sortedWith(
                        compareByDescending(DatabaseConfiguration::default)
                                .thenByDescending(DatabaseConfiguration::name)
                )
    }

    fun listDatabasesByName() = listDatabases().associateBy { it.name }

    fun removeDatabase(name: String): Result<Unit> {
        val config = loadConfig()
        val newConfig = config.copy(
            databases = config.databases
                .toMutableMap()
                .apply { remove(name) ?: throw NotFoundException("Database not found with name") }
        )
        saveConfig(newConfig)
        return Result.success(Unit)
    }

    fun getDefaultDatabase() : DatabaseConfiguration? {
        val config = loadConfig()
        return config.defaultDatabaseName?.let { config.databases[it] }
    }

}