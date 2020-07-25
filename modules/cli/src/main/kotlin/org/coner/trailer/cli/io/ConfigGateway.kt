package org.coner.trailer.cli.io

import net.harawata.appdirs.AppDirsFactory
import java.io.File
import java.util.*

class ConfigGateway {

    private val appDirs by lazy {
        AppDirsFactory.getInstance()
    }

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

    fun configureDatabase(dbConfig: DatabaseConfiguration) {
        val properties = loadProperties()
        properties[buildCrispyFishDatabasePropertyKey(dbConfig.name)] = dbConfig.crispyFishDatabase.toString()
        properties[buildSnoozleDatabasePropertyKey(dbConfig.name)] = dbConfig.snoozleDatabase.toString()
        properties.store(propertiesFile.bufferedWriter(), null)
    }

    fun listDatabases(): List<DatabaseConfiguration> {
        val properties = loadProperties()
        return properties
                .mapNotNull { findDatabaseNameIn(it.key) }
                .distinct()
                .sorted()
                .map { name -> buildDatabaseConfiguration(name, properties) }
    }

    fun removeDatabase(name: String) {
        loadProperties().apply {
            remove(buildCrispyFishDatabasePropertyKey(name))
            remove(buildSnoozleDatabasePropertyKey(name))
            store(propertiesFile.bufferedWriter(), null)
        }
    }

    private fun buildDatabaseConfiguration(name: String, properties: Properties): DatabaseConfiguration {
        val crispyFishDatabasePropertyKey = buildCrispyFishDatabasePropertyKey(name)
        val snoozleDatabasePropertyKey = buildSnoozleDatabasePropertyKey(name)
        return DatabaseConfiguration(
                name = name,
                crispyFishDatabase = File(
                        properties.getProperty(crispyFishDatabasePropertyKey)
                                ?: throw IllegalStateException("Config file has no crispy fish configuration for database $name")
                ),
                snoozleDatabase = File(
                        properties.getProperty(snoozleDatabasePropertyKey)
                                ?: throw IllegalStateException("Config file has no snoozle configuration for database $name")
                )
        )
    }

    private fun findDatabaseNameIn(propertyKey: Any): String? {
        if (propertyKey !is String || !propertyKey.startsWith("db[")) return null // not a database property
        val lastIndexOfName = propertyKey.indexOf(']', startIndex = 3) - 1
        return propertyKey.substring(3..lastIndexOfName)
    }

    private fun buildCrispyFishDatabasePropertyKey(name: String) = "db[$name].crispyFishDatabase"

    private fun buildSnoozleDatabasePropertyKey(name: String) = "db[$name].snoozleDatabase"

}