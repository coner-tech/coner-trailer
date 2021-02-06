package org.coner.trailer.cli

import assertk.all
import assertk.assertAll
import assertk.assertThat
import assertk.assertions.*
import org.coner.trailer.cli.util.ConerTrailerCliRunner
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi

@ExperimentalPathApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConerTrailerCliIT {

    lateinit var runner: ConerTrailerCliRunner

    @TempDir lateinit var configDir: Path
    @TempDir lateinit var snoozleDir: Path
    @TempDir lateinit var crispyFishDir: Path

    @BeforeEach
    fun before() {
        runner = ConerTrailerCliRunner(
            configDir = configDir,
            snoozleDir = snoozleDir,
            crispyFishDir = crispyFishDir
        )
    }

    @Test
    fun `It should print help`() {
        val process = runner.exec("--help")
        process.waitFor()

        val output = process.inputStream.bufferedReader().use { it.readText() }
        val error = process.errorStream.bufferedReader().use { it.readText() }

        assertAll {
            assertThat(process.exitValue(), "exit value").isEqualTo(0)
            assertThat(output, "output").startsWith("Usage: coner-trailer-cli")
            assertThat(error, "error").isNullOrEmpty()
        }
    }

    @Test
    fun `It should add a database config`() {
        val databaseName = "arbitrary-database-name"

        val process = runner.execConfigureDatabaseAdd(databaseName)
        process.waitFor()

        val output = process.inputStream.bufferedReader().use { it.readText() }
        val error = process.errorStream.bufferedReader().use { it.readText() }

        assertAll {
            assertThat(process.exitValue(), "exit value").isEqualTo(0)
            assertThat(output, "output").isNotEmpty()
            assertThat(error, "error").isEmpty()
        }
    }

    @Test
    fun `It should make a motorsportreg request with wrong credentials and get an unauthorized response`() {
        val databaseName = "motorsportreg-wrong-credentials"
        runner.execConfigureDatabaseAdd(databaseName).waitForSuccess()

        val process = runner.exec(
            "motorsportreg", "member", "list",
            environment = arrayOf(
                "MOTORSPORTREG_ORGANIZATION_ID=wrong",
                "MOTORSPORTREG_USERNAME=wrong",
                "MOTORSPORTREG_PASSWORD=wrong"
            )
        )
        process.waitFor()

        val error = process.errorStream.bufferedReader().readText()
        assertAll {
            assertThat(process.exitValue(), "exit value").isNotEqualTo(0)
            assertThat(error, "error").all {
                contains("Failed to fetch members", ignoreCase = true)
                contains("401")
            }
        }
    }
}

private fun Process.waitForSuccess() {
    val commandLine = info().commandLine().get()
    val exitCode = waitFor()
    assertThat(exitCode, "exit code of: $commandLine").isEqualTo(0)
}