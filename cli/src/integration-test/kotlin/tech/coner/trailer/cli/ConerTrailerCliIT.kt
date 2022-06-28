package tech.coner.trailer.cli

import assertk.all
import assertk.assertAll
import assertk.assertThat
import assertk.assertions.*
import com.github.ajalt.clikt.core.PrintHelpMessage
import com.github.ajalt.clikt.core.context
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import tech.coner.trailer.TestEvents
import tech.coner.trailer.TestParticipants
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.clikt.error
import tech.coner.trailer.cli.clikt.output
import tech.coner.trailer.cli.command.RootCommand
import tech.coner.trailer.cli.util.IntegrationTestAppArgumentBuilder
import tech.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import tech.coner.trailer.eventresults.EventResultsType
import tech.coner.trailer.di.Format
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.extension
import kotlin.io.path.nameWithoutExtension
import kotlin.io.path.readText

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
        command = ConerTrailerCli.createCommands()
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

        command.parse(appArgumentBuilder.configureDatabaseAdd(databaseName))

        assertAll {
            assertThat(testConsole.output, "console output").isNotEmpty()
            assertThat(testConsole.error, "console errors").isEmpty()
        }
    }

    @Test
    fun `It should make a motorsportreg request with wrong credentials and get an unauthorized response`() {
        val databaseName = "motorsportreg-wrong-credentials"
        command.parse(appArgumentBuilder.configureDatabaseAdd(databaseName))
        command.parse(appArgumentBuilder.configureDatabaseSnoozleInitialize())

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
        command.parse(appArgumentBuilder.configureDatabaseAdd(databaseName))
        command.parse(appArgumentBuilder.configureDatabaseSnoozleInitialize())
        val event = TestEvents.Lscc2019Simplified.points1
        command.parse(appArgumentBuilder.clubSet(event.policy.club))
        val seasonFixture = SeasonFixture.Lscc2019Simplified(crispyFishDir)
        command.parse(appArgumentBuilder.policyAdd(event.policy))

        command.parse(appArgumentBuilder.eventAddCrispyFish(
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
        val fileStream = Files.find(snoozleDir, 4, { path, attrs ->
            attrs.isRegularFile
                    && path.nameWithoutExtension == "${event.id}"
                    && path.extension == "json"
        })
        val eventEntity = fileStream.toList().single()
        assertThat(eventEntity.readText(), "persisted event entity").all {
            contains("${event.id}")
            contains(event.name)
        }
    }

    @Test
    fun `It should add a crispy fish person map entry`() {
        val databaseName = "add-crispy-fish-person-map-entry"
        command.parse(appArgumentBuilder.configureDatabaseAdd(databaseName))
        command.parse(appArgumentBuilder.configureDatabaseSnoozleInitialize())
        val event = TestEvents.Lscc2019Simplified.points1
        val seasonFixture = SeasonFixture.Lscc2019Simplified(crispyFishDir)
        command.parse(appArgumentBuilder.clubSet(event.policy.club))
        command.parse(appArgumentBuilder.policyAdd(event.policy))
        val participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF
        command.parse(appArgumentBuilder.personAdd(participant.person!!))
        command.parse(appArgumentBuilder.eventAddCrispyFish(
            event = event,
            crispyFishEventControlFile = seasonFixture.event1.ecfPath,
            crispyFishClassDefinitionFile = seasonFixture.classDefinitionPath
        ))

        command.parse(appArgumentBuilder.eventCrispyFishPersonMapAdd(
            event = event,
            participant = participant
        ))

        assertAll {
            assertThat(testConsole.output, "output").all {
                contains(event.name)
                contains("${event.date}")
                contains(participant.signage!!.classing!!.group!!.abbreviation)
                contains(participant.signage!!.classing!!.handicap.abbreviation)
                contains(participant.signage!!.number!!)
                contains("${participant.person!!.id}")
            }
            assertThat(testConsole.error, "error").isEmpty()
        }
    }

    @ParameterizedTest
    @MethodSource("tech.coner.trailer.cli.util.ParameterSources#provideArgumentsForEventResultsOfAllTypesAndAllFormats")
    fun `It should print event results of all types and all formats`(eventResultType: EventResultsType, format: Format) {
        val event = TestEvents.Lscc2019Simplified.points1
        val databaseName = "print-event-results-${eventResultType.key}-${format.name}"
        command.parse(appArgumentBuilder.configureDatabaseAdd(databaseName))
        command.parse(appArgumentBuilder.configureDatabaseSnoozleInitialize())
        val seasonFixture = SeasonFixture.Lscc2019Simplified(crispyFishDir)
        command.parse(appArgumentBuilder.clubSet(event.policy.club))
        command.parse(appArgumentBuilder.policyAdd(policy = event.policy))
        command.parse(appArgumentBuilder.eventAddCrispyFish(
            event = event,
            crispyFishEventControlFile = seasonFixture.event1.ecfPath,
            crispyFishClassDefinitionFile = seasonFixture.classDefinitionPath
        ))
        testConsole.clear()

        command.parse(appArgumentBuilder.eventResults(
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

    @Test
    fun `It should list event participants`() {
        val event = TestEvents.Lscc2019Simplified.points1
        val databaseName = "list-event-participants"
        command.parse(appArgumentBuilder.configureDatabaseAdd(databaseName))
        command.parse(appArgumentBuilder.configureDatabaseSnoozleInitialize())
        val seasonFixture = SeasonFixture.Lscc2019Simplified(crispyFishDir)
        command.parse(appArgumentBuilder.clubSet(event.policy.club))
        command.parse(appArgumentBuilder.policyAdd(policy = event.policy))
        command.parse(appArgumentBuilder.eventAddCrispyFish(
            event = event,
            crispyFishEventControlFile = seasonFixture.event1.ecfPath,
            crispyFishClassDefinitionFile = seasonFixture.classDefinitionPath
        ))
        testConsole.clear()

        command.parse(appArgumentBuilder.eventParticipantsList(event = event))

        assertThat(testConsole).all {
            output().transform { it.lines() }.all {
                index(1).all {
                    contains("Signage")
                    contains("First Name")
                    contains("Last Name")
                    contains("Member ID")
                    contains("Car Model")
                    contains("Car Color")
                }
                index(3).contains("HS 1")
                index(4).contains("Anastasia")
                index(5).contains("Drake")
                index(6).contains("1994 Mazda Miata")
                index(7).contains("WorldRallyBlue")
                index(8).contains("NOV ES 18")
                index(9).contains("Bryant")
            }
            error().isEmpty()
        }
    }

    private fun args(vararg args: String) = appArgumentBuilder.build(*args)
}