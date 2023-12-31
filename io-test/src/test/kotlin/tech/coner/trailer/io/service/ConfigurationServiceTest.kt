package tech.coner.trailer.io.service

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.slot
import io.mockk.verifySequence
import java.nio.file.Path
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import tech.coner.trailer.io.*
import tech.coner.trailer.io.payload.configuration
import tech.coner.trailer.io.payload.defaultDbConfig
import tech.coner.trailer.io.repository.ConfigurationRepository
import tech.coner.trailer.io.util.SimpleCache

@ExtendWith(MockKExtension::class)
class ConfigurationServiceTest {

    lateinit var service: ConfigurationService

    @TempDir lateinit var root: Path
    @MockK lateinit var repository: ConfigurationRepository
    lateinit var coroutineContext: CoroutineContext

    @BeforeEach
    fun before() {
        coroutineContext = Dispatchers.Default + Job()
        service = ConfigurationService(
            coroutineContext = coroutineContext,
            repository = repository,
            cache = SimpleCache()
        )
    }

    @AfterEach
    fun tearDown() {
        coroutineContext.cancel()
    }

    @Test
    fun `It should pass init through to repository`() {
        justRun { repository.init() }

        service.init()

        verifySequence { repository.init() }
    }

    @Nested
    inner class SetDefaultDatabase {

        @Test
        fun `It should set default database by name`() = runTest {
            val config = TestConfigurations(root).testConfiguration()
            check(config.databases["bar"]?.default == true) { "Prerequisite failed: bar database must be default" }
            check(config.databases["foo"]?.default == false) { "Prerequisite failed: foo database must not be default" }
            every { repository.load() } returns config
            val saveSlot = slot<Configuration>()
            every { repository.save(capture(saveSlot)) } answers { saveSlot.captured }

            val actual = service.setDefaultDatabase("foo")

            verifySequence {
                repository.load()
                repository.save(any())
            }
            assertThat(actual)
                .isSuccess()
                .all {
                    configuration().all {
                        isSameInstanceAs(saveSlot.captured)
                        databases().all {
                            key("foo").default().isTrue()
                            key("bar").default().isFalse()
                        }
                        defaultDatabaseName().isEqualTo("foo")
                    }
                    defaultDbConfig().all {
                        name().isEqualTo("foo")
                        default().isTrue()
                    }
                }
        }

        @Test
        fun `When there are no databases, it should fail with not found`() = runTest {
            every { repository.load() } returns Configuration.DEFAULT
            check(Configuration.DEFAULT.databases.isEmpty()) { "Prerequisite failed: default configuration is expected to define no databases" }

            val actual = service.setDefaultDatabase("foo")

            assertThat(actual)
                .isFailure()
                .isInstanceOf(NotFoundException::class)
        }

        @Test
        fun `When called with name that does not exist, it should fail with not found`() = runTest {
            val config = TestConfigurations(root).testConfiguration()
            val nameDoesNotExist = "nameDoesNotExist"
            check(!config.databases.containsKey(nameDoesNotExist)) { "Prerequisite failed: test configuration must not contain key $nameDoesNotExist" }
            every { repository.load() } returns config

            val actual = service.setDefaultDatabase(nameDoesNotExist)

            assertThat(actual)
                .isFailure()
                .isInstanceOf(NotFoundException::class)
        }
    }

    @Test
    fun `It should list databases`() = runTest {
        val testConfigurations = TestConfigurations(root)
        val config = testConfigurations.testConfiguration()
        every { repository.load() } returns config

        val actual = service.listDatabases()

        assertThat(actual)
            .isSuccess()
            .isEqualTo(
                listOf(
                    testConfigurations.testDatabaseConfigurations.bar,
                    testConfigurations.testDatabaseConfigurations.foo
                )
            )
    }

    @Test
    fun `It should list databases by name`() = runTest {
        val testConfigurations = TestConfigurations(root)
        val config = testConfigurations.testConfiguration()
        every { repository.load() } returns config

        val actual = service.listDatabasesByName()

        assertThat(actual)
            .isSuccess()
            .isEqualTo(
                mapOf(
                    testConfigurations.testDatabaseConfigurations.bar.let { it.name to it },
                    testConfigurations.testDatabaseConfigurations.foo.let { it.name to it }
                )
            )
    }

    @Nested
    inner class RemoveDatabase {

        lateinit var config: Configuration

        @BeforeEach
        fun before() {
            config = TestConfigurations(root).testConfiguration()
        }

        @Test
        fun `It should remove database by name`() = runTest {
            every { repository.load() } returns config
            check(config.databases.containsKey("foo")) { "Prerequisite failed: must contain foo database" }
            val saveSlot = slot<Configuration>()
            every { repository.save(capture(saveSlot)) } answers { saveSlot.captured }

            val actual = service.removeDatabase("foo")

            assertThat(actual)
                .isSuccess()
                .all {
                    isSameInstanceAs(saveSlot.captured)
                    databases().matchesPredicate { !it.containsKey("foo") }
                }
            verifySequence {
                repository.load()
                repository.save(any())
            }
        }

        @Test
        fun `When the default database is removed, it should null out defaultDatabaseName field`() = runTest {
            every { repository.load() } returns config
            check(config.databases.containsKey("bar")) { "Prerequisite failed: must contain bar database" }
            check(
                config.databases["bar"]?.default == true && config.defaultDatabaseName == "bar"
            ) { "Prerequisite failed: bar database must be default" }
            val saveSlot = slot<Configuration>()
            every { repository.save(capture(saveSlot)) } answers { saveSlot.captured }

            val actual = service.removeDatabase("bar")

            assertThat(actual)
                .isSuccess()
                .all {
                    isSameInstanceAs(saveSlot.captured)
                    databases().matchesPredicate { !it.containsKey("bar") }
                    defaultDatabaseName().isNull()
                }
        }

        @Test
        fun `When no database with name exists, it should fail with not found`() = runTest {
            every { repository.load() } returns config
            check(!config.databases.containsKey("notFound")) { "Prerequisite failed: must contain database named notFound" }

            val actual = service.removeDatabase("notFound")

            assertThat(actual)
                .isFailure()
                .isInstanceOf(NotFoundException::class)
        }
    }

    @Nested
    inner class GetDefaultDatabase {

        lateinit var config: Configuration

        @BeforeEach
        fun before() {
            config = TestConfigurations(root).testConfiguration()
        }

        @Test
        fun `It should get default database`() = runTest {
            every { repository.load() } returns config

            val actual = service.getDefaultDatabase()

            assertThat(actual)
                .isSuccess()
                .isNotNull()
                .isSameInstanceAs(config.databases["bar"])
        }

        @Test
        fun `When no default database is named, it should return null`() = runTest {
            every { repository.load() } returns config.copy(defaultDatabaseName = null)

            val actual = service.getDefaultDatabase()

            assertThat(actual)
                .isSuccess()
                .isNull()
        }
    }
}