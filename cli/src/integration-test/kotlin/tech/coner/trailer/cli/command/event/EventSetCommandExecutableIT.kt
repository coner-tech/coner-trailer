package tech.coner.trailer.cli.command.event

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.exists
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import kotlin.io.path.readText
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import tech.coner.trailer.Event
import tech.coner.trailer.TestEvents
import tech.coner.trailer.TestParticipants
import tech.coner.trailer.cli.command.BaseExecutableIT
import tech.coner.trailer.cli.util.error
import tech.coner.trailer.cli.util.isSuccess
import tech.coner.trailer.cli.util.output
import tech.coner.trailer.datasource.crispyfish.fixture.SeasonFixture

class EventSetCommandExecutableIT : BaseExecutableIT() {

    @ParameterizedTest
    @EnumSource(Event.Lifecycle::class)
    fun `It should set event lifecycle`(lifecycle: Event.Lifecycle) {
        val databaseName = "event-and-prerequisites"
        newArrange { configDatabaseAdd(databaseName) }
        val event = TestEvents.Lscc2019Simplified.points1
        val seasonFixture = SeasonFixture.Lscc2019Simplified(crispyFishDir)
        newArrange { configureDatabaseSnoozleInitialize() }
        newArrange { clubSet(event.policy.club) }
        newArrange { policyAdd(event.policy) }
        newArrange {
            eventAddCrispyFish(
                event = event,
                crispyFishEventControlFile = seasonFixture.event1.ecfPath,
                crispyFishClassDefinitionFile = seasonFixture.classDefinitionPath
            )
        }
        if (lifecycle >= Event.Lifecycle.ACTIVE) {
            TestParticipants.Lscc2019Points1Simplified.all.forEach { participant ->
                newArrange { personAdd(participant.person!!) }
                newArrange { eventCrispyFishPersonMapAdd(event, participant) }
            }
        }

        val processOutcome = newTestCommand {
            eventSet(
                eventId = event.id,
                lifecycle = lifecycle
            )
        }

        assertThat(processOutcome).all {
            isSuccess()
            output().isNotNull().all {
                contains(event.name)
                contains(lifecycle.name, ignoreCase = true)
            }
            error().isNull()
        }
        val file = snoozleDir.resolve("events").resolve("${event.id}.json")
        assertThat(file).all {
            exists()
            transform("contents") { it.readText() }.all {
                contains("${event.id}")
                contains(event.name)
                contains(lifecycle.name, ignoreCase = true)
            }
        }
    }
}