package tech.coner.trailer.io.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import tech.coner.trailer.io.Configuration
import java.nio.file.Path
import kotlin.io.path.*

class ConfigurationRepository(
    private val configDir: Path,
    private val objectMapper: ObjectMapper
) {

    private val configFile by lazy {
        configDir.resolve("config.json")
    }

    fun init() {
        if (configDir.notExists()) {
            configDir.createDirectories()
        }
        check(configDir.isDirectory()) { "$configDir is not a directory" }
    }

    fun load(): Configuration? {
        return when {
            configFile.isReadable() -> configFile.bufferedReader().use { objectMapper.readValue(it) }
            else -> null
        }
    }

    fun save(config: Configuration): Configuration {
        val tempFile = createTempFile(prefix = "coner-trailer.config")
        try {
            tempFile
                    .bufferedWriter()
                    .use { objectMapper.writeValue(it, config) }
            tempFile.moveTo(configFile, overwrite = true)
        } finally {
            tempFile.deleteIfExists()
        }
        return config
    }

}