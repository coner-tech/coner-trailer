package org.coner.trailer.cli.util

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isSameAs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.coner.trailer.eventresults.EventResultsFileNameGenerator
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.ExperimentalPathApi

@ExperimentalPathApi
@ExtendWith(MockKExtension::class)
class FileOutputDestinationResolverTest {

    lateinit var resolver: FileOutputDestinationResolver

    @MockK lateinit var eventResultsFileNameGenerator: EventResultsFileNameGenerator

    @BeforeEach
    fun before() {
        resolver = FileOutputDestinationResolver(
            eventResultsFileNameGenerator = eventResultsFileNameGenerator
        )
    }

    @Test
    fun `It should process null path and generate with current working directory`() {
        val actual = resolver.process(null) { resolve("in-cwd") }

        val expected = Paths.get("${System.getProperty("user.home")}/in-cwd")
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `It should process directory path and generate with it`(
        @TempDir dir: Path
    ) {
        val actual = resolver.process(dir) { resolve("temp-file") }

        val expected = dir.resolve("temp-file")
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `It should return regular file path without additional processing`(
        @TempDir dir: Path
    ) {
        val regularFile = dir.resolve("regular-file.txt")

        val actual = assertDoesNotThrow {
            resolver.process(regularFile) { throw Exception("Should not invoke generator") }
        }

        assertThat(actual).isSameAs(regularFile)
    }
}