package org.coner.trailer.cli

import assertk.all
import assertk.assertAll
import assertk.assertThat
import assertk.assertions.*
import com.github.ajalt.clikt.core.PrintHelpMessage
import com.github.ajalt.clikt.core.context
import org.coner.trailer.Event
import org.coner.trailer.TestEvents
import org.coner.trailer.TestParticipants
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.command.RootCommand
import org.coner.trailer.cli.util.IntegrationTestAppArgumentBuilder
import org.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import org.coner.trailer.eventresults.EventResultsType
import org.coner.trailer.render.Format
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.*
import kotlin.streams.toList

@ExperimentalPathApi
class ConerTrailerCliIT {

    lateinit var command: RootCommand

    @TempDir lateinit var testDir: Path
    lateinit var configDir: Path
    lateinit var snoozleDir: Path
    lateinit var crispyFishDir: Path

    lateinit var appArgumentBuilder: IntegrationTestAppArgumentBuilder
    lateinit var testConsole: StringBufferConsole

    @BeforeEach
    fun before() {
        configDir = testDir.resolve("config").apply { createDirectory() }
        snoozleDir = testDir.resolve("snoozle").apply { createDirectory() }
        crispyFishDir = testDir.resolve("crispy-fish").apply { createDirectory() }
        appArgumentBuilder = IntegrationTestAppArgumentBuilder(
            configDir = configDir,
            snoozleDir = snoozleDir,
            crispyFishDir = crispyFishDir
        )
        testConsole = StringBufferConsole()
        command = ConerTrailerCli.factory()
            .context {
                console = testConsole
            }
    }

    @Test
    fun `It should print help`() {
        assertThrows<PrintHelpMessage> {
            command.parse(args("--help"))
        }
    }

    @Test
    fun `It should add a database config`() {
        val databaseName = "arbitrary-database-name"

        command.parse(appArgumentBuilder.buildConfigureDatabaseAdd(databaseName))

        assertAll {
            assertThat(testConsole.output, "console output").isNotEmpty()
            assertThat(testConsole.error, "console errors").isEmpty()
        }
    }

    @Test
    fun `It should make a motorsportreg request with wrong credentials and get an unauthorized response`() {
        val databaseName = "motorsportreg-wrong-credentials"
        command.parse(appArgumentBuilder.buildConfigureDatabaseAdd(databaseName))

        assertThrows<IllegalStateException> {
            command.parse(args(
                "--motorsportreg-username", "wrong",
                "--motorsportreg-password", "wrong",
                "--motorsportreg-organization-id", "wrong",
                "motorsportreg",
                "member",
                "list"
            ))
        }
    }

    @Test
    fun `It should add an event`() {
        val databaseName = "event-and-prerequisites"
        command.parse(appArgumentBuilder.buildConfigureDatabaseAdd(databaseName))
        val event = TestEvents.Lscc2019Simplified.points1
        val seasonFixture = SeasonFixture.Lscc2019Simplified(crispyFishDir)
        command.parse(appArgumentBuilder.buildPolicyAdd(event.policy))

        command.parse(appArgumentBuilder.buildEventAddCrispyFish(
            event = event,
            crispyFishEventControlFile = seasonFixture.event1.ecfPath,
            crispyFishClassDefinitionFile = seasonFixture.classDefinitionPath
        ))

        assertAll {
            assertThat(testConsole.output, "output").all {
                contains(event.name)
                contains("${event.date}")
            }
            assertThat(testConsole.error, "error").isEmpty()
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
        command.parse(appArgumentBuilder.buildConfigureDatabaseAdd(databaseName))
        val event = TestEvents.Lscc2019Simplified.points1
        val seasonFixture = SeasonFixture.Lscc2019Simplified(crispyFishDir)
        command.parse(appArgumentBuilder.buildPolicyAdd(event.policy))
        val participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF
        command.parse(appArgumentBuilder.buildPersonAdd(participant.person!!))
        command.parse(appArgumentBuilder.buildEventAddCrispyFish(
            event = event,
            crispyFishEventControlFile = seasonFixture.event1.ecfPath,
            crispyFishClassDefinitionFile = seasonFixture.classDefinitionPath
        ))

        command.parse(appArgumentBuilder.buildEventCrispyFishPersonMapAdd(
            event = event,
            participant = participant
        ))

        assertAll {
            assertThat(testConsole.output, "output").all {
                contains(event.name)
                contains("${event.date}")
                contains(participant.classing!!.group!!.abbreviation)
                contains(participant.classing!!.handicap.abbreviation)
                contains(participant.number!!)
                contains("${participant.person!!.id}")
            }
            assertThat(testConsole.error, "error").isEmpty()
        }
    }

    @ParameterizedTest
    @MethodSource("org.coner.trailer.cli.util.ParameterSources#provideArgumentsForEventResultsOfAllTypesAndAllFormats")
    fun `It should print event results of all types and all formats`(eventResultType: EventResultsType, format: Format) {
        val event = TestEvents.Lscc2019Simplified.points1
        val databaseName = "print-event-results-${eventResultType.key}-${format.name}"
        command.parse(
            appArgumentBuilder.buildConfigureDatabaseAdd(databaseName)
        )
        val seasonFixture = SeasonFixture.Lscc2019Simplified(crispyFishDir)
        command.parse(appArgumentBuilder.buildPolicyAdd(policy = event.policy))
        command.parse(appArgumentBuilder.buildEventAddCrispyFish(
            event = event,
            crispyFishEventControlFile = seasonFixture.event1.ecfPath,
            crispyFishClassDefinitionFile = seasonFixture.classDefinitionPath
        ))
        testConsole.clear()

        command.parse(appArgumentBuilder.buildEventResults(
            event = event,
            type = eventResultType,
            format = format,
            output = "console"
        ))

        assertAll {
            assertThat(testConsole.output, "output").all {
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
                        contains("────")
                        contains("┼")
                        contains("┴")
                        doesNotContain("<html>")
                        doesNotContain("</html>")
                    }
                    Format.JSON -> {
                        startsWith("{")
                        contains(eventResultType.key)
                        endsWith("}")
                    }
                }
            }
            assertThat(testConsole.error, "error").isEmpty()
        }
    }

    private fun args(vararg args: String) = appArgumentBuilder.build(*args)
}