package tech.coner.trailer.cli

import assertk.all
import assertk.assertAll
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.endsWith
import assertk.assertions.isEmpty
import assertk.assertions.startsWith
import com.github.ajalt.clikt.core.context
import java.nio.file.Path
import kotlin.io.path.createDirectory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import tech.coner.trailer.TestEvents
import tech.coner.trailer.cli.clikt.StringBuilderConsole
import tech.coner.trailer.cli.command.RootCommand
import tech.coner.trailer.cli.di.Invocation
import tech.coner.trailer.cli.util.IntegrationTestAppArgumentBuilder
import tech.coner.trailer.cli.util.SubcommandArgumentsFactory
import tech.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import tech.coner.trailer.eventresults.EventResultsType
import tech.coner.trailer.presentation.di.Format

class ConerTrailerCliIT {

    lateinit var invocation: Invocation
    lateinit var command: RootCommand

    @TempDir lateinit var testDir: Path
    lateinit var configDir: Path
    lateinit var snoozleDir: Path
    lateinit var crispyFishDir: Path

    lateinit var argumentsFactory: SubcommandArgumentsFactory
    lateinit var appArgumentBuilder: IntegrationTestAppArgumentBuilder
    lateinit var testConsole: StringBuilderConsole


    @BeforeEach
    fun before() {
        configDir = testDir.resolve("config").apply { createDirectory() }
        snoozleDir = testDir.resolve("snoozle").apply { createDirectory() }
        crispyFishDir = testDir.resolve("crispy-fish").apply { createDirectory() }
        argumentsFactory = SubcommandArgumentsFactory(
            snoozleDir = snoozleDir,
            crispyFishDir = crispyFishDir
        )
        appArgumentBuilder = IntegrationTestAppArgumentBuilder(
            configDir = configDir,
            snoozleDir = snoozleDir,
            crispyFishDir = crispyFishDir
        )
        testConsole = StringBuilderConsole()
        invocation = ConerTrailerCli.createInvocation()
        command = ConerTrailerCli.createRootCommand(invocation)
            .context {
                console = testConsole
            }
    }

    @ParameterizedTest
    @MethodSource("tech.coner.trailer.cli.util.ParameterSources#provideArgumentsForEventResultsOfAllTypesAndAllFormats")
    fun `It should print event results of all types and all formats`(eventResultType: EventResultsType, format: Format) {
        val event = TestEvents.Lscc2019Simplified.points1
        val databaseName = "print-event-results-${eventResultType.key}-${format.name}"
        command.parse(appArgumentBuilder.configDatabaseAdd(databaseName))
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
                    Format.TEXT -> {
                        contains(eventResultType.titleShort)
                        contains("───")
                        contains("─┬─")
                        contains("─┴─")
                        contains("│")
                        contains("├")
                        contains("┤")
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
