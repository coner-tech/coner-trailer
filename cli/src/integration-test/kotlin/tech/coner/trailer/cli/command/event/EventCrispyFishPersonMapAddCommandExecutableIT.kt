package tech.coner.trailer.cli.command.event

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import org.junit.jupiter.api.Test
import tech.coner.trailer.TestEvents
import tech.coner.trailer.TestParticipants
import tech.coner.trailer.cli.command.BaseExecutableIT
import tech.coner.trailer.cli.util.error
import tech.coner.trailer.cli.util.exitCode
import tech.coner.trailer.cli.util.output
import tech.coner.trailer.datasource.crispyfish.fixture.SeasonFixture

class EventCrispyFishPersonMapAddCommandExecutableIT : BaseExecutableIT() {

    @Test
    fun `It should add a crispy fish person map entry`() {
        val databaseName = "add-crispy-fish-person-map-entry"
        arrange { configDatabaseAdd(databaseName) }
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

        val processOutcome = testCommand { eventCrispyFishPersonMapAdd(
            event = event,
            participant = participant
        ) }

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
}