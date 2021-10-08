package org.coner.trailer.cli

import assertk.all
import assertk.assertAll
import assertk.assertThat
import assertk.assertions.*
import org.coner.trailer.TestEvents
import org.coner.trailer.TestParticipants
import org.coner.trailer.cli.util.ConerTrailerCliProcessRunner
import org.coner.trailer.cli.util.IntegrationTestAppArgumentBuilder
import org.coner.trailer.cli.util.NativeImageCommandArrayFactory
import org.coner.trailer.cli.util.ShadedJarCommandArrayFactory
import org.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import org.coner.trailer.eventresults.EventResultsType
import org.coner.trailer.render.Format
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.extension
import kotlin.io.path.nameWithoutExtension
import kotlin.io.path.readText

class ConerTrailerCliExecutableIT {

    lateinit var processRunner: ConerTrailerCliProcessRunner

    @TempDir lateinit var testDir: Path
    lateinit var configDir: Path
    lateinit var snoozleDir: Path
    lateinit var crispyFishDir: Path

    @BeforeEach
    fun before() {
        configDir = testDir.resolve("config").apply { createDirectory() }
        snoozleDir = testDir.resolve("snoozle").apply { createDirectory() }
        crispyFishDir = testDir.resolve("crispy-fish").apply { createDirectory() }
        processRunner = ConerTrailerCliProcessRunner(
            processCommandArrayFactory = when (System.getProperty("coner-trailer-cli.argument-factory", "shaded-jar")) {
                "shaded-jar" -> ShadedJarCommandArrayFactory()
                "native-image" -> NativeImageCommandArrayFactory()
                else -> throw IllegalStateException("Unknown argument factory")
            },
            appArgumentBuilder = IntegrationTestAppArgumentBuilder(
                configDir = configDir,
                snoozleDir = snoozleDir,
                crispyFishDir = crispyFishDir
            )
        )
    }

    @Test
    fun `It should print help`() {
        val process = processRunner.exec("--help")
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

        val process = processRunner.execConfigureDatabaseAdd(databaseName)
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
        processRunner.execConfigureDatabaseAdd(databaseName).waitForSuccess()

        val process = processRunner.exec(
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

    @Test
    fun `It should add an event`() {
        val databaseName = "event-and-prerequisites"
        processRunner.execConfigureDatabaseAdd(databaseName).waitForSuccess()
        val event = TestEvents.Lscc2019Simplified.points1
        val seasonFixture = SeasonFixture.Lscc2019Simplified(crispyFishDir)
        processRunner.execPolicyAdd(policy = event.policy)
            .waitForSuccess()

        val process = processRunner.execEventAddCrispyFish(
            event = event,
            crispyFishEventControlFile = seasonFixture.event1.ecfPath,
            crispyFishClassDefinitionFile = seasonFixture.classDefinitionPath
        )
        process.waitFor()

        val output = process.inputStream.bufferedReader().readText()
        val error = process.errorStream.bufferedReader().readText()
        assertAll {
            assertThat(process.exitValue(), "exit value").isEqualTo(0)
            assertThat(output, "output").all {
                contains(event.name)
                contains("${event.date}")
            }
            assertThat(error, "error").isEmpty()
        }
        val eventEntity = Files.find(snoozleDir, 4, { path, attrs ->
            attrs.isRegularFile
                    && path.nameWithoutExtension == "${event.id}"
                    && path.extension == "json"
        }).toList().single()
        assertThat(eventEntity.readText(), "persisted event entity").all {
            contains("${event.id}")
            contains(event.name)
        }
    }

    @Test
    fun `It should add a crispy fish person map entry`() {
        val databaseName = "add-crispy-fish-person-map-entry"
        processRunner.execConfigureDatabaseAdd(databaseName).waitForSuccess()
        val event = TestEvents.Lscc2019Simplified.points1
        val seasonFixture = SeasonFixture.Lscc2019Simplified(crispyFishDir)
        val participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF
        processRunner.execPolicyAdd(policy = event.policy).waitForSuccess()
        processRunner.execPersonAdd(participant.person!!).waitForSuccess()
        processRunner.execEventAddCrispyFish(
            event = event,
            crispyFishEventControlFile = seasonFixture.event1.ecfPath,
            crispyFishClassDefinitionFile = seasonFixture.classDefinitionPath
        ).waitForSuccess()

        val process: Process = processRunner.execEventCrispyFishPersonMapAdd(
            event = event,
            participant = participant
        )
        process.waitFor()

        val output = process.inputStream.bufferedReader().readText()
        val error = process.errorStream.bufferedReader().readText()
        assertAll {
            assertThat(process.exitValue(), "exit value").isEqualTo(0)
            assertThat(output, "output").all {
                contains(event.name)
                contains("${event.date}")
                contains(participant.classing!!.group!!.abbreviation)
                contains(participant.classing!!.handicap.abbreviation)
                contains(participant.number!!)
                contains("${participant.person!!.id}")
            }
            assertThat(error, "error").isEmpty()
        }
    }

    @ParameterizedTest
    @MethodSource("org.coner.trailer.cli.util.ParameterSources#provideArgumentsForEventResultsOfAllTypesAndAllFormats")
    fun `It should print event results of all types and all formats`(eventResultType: EventResultsType, format: Format) {
        val event = TestEvents.Lscc2019Simplified.points1
        val databaseName = "print-event-results-${eventResultType.key}-${format.name}"
        processRunner.execConfigureDatabaseAdd(databaseName).waitForSuccess()
        val seasonFixture = SeasonFixture.Lscc2019Simplified(crispyFishDir)
        processRunner.execPolicyAdd(policy = event.policy)
            .waitForSuccess()
        processRunner.execEventAddCrispyFish(
            event = event,
            crispyFishEventControlFile = seasonFixture.event1.ecfPath,
            crispyFishClassDefinitionFile = seasonFixture.classDefinitionPath
        ).waitForSuccess()

        val process = processRunner.execEventResults(
            event = event,
            type = eventResultType,
            format = format,
            output = "console"
        )
        process.waitFor()

        val output = process.inputStream.bufferedReader().readText().trim()
        val error = process.errorStream.bufferedReader().readText().trim()
        assertAll {
            assertThat(process.exitValue(), "exit value").isEqualTo(0)
            assertThat(output, "output").all {
                contains(event.name)
                when (format) {
                    Format.HTML -> {
                        startsWith("<!DOCTYPE html>")
                        contains(eventResultType.titleShort)
                        contains("<table ")
                        contains("</table>")
                        endsWith("</html>")
                    }
                    Format.TEXT -> {
                        contains(eventResultType.titleShort)
                        doesNotContain("<!DOCTYPE html")
                        doesNotContain("</html>")
                    }
                    Format.JSON -> {
                        startsWith("{")
                        contains(eventResultType.key)
                        endsWith("}")
                    }
                }
            }
            assertThat(error, "error").isEmpty()
        }
    }

    private fun Process.waitForSuccess() {
        val commandLine = info().commandLine().get()
        val exitCode = waitFor()
        assertThat(exitCode, "exit code of: $commandLine").isEqualTo(0)
    }
}
