package tech.coner.trailer.io.repository

import assertk.assertThat
import assertk.assertions.exists
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.*
import org.junit.jupiter.api.io.TempDir
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import tech.coner.trailer.io.Configuration
import tech.coner.trailer.io.DatabaseConfiguration
import tech.coner.trailer.io.WebappConfiguration
import java.nio.file.Path
import kotlin.io.path.*

class ConfigurationRepositoryTest {

    lateinit var repository: ConfigurationRepository

    lateinit var objectMapper: ObjectMapper
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
            webapps = Configuration.Webapps(
                competition = WebappConfiguration(
                    port = 8080,
                    exploratory = false
                )
            )
        )
    }

    @BeforeEach
    fun before() {
        objectMapper = jacksonObjectMapper()
        repository = ConfigurationRepository(
            configDir = configDir,
            objectMapper = objectMapper
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
                .bufferedWriter()
                .use { objectMapper.writeValue(it, expected) }

            val actual = repository.load()

            assertThat(actual).isEqualTo(expected)
        }

        @Test
        fun `When configuration does not exist, it should return null`() {
            val actual = repository.load()

            assertThat(actual).isNull()
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

            val expectedSavedJson = objectMapper.writeValueAsString(save)
            JSONAssert.assertEquals(expectedSavedJson, configFile.readText(), JSONCompareMode.STRICT)
        }

        @Test
        fun `It should replace old configuration with new configuration`() {
            val configFile = configDir.resolve("config.json")
            val oldConfiguration = testConfiguration
            configFile
                .createFile()
                .bufferedWriter()
                .use { objectMapper.writeValue(it, oldConfiguration) }
            check(configFile.exists()) { "Failed prerequisite: config.json must exist" }
            val newConfiguration = oldConfiguration.copy(defaultDatabaseName = null)

            repository.save(newConfiguration)

            val newConfigurationJson = objectMapper.writeValueAsString(newConfiguration)
            JSONAssert.assertEquals(newConfigurationJson, configFile.readText(), JSONCompareMode.STRICT)
        }
    }
}
