package tech.coner.trailer.io.repository

import assertk.assertThat
import assertk.assertions.exists
import assertk.assertions.isEqualTo
import assertk.assertions.isSameAs
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.nio.file.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile
import kotlin.io.path.exists
import kotlin.io.path.notExists
import kotlin.io.path.readText
import kotlin.io.path.writeText
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.io.TempDir
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import tech.coner.trailer.io.Configuration
import tech.coner.trailer.io.DatabaseConfiguration
import tech.coner.trailer.io.WebappConfiguration

class ConfigurationRepositoryTest {

    lateinit var repository: ConfigurationRepository

    @TempDir lateinit var root: Path
    private val configDir: Path by lazy { root.resolve("config-dir") }
    private val crispyFishDatabase: Path by lazy { root.resolve("crispyFishDatabase").createDirectories() }
    private val snoozleDatabase: Path by lazy { root.resolve("snoozleDatabase").createDirectories() }

    private val testDatabase by lazy {
        DatabaseConfiguration(
            name = "test",
            crispyFishDatabase = crispyFishDatabase,
            snoozleDatabase = snoozleDatabase,
            motorsportReg = DatabaseConfiguration.MotorsportReg(
                username = "test@coner.tech",
                organizationId = "test-coner-motorsportreg-organization-id"
            ),
            default = true
        )
    }
    private val testConfiguration by lazy {
        Configuration(
            databases = mapOf(
                testDatabase.name to testDatabase
            ),
            defaultDatabaseName = testDatabase.name,
            webappResults = WebappConfiguration(
                port = 8080
            )
        )
    }

    @BeforeEach
    fun before() {
        repository = ConfigurationRepository(
            configDir = configDir,
            objectMapper = jacksonObjectMapper()
        )
    }

    @Nested
    inner class Init {

        @Test
        fun `When configDir does not exist, setup should create it`() {
            check(configDir.notExists()) { "Prerequisite failed: $configDir should not exist" }

            repository.init()

            assertThat(configDir).exists()
        }

        @Test
        fun `When configDir already exists as directory, setup should succeed`() {
            configDir.createDirectories()

            assertDoesNotThrow {
                repository.init()
            }
        }

        @Test
        fun `When configDir already exists but not as directory, setup should fail`() {
            configDir.createFile().writeText("irrelevant")

            assertThrows<IllegalStateException> {
                repository.init()
            }
        }
    }

    @Nested
    inner class Load {

        @BeforeEach
        fun before() {
            repository.init()
        }

        @Test
        fun `It should load configuration`() {
            val expected = testConfiguration
            configDir
                .createDirectories()
                .resolve("config.json")
                .createFile()
                .writeText(expected.toJson())

            val actual = repository.load()

            assertThat(actual).isEqualTo(expected)
        }

        @Test
        fun `When configuration does not exist, it should return default configuration`() {
            val actual = repository.load()

            assertThat(actual).isSameAs(Configuration.DEFAULT)
        }
    }

    @Nested
    inner class Save {

        @BeforeEach
        fun before () {
            repository.init()
        }

        @Test
        fun `It should save configuration when none yet exists`() {
            val configFile = configDir.resolve("config.json")
            check(configFile.notExists()) { "Failed prerequisite: config.json must not exist" }
            val save = testConfiguration

            repository.save(save)

            JSONAssert.assertEquals(save.toJson(), configFile.readText(), JSONCompareMode.STRICT)
        }

        @Test
        fun `It should replace old configuration with new configuration`() {
            val configFile = configDir.resolve("config.json")
            val oldConfiguration = testConfiguration
            configFile
                .createFile()
                .writeText(oldConfiguration.toJson())
            check(configFile.exists()) { "Failed prerequisite: config.json must exist" }
            val newConfiguration = oldConfiguration.copy(defaultDatabaseName = null)

            repository.save(newConfiguration)

            JSONAssert.assertEquals(newConfiguration.toJson(), configFile.readText(), JSONCompareMode.STRICT)
        }
    }
}

private fun Configuration.toJson() = """
    {
        "databases": {
            ${databases.map { it.toJson() }.joinToString()}
        },
        "defaultDatabaseName": ${defaultDatabaseName?.let { "\"$it\"" }},
        "webappResults": ${webappResults?.let { """{ "port": ${it.port} }""" } ?: "null"}
    }
""".trimIndent()

private fun Map.Entry<String, DatabaseConfiguration>.toJson() = """
    "${value.name}": {
        "name": "${value.name}",
        "crispyFishDatabase": "file://${value.crispyFishDatabase.absolutePathString()}/",
        "snoozleDatabase": "file://${value.snoozleDatabase.absolutePathString()}/",
        "motorsportReg": {
            "username": "${value.motorsportReg!!.username!!}",
            "organizationId": "${value.motorsportReg!!.organizationId!!}"
        },
        "default": ${value.default}
    }
""".trimIndent()
