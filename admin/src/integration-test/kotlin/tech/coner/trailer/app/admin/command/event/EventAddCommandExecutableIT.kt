package tech.coner.trailer.app.admin.command.event

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import org.junit.jupiter.api.Test
import tech.coner.trailer.TestEvents
import tech.coner.trailer.app.admin.util.error
import tech.coner.trailer.app.admin.util.exitCode
import tech.coner.trailer.app.admin.util.output
import tech.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import kotlin.io.path.readText

class EventAddCommandExecutableIT : tech.coner.trailer.app.admin.command.BaseExecutableIT() {

    @Test
    fun `It should add an event`() {
        val databaseName = "event-and-prerequisites"
        arrange { configDatabaseAdd(databaseName) }
        val event = TestEvents.Lscc2019Simplified.points1
        val seasonFixture = SeasonFixture.Lscc2019Simplified(crispyFishDir)
        arrange { configureDatabaseSnoozleInitialize() }
        arrange { clubSet(event.policy.club) }
        arrange { policyAdd(event.policy) }

        val processOutcome = testCommand {
            eventAddCrispyFish(
                event = event,
                crispyFishEventControlFile = seasonFixture.event1.ecfPath,
                crispyFishClassDefinitionFile = seasonFixture.classDefinitionPath
            )
        }

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