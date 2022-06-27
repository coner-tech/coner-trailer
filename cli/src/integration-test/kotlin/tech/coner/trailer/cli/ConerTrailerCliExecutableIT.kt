package tech.coner.trailer.cli

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import com.github.ajalt.clikt.core.context
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Timeout
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import tech.coner.trailer.TestEvents
import tech.coner.trailer.TestParticipants
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.util.*
import tech.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import tech.coner.trailer.eventresults.EventResultsType
import tech.coner.trailer.render.Format
import java.nio.file.Files
import java.nio.file.Path
import java.util.concurrent.TimeUnit
import kotlin.io.path.createDirectory
import kotlin.io.path.extension
import kotlin.io.path.nameWithoutExtension
import kotlin.io.path.readText

@Timeout(value = 1, unit = TimeUnit.MINUTES)
class ConerTrailerCliExecutableIT {

    lateinit var executor: ConerTrailerCliProcessExecutor

    lateinit var appArgumentBuilder: IntegrationTestAppArgumentBuilder

    @TempDir lateinit var testDir: Path
    lateinit var configDir: Path
    lateinit var snoozleDir: Path
    lateinit var crispyFishDir: Path

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
        executor = ConerTrailerCliProcessExecutor(
            processCommandArrayFactory =  when (System.getProperty("coner-trailer-cli.argument-factory", "shaded-jar")) {
                "shaded-jar" -> ShadedJarCommandArrayFactory()
                "native-image" -> NativeImageCommandArrayFactory()
                else -> throw IllegalStateException("Unknown argument factory")
            },
            appArgumentBuilder = appArgumentBuilder
        )
    }

    @Test
    fun `It should print help`() {
        val processOutcome = executor.exec("--help").awaitOutcome()

        assertThat(processOutcome).all {
            exitCode().isEqualTo(0)
            output().isNotNull().startsWith("Usage: coner-trailer-cli")
            error().isNull()
        }
    }

    @Test
    fun `It should add a database config`() {
        val databaseName = "arbitrary-database-name"

        val processOutcome = executor.configureDatabaseAdd(databaseName).awaitOutcome()

        assertThat(processOutcome).all {
            exitCode().isEqualTo(0)
            output().isNotNull().isNotEmpty()
            error().isNull()
        }
    }

    @Test
    fun `It should make a motorsportreg request with wrong credentials and get an unauthorized response`() {
        val databaseName = "motorsportreg-wrong-credentials"
        arrange { configureDatabaseAdd(databaseName) }
        arrange { configureDatabaseSnoozleInitialize() }

        val processOutcome = executor.exec(
            "motorsportreg", "member", "list",
            environment = arrayOf(
                "MOTORSPORTREG_ORGANIZATION_ID=wrong",
                "MOTORSPORTREG_USERNAME=wrong",
                "MOTORSPORTREG_PASSWORD=wrong"
            )
        ).awaitOutcome()

        assertThat(processOutcome).all {
            exitCode().isNotEqualTo(0)
            error().isNotNull().all {
                contains("Failed to fetch members", ignoreCase = true)
                contains("401")
            }
        }
    }

    @Test
    fun `It should add an event`() {
        val databaseName = "event-and-prerequisites"
        arrange { configureDatabaseAdd(databaseName) }
        val event = TestEvents.Lscc2019Simplified.points1
        val seasonFixture = SeasonFixture.Lscc2019Simplified(crispyFishDir)
        arrange { configureDatabaseSnoozleInitialize() }
        arrange { clubSet(event.policy.club) }
        arrange { policyAdd(policy = event.policy) }

        val processOutcome = executor.eventAddCrispyFish(
            event = event,
            crispyFishEventControlFile = seasonFixture.event1.ecfPath,
            crispyFishClassDefinitionFile = seasonFixture.classDefinitionPath
        ).awaitOutcome()

        assertThat(processOutcome).all {
            exitCode().isEqualTo(0)
            output().isNotNull().all {
                contains(event.name)
                contains("${event.date}")
            }
            error().isNull()
        }
        val files = Files.find(snoozleDir, 4, { path, attrs ->
            attrs.isRegularFile
                    && path.nameWithoutExtension == "${event.id}"
                    && path.extension == "json"
        })
        val eventEntity: Path = files.toList().single()
        assertThat(eventEntity.readText(), "persisted event entity").all {
            contains("${event.id}")
            contains(event.name)
        }
    }

    @Test
    fun `It should add a crispy fish person map entry`() {
        val databaseName = "add-crispy-fish-person-map-entry"
        arrange { configureDatabaseAdd(databaseName) }
        val event = TestEvents.Lscc2019Simplified.points1
        val seasonFixture = SeasonFixture.Lscc2019Simplified(crispyFishDir)
        val participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF
        arrange { configureDatabaseSnoozleInitialize() }
        arrange { clubSet(event.policy.club) }
        arrange { policyAdd(event.policy) }
        arrange { personAdd(participant.person!!) }
        arrange { eventAddCrispyFish(
            event = event,
            crispyFishEventControlFile = seasonFixture.event1.ecfPath,
            crispyFishClassDefinitionFile = seasonFixture.classDefinitionPath
        ) }

        val processOutcome = executor.eventCrispyFishPersonMapAdd(
            event = event,
            participant = participant
        ).awaitOutcome()

        assertThat(processOutcome).all {
            exitCode().isEqualTo(0)
            output().isNotNull().all {
                contains(event.name)
                contains("${event.date}")
                contains(participant.signage!!.classing!!.group!!.abbreviation)
                contains(participant.signage!!.classing!!.handicap.abbreviation)
                contains(participant.signage!!.number!!)
                contains("${participant.person!!.id}")
            }
            error().isNull()
        }
    }

    @ParameterizedTest
    @MethodSource("tech.coner.trailer.cli.util.ParameterSources#provideArgumentsForEventResultsOfAllTypesAndAllFormats")
    fun `It should print event results of all types and all formats`(eventResultType: EventResultsType, format: Format) {
        val event = TestEvents.Lscc2019Simplified.points1
        val databaseName = "print-event-results-${eventResultType.key}-${format.name}"
        arrange { configureDatabaseAdd(databaseName) }
        val seasonFixture = SeasonFixture.Lscc2019Simplified(crispyFishDir)
        arrange { configureDatabaseSnoozleInitialize() }
        arrange { clubSet(event.policy.club) }
        arrange { policyAdd(event.policy) }
        arrange { eventAddCrispyFish(
            event = event,
            crispyFishEventControlFile = seasonFixture.event1.ecfPath,
            crispyFishClassDefinitionFile = seasonFixture.classDefinitionPath
        ) }

        val processOutcome = executor.eventResults(
            event = event,
            type = eventResultType,
            format = format,
            output = "console"
        ).awaitOutcome()

        assertThat(processOutcome).all {
            exitCode().isEqualTo(0)
            output().isNotNull().all {
                contains(event.name)
                when (format) {
                    Format.HTML -> {
                        startsWith("<!DOCTYPE html>")
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
            error().isNull()
        }
    }
    
    private fun arrange(fn: IntegrationTestAppArgumentBuilder.() -> Array<String>) {
        ConerTrailerCli.createCommands()
            .context {
                console = StringBufferConsole()
            }
            .parse(appArgumentBuilder.fn())
    }
}
