package tech.coner.trailer.app.admin.command.event

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import org.junit.jupiter.api.Test
import tech.coner.trailer.app.admin.util.error
import tech.coner.trailer.app.admin.util.output
import tech.coner.trailer.datasource.crispyfish.fixture.SeasonFixture

class EventCheckCommandExecutableIT : tech.coner.trailer.app.admin.command.BaseExecutableIT() {

    @Test
    fun `It should check an event containing runs with invalid signage`() {
        val databaseName = "64-invalid-signage"
        arrange { configDatabaseAdd(databaseName) }
        arrange { configureDatabaseSnoozleInitialize() }
        val seasonFixture = SeasonFixture.Issue64CrispyFishStagingLinesInvalidSignage(temp = crispyFishDir)
        val event = seasonFixture.event.coreSeasonEvent.event
        arrange { clubSet(event.policy.club) }
        arrange { policyAdd(event.policy) }
        arrange {
            eventAddCrispyFish(
                event = event,
                crispyFishEventControlFile = seasonFixture.event.ecfPath,
                crispyFishClassDefinitionFile = seasonFixture.classDefinitionPath
            )
        }

        val processOutcome = testCommand { eventCheck(event) }

        assertThat(processOutcome).all {
            output().isNotNull().contains("CS 3 ")
            error().isNull()
        }
    }
}