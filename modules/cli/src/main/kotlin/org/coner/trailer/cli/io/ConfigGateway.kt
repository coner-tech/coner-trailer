package org.coner.trailer.cli.io

import net.harawata.appdirs.AppDirsFactory
import java.io.File

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

    fun setup() {
        if (!configDir.exists()) {
            check(configDir.mkdirs()) { "Failed to create $configDir" }
        }
    }
}