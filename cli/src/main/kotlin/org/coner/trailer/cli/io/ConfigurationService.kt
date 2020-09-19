package org.coner.trailer.cli.io

import net.harawata.appdirs.AppDirs
import java.io.File
import java.util.*

class ConfigurationService(
        private val appDirs: AppDirs
) {

    private val configDir by lazy {
        File(appDirs.getUserConfigDir("coner-trailer", "1.0", "coner"))
    }

    val propertiesFile by lazy {
        configDir.resolve("config.properties")
    }

    private fun loadProperties(): Properties {
        return Properties().apply {
            if (propertiesFile.exists()) {
                load(propertiesFile.bufferedReader())
            }
        }
    }

    fun setup() {
        if (!configDir.exists()) {
            check(configDir.mkdirs()) { "Failed to create $configDir" }
        }
    }

    val noDatabase: DatabaseConfiguration by lazy { DatabaseConfiguration(
            name = "no database",
            crispyFishDatabase = propertiesFile.parentFile,
            snoozleDatabase = propertiesFile.parentFile,
            default = false
    ) }

    fun configureDatabase(dbConfig: DatabaseConfiguration) {
        val properties = loadProperties()
        if (dbConfig.default) {
            properties[PropertyKeys.DEFAULT_DATABASE] = dbConfig.name
        }
        properties[PropertyKeys.crispyFishDatabase(dbConfig.name)] = dbConfig.crispyFishDatabase.toString()
        properties[PropertyKeys.snoozleDatabase(dbConfig.name)] = dbConfig.snoozleDatabase.toString()
        properties.store(propertiesFile.bufferedWriter(), null)
    }

    fun listDatabases(): List<DatabaseConfiguration> {
        val properties = loadProperties()
        return properties
                .mapNotNull { findDatabaseNameIn(it.key) }
                .distinct()
                .map { name -> buildDatabaseConfiguration(name, properties) }
                .sortedWith(
                        compareByDescending(DatabaseConfiguration::default)
                                .thenByDescending(DatabaseConfiguration::name)
                )
                .let { actual ->
                    if (actual.isNotEmpty()) actual
                    else listOf(noDatabase)
                }
    }

    fun listDatabasesByName() = listDatabases()
            .map { it.name to it }
            .toMap()

    fun removeDatabase(dbConfig: DatabaseConfiguration) {
        loadProperties().apply {
            remove(PropertyKeys.crispyFishDatabase(dbConfig.name))
            remove(PropertyKeys.snoozleDatabase(dbConfig.name))
            store(propertiesFile.bufferedWriter(), null)
        }
    }

    private fun buildDatabaseConfiguration(name: String, properties: Properties): DatabaseConfiguration {
        val crispyFishDatabasePropertyKey = PropertyKeys.crispyFishDatabase(name)
        val snoozleDatabasePropertyKey = PropertyKeys.snoozleDatabase(name)
        return DatabaseConfiguration(
                name = name,
                crispyFishDatabase = File(
                        properties.getProperty(crispyFishDatabasePropertyKey)
                                ?: throw IllegalStateException("Config file has no crispy fish configuration for database $name")
                ),
                snoozleDatabase = File(
                        properties.getProperty(snoozleDatabasePropertyKey)
                                ?: throw IllegalStateException("Config file has no snoozle configuration for database $name")
                ),
                default = properties.getProperty(PropertyKeys.DEFAULT_DATABASE) == name
        )
    }

    private fun findDatabaseNameIn(propertyKey: Any): String? {
        if (propertyKey !is String || !propertyKey.startsWith("db[")) return null // not a database property
        val lastIndexOfName = propertyKey.indexOf(']', startIndex = 3) - 1
        return propertyKey.substring(3..lastIndexOfName)
    }

    fun getDatabase(name: String): DatabaseConfiguration? {
        return listDatabases().singleOrNull { it.name == name }
    }

    fun getDefaultDatabase() : DatabaseConfiguration? {
        return listDatabases().singleOrNull { it.default }
    }

    private object PropertyKeys {

        const val DEFAULT_DATABASE = "db.default"

        fun crispyFishDatabase(name: String) = "db[$name].crispyFishDatabase"

        fun snoozleDatabase(name: String) = "db[$name].snoozleDatabase"
    }

}