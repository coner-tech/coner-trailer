package tech.coner.trailer.app.admin.command.event.run

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.index
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import org.junit.jupiter.api.Test
import tech.coner.trailer.TestEvents
import tech.coner.trailer.app.admin.command.BaseExecutableIT
import tech.coner.trailer.app.admin.util.error
import tech.coner.trailer.app.admin.util.output
import tech.coner.trailer.datasource.crispyfish.fixture.SeasonFixture

class EventRunListCommandExecutableIT : BaseExecutableIT() {

    @Test
    fun `It should list event runs`() {
        val event = TestEvents.Lscc2019Simplified.points1
        val databaseName = "list-event-runs"
        arrange { configDatabaseAdd(databaseName) }
        arrange { configureDatabaseSnoozleInitialize() }
        arrange { clubSet(event.policy.club) }
        arrange { policyAdd(event.policy) }
        val seasonFixture = SeasonFixture.Lscc2019Simplified(crispyFishDir)
        arrange {
            eventAddCrispyFish(
                event = event,
                crispyFishEventControlFile = seasonFixture.event1.ecfPath,
                crispyFishClassDefinitionFile = seasonFixture.classDefinitionPath
            )
        }

        val processOutcome = testCommand { eventRunList(event) }

        assertThat(processOutcome).all {
            output().isNotNull().transform("lines") { it.lines() }.all {
                index(1).all {
                    contains("Sequence")
                    contains("Signage")
                    contains("Name")
                    contains("Car Model")
                    contains("Car Color")
                    contains("Time")
                    contains("Penalties")
                    contains("Rerun")
                }
                index(3).all {
                    contains("1 ")
                    contains("STR 1")
                    contains("Eugene Drake")
                    contains("1999 Mazda Miata")
                    contains("49.367")
                }
                index(4).contains("STR 1")
                index(5).contains("Eugene Drake")
                index(6).all {
                    contains("49.573")
                    contains("DNF")
                }
                index(7).all {
                    contains("5 ")
                    contains("STR 1")
                    contains("Eugene Drake")
                    contains("1999 Mazda Miata")
                    contains("47.544")
                }
                index(8).all {
                    contains("6 ")
                    contains("STR 23")
                    contains("Jimmy Mckenzie")
                    contains("1994 Mazda Miata")
                    contains("White")
                    contains("50.115")
                    contains("+2")
                }
                index(37).all {
                    contains("35 ")
                    contains("NOV ES 18")
                    contains("Dominic Rogers")
                    contains("2002 Mazda Miata")
                    contains("Blue")
                    contains("52.447")
                }
            }
            error().isNull()
        }
    }
}