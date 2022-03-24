package tech.coner.trailer.io.service

import tech.coner.trailer.io.Configuration
import tech.coner.trailer.io.DatabaseConfiguration
import tech.coner.trailer.io.payload.ConfigAddDatabaseOutcome
import tech.coner.trailer.io.payload.ConfigAddDatabaseParam
import tech.coner.trailer.io.payload.ConfigSetDefaultDatabaseOutcome
import tech.coner.trailer.io.repository.ConfigurationRepository

class ConfigurationService(
    private val repository: ConfigurationRepository
) {

    fun init() {
        repository.init()
    }

    fun get(): Configuration {
        return repository.load()
    }

    fun addDatabase(param: ConfigAddDatabaseParam): Result<ConfigAddDatabaseOutcome> {
        val config = repository.load()
        if (config.databases.containsKey(param.name)) {
            return Result.failure(AlreadyExistsException("Database with name already exists"))
        }
        val newDbConfig = DatabaseConfiguration(
            name = param.name,
            crispyFishDatabase = param.crispyFishDatabase,
            snoozleDatabase = param.snoozleDatabase,
            motorsportReg = param.motorsportReg?.let { DatabaseConfiguration.MotorsportReg(
                username = it.username,
                organizationId = it.organizationId
            ) },
            default = param.default
        )
        val newConfig = config.copy(
            databases = config.databases
                .toMutableMap()
                .apply { put(param.name, newDbConfig) },
            defaultDatabaseName = if (param.default) param.name else config.defaultDatabaseName
        )
        try {
            repository.save(newConfig)
        } catch (t: Throwable) {
            return Result.failure(Exception("Failed to save new config", t))
        }
        return Result.success(ConfigAddDatabaseOutcome(configuration = newConfig, addedDbConfig = newDbConfig))
    }

    fun setDefaultDatabase(name: String): Result<ConfigSetDefaultDatabaseOutcome> {
        val config = repository.load()
        val newDefaultDbConfig = config.databases[name]
            ?.copy(default = true)
            ?: return Result.failure(NotFoundException("Database not found with name"))
        val newConfig = config.copy(
            databases = config.databases
                .mapValues {
                    when (it.key) {
                        name -> newDefaultDbConfig
                        else -> it.value.copy(default = false)
                    }
                },
            defaultDatabaseName = name
        )
        repository.save(newConfig)
        return Result.success(ConfigSetDefaultDatabaseOutcome(newConfig, newDefaultDbConfig))
    }

    fun listDatabases(): List<DatabaseConfiguration> {
        return repository.load().databases.values.toList()
            .sortedWith(
                compareByDescending(DatabaseConfiguration::default)
                    .thenByDescending(DatabaseConfiguration::name)
            )
    }

    fun listDatabasesByName() = listDatabases().associateBy { it.name }

    fun removeDatabase(name: String): Result<Configuration> {
        val config = repository.load()
        val newConfig = config.copy(
            databases = config.databases
                .toMutableMap()
                .apply { remove(name) ?: return Result.failure(NotFoundException("Database not found with name")) },
            defaultDatabaseName = if (name == config.defaultDatabaseName) null else config.defaultDatabaseName
        )
        repository.save(newConfig)
        return Result.success(newConfig)
    }

    fun findDatabaseByName(name: String): Result<DatabaseConfiguration> {
        return listDatabasesByName()[name]
            ?.let { Result.success(it) }
            ?: Result.failure(NotFoundException("Database with name not found"))
    }

    fun getDefaultDatabase() : DatabaseConfiguration? {
        val config = repository.load()
        return config.defaultDatabaseName?.let { config.databases[it] }
    }

}