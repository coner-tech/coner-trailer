package tech.coner.trailer.io.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.nio.file.Path
import kotlin.io.path.bufferedReader
import kotlin.io.path.bufferedWriter
import kotlin.io.path.createDirectories
import kotlin.io.path.deleteIfExists
import kotlin.io.path.isDirectory
import kotlin.io.path.isReadable
import kotlin.io.path.moveTo
import kotlin.io.path.notExists
import tech.coner.trailer.io.Configuration

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

    fun load(): Configuration {
        return when {
            configFile.isReadable() -> configFile.bufferedReader().use { objectMapper.readValue(it) }
            else -> Configuration.DEFAULT
        }
    }

    fun save(config: Configuration) {
        val tempFile = kotlin.io.path.createTempFile()
        try {
            tempFile
                    .bufferedWriter()
                    .use { objectMapper.writeValue(it, config) }
            tempFile.moveTo(configFile, overwrite = true)
        } finally {
            tempFile.deleteIfExists()
        }
    }

}