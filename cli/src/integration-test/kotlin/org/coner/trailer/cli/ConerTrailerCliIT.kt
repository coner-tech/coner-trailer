package org.coner.trailer.cli

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.startsWith
import org.coner.trailer.cli.util.ConerTrailerCliRunner
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.io.path.ExperimentalPathApi

@ExperimentalPathApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConerTrailerCliIT {

    lateinit var runner: ConerTrailerCliRunner

    @BeforeAll
    fun beforeAll() {
        runner = ConerTrailerCliRunner()
    }

    @Test
    fun `It should print help`() {
        val process = runner.exec("--help")
        val reader = process.inputStream.bufferedReader()
        process.waitFor()

        val output = reader.readText()

        assertThat(process.exitValue(), "exit value").isEqualTo(0)
        assertThat(output, "output").startsWith("Usage: coner-trailer-cli")
    }
}