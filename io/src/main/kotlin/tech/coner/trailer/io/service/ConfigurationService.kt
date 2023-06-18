package tech.coner.trailer.io.service

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import tech.coner.trailer.io.Configuration
import tech.coner.trailer.io.DatabaseConfiguration
import tech.coner.trailer.io.Webapp
import tech.coner.trailer.io.WebappConfiguration
import tech.coner.trailer.io.payload.ConfigAddDatabaseOutcome
import tech.coner.trailer.io.payload.ConfigAddDatabaseParam
import tech.coner.trailer.io.payload.ConfigSetDefaultDatabaseOutcome
import tech.coner.trailer.io.repository.ConfigurationRepository
import tech.coner.trailer.io.util.Cache
import tech.coner.trailer.io.util.runSuspendCatching

class ConfigurationService(
    override val coroutineContext: CoroutineContext,
    private val repository: ConfigurationRepository,
    private val cache: Cache<Unit, Configuration>
) : CoroutineScope {

    fun init() {
        repository.init()
    }

    suspend fun get(): Result<Configuration> = runSuspendCatching {
        cache.getOrCreate(Unit) {
            repository.load() ?: Configuration.DEFAULT
        }
    }

    private suspend fun put(config: Configuration): Result<Configuration> = runSuspendCatching {
        cache.update(Unit) {
            repository.save(config)
        }
    }

    suspend fun addDatabase(param: ConfigAddDatabaseParam): Result<ConfigAddDatabaseOutcome> = runSuspendCatching {
        val config = get().getOrThrow()
        if (config.databases.containsKey(param.name)) {
            throw AlreadyExistsException("Database with name already exists")
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
        put(newConfig)
            .map { ConfigAddDatabaseOutcome(configuration = newConfig, addedDbConfig = newDbConfig) }
            .getOrThrow()
    }

    suspend fun setDefaultDatabase(name: String): Result<ConfigSetDefaultDatabaseOutcome> = runSuspendCatching {
        val config = get().getOrThrow()
        val newDefaultDbConfig = config.databases[name]
            ?.copy(default = true)
            ?: throw NotFoundException("Database not found with name")
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
        put(newConfig)
            .map { ConfigSetDefaultDatabaseOutcome(it, newDefaultDbConfig) }
            .getOrThrow()
    }

    suspend fun listDatabases(): Result<List<DatabaseConfiguration>> = runSuspendCatching {
        get().getOrThrow().databases.values.toList()
            .sortedWith(
                compareByDescending(DatabaseConfiguration::default)
                    .thenByDescending(DatabaseConfiguration::name)
            )
    }

    suspend fun listDatabasesByName(): Result<Map<String, DatabaseConfiguration>> = runSuspendCatching {
        listDatabases()
            .map { dbConfigs -> dbConfigs.associateBy { it.name } }
            .getOrThrow()
    }

    suspend fun removeDatabase(name: String): Result<Configuration> = runSuspendCatching {
        val config = get().getOrThrow()
        val newConfig = config.copy(
            databases = config.databases
                .toMutableMap()
                .apply { remove(name) ?: throw NotFoundException("Database not found with name") },
            defaultDatabaseName = if (name == config.defaultDatabaseName) null else config.defaultDatabaseName
        )
        put(newConfig).getOrThrow()
    }

    suspend fun findDatabaseByName(name: String): Result<DatabaseConfiguration> = runSuspendCatching {
        listDatabasesByName().getOrThrow()[name]
            ?: throw NotFoundException("Database with name not found")
    }

    suspend fun getDefaultDatabase(): Result<DatabaseConfiguration?> = runSuspendCatching {
        val config = get().getOrThrow()
        config.defaultDatabaseName?.let { config.databases[it] }
    }

    suspend fun getWebappConfiguration(webapp: Webapp): Result<WebappConfiguration> = runSuspendCatching {
        val config = get().getOrThrow()
        when (webapp) {
            Webapp.COMPETITION -> config.webapps?.competition
                ?: Configuration.DEFAULT.requireWebapps().requireCompetition()
        }
    }

    fun mergeWebappConfiguration(
        original: WebappConfiguration,
        overridePort: Int?,
        overrideExploratory: Boolean?,
    ): WebappConfiguration {
        return original.copy(
            port = overridePort ?: original.port,
            exploratory = overrideExploratory ?: original.exploratory
        )
    }

    suspend fun configureWebapp(
        webapp: Webapp,
        webappConfig: WebappConfiguration?
    ): Result<Configuration> = runSuspendCatching {
        val config = get().getOrThrow()
        val webapps = config.webapps ?: Configuration.DEFAULT.requireWebapps()
        val newWebapps = when (webapp) {
            Webapp.COMPETITION -> webapps.copy(competition = webappConfig)
        }
        put(config.copy(webapps = newWebapps)).getOrThrow()
    }

}