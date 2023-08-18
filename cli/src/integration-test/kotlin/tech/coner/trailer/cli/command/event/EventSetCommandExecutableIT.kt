package tech.coner.trailer.cli.command.event

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import tech.coner.trailer.Event
import tech.coner.trailer.TestEvents
import tech.coner.trailer.TestParticipants
import tech.coner.trailer.cli.command.BaseExecutableIT
import tech.coner.trailer.cli.util.error
import tech.coner.trailer.cli.util.exitCode
import tech.coner.trailer.cli.util.output
import tech.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import kotlin.io.path.extension
import kotlin.io.path.nameWithoutExtension
import kotlin.io.path.readText
import kotlin.io.path.walk

class EventSetCommandExecutableIT : BaseExecutableIT() {

    @ParameterizedTest
    @EnumSource(Event.Lifecycle::class)
    fun `It should set event lifecycle`(lifecycle: Event.Lifecycle) {
        val databaseName = "event-and-prerequisites"
        arrange { configDatabaseAdd(databaseName) }
        val event = TestEvents.Lscc2019Simplified.points1
        val seasonFixture = SeasonFixture.Lscc2019Simplified(crispyFishDir)
        arrange { configureDatabaseSnoozleInitialize() }
        arrange { clubSet(event.policy.club) }
        arrange { policyAdd(event.policy) }
        arrange {
            eventAddCrispyFish(
                event = event,
                crispyFishEventControlFile = seasonFixture.event1.ecfPath,
                crispyFishClassDefinitionFile = seasonFixture.classDefinitionPath
            )
        }
        if (lifecycle >= Event.Lifecycle.ACTIVE) {
            TestParticipants.Lscc2019Points1Simplified.all.forEach { participant ->
                arrange { personAdd(participant.person!!) }
                arrange { eventCrispyFishPersonMapAdd(event, participant) }
            }
        }

        val processOutcome = testCommand {
            eventSet(
                eventId = event.id,
                lifecycle = lifecycle
            )
        }

        assertThat(processOutcome).all {
            exitCode().isEqualTo(0)
            output().isNotNull().all {
                contains(event.name)
                contains(lifecycle.name, ignoreCase = true)
            }
            error().isNull()
        }
        val file = snoozleDir.walk()
            .single { it.nameWithoutExtension == "${event.id}" && it.extension == "json" }
        val persisted = file.readText()
        assertThat(persisted).all {
            contains("${event.id}")
            contains(event.name)
            contains(lifecycle.name, ignoreCase = true)
        }
    }
}