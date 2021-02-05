package org.coner.trailer.cli

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNullOrEmpty
import assertk.assertions.startsWith
import org.coner.trailer.cli.util.ConerTrailerCliRunner
import org.junit.jupiter.api.BeforeAll
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

    @BeforeAll
    fun beforeAll() {
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

    }
}