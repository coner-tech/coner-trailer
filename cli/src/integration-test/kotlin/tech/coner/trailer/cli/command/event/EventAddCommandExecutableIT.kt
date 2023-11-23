package tech.coner.trailer.cli.command.event

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.exists
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNull
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
        val eventFile = snoozleDir.resolve("events").resolve("${event.id}.json")
        assertThat(eventFile, "event file").all {
            exists()
            transform("contents") { it.readText() }.all {
                contains("${event.id}")
                contains(event.name)
            }
        }
    }
}