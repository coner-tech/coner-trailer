package tech.coner.trailer.cli.command.event.participant

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.hasSize
import assertk.assertions.index
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import org.junit.jupiter.api.Test
import tech.coner.trailer.TestEvents
import tech.coner.trailer.cli.command.BaseExecutableIT
import tech.coner.trailer.cli.util.error
import tech.coner.trailer.cli.util.isSuccess
import tech.coner.trailer.cli.util.output
import tech.coner.trailer.datasource.crispyfish.fixture.SeasonFixture

class EventParticipantListCommandExecutableIT : BaseExecutableIT() {

    @Test
    fun `It should list event participants`() {
        val event = TestEvents.Lscc2019Simplified.points1
        val databaseName = "list-event-participants"
        newArrange { configDatabaseAdd(databaseName) }
        newArrange { configureDatabaseSnoozleInitialize() }
        newArrange { clubSet(event.policy.club) }
        newArrange { policyAdd(event.policy) }
        val seasonFixture = SeasonFixture.Lscc2019Simplified(crispyFishDir)
        newArrange { eventAddCrispyFish(
            event = event,
            crispyFishEventControlFile = seasonFixture.event1.ecfPath,
            crispyFishClassDefinitionFile = seasonFixture.classDefinitionPath
        ) }

        val processOutcome = newTestCommand { eventParticipantList(event) }

        assertThat(processOutcome).all {
            isSuccess()
            output().isNotNull().transform("lines") { it.lines() }.all {
                hasSize(12)
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
            error().isNull()
        }
    }
}