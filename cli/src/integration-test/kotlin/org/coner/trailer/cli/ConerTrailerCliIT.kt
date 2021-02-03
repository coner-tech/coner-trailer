package org.coner.trailer.cli

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.startsWith
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConerTrailerCliIT {

    lateinit var executeApp: String

    @BeforeAll
    fun beforeAll() {
        executeApp = System.getProperty("execute-app")
    }

    @Test
    fun `It should print help`() {
        val process = Runtime.getRuntime().exec("$executeApp --help")
        val reader = process.inputStream.bufferedReader()
        process.waitFor()

        val output = reader.readText()

        assertThat(process.exitValue(), "exit value").isEqualTo(0)
        assertThat(output, "output").startsWith("Usage: coner-trailer-cli")
    }
}