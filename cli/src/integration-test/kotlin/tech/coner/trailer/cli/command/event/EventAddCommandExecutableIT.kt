package tech.coner.trailer.cli.command.event

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.extension
import kotlin.io.path.nameWithoutExtension
import kotlin.io.path.readText
import org.junit.jupiter.api.Test
import tech.coner.trailer.TestEvents
import tech.coner.trailer.cli.command.BaseExecutableIT
import tech.coner.trailer.cli.util.error
import tech.coner.trailer.cli.util.exitCode
import tech.coner.trailer.cli.util.output
import tech.coner.trailer.datasource.crispyfish.fixture.SeasonFixture

class EventAddCommandExecutableIT : BaseExecutableIT() {

    @Test
    fun `It should add an event`() {
        val databaseName = "event-and-prerequisites"
        newArrange { configDatabaseAdd(databaseName) }
        val event = TestEvents.Lscc2019Simplified.points1
        val seasonFixture = SeasonFixture.Lscc2019Simplified(crispyFishDir)
        newArrange { configureDatabaseSnoozleInitialize() }
        newArrange { clubSet(event.policy.club) }
        newArrange { policyAdd(event.policy) }

        val processOutcome = newTestCommand { eventAddCrispyFish(
            event = event,
            crispyFishEventControlFile = seasonFixture.event1.ecfPath,
            crispyFishClassDefinitionFile = seasonFixture.classDefinitionPath
        ) }

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
}