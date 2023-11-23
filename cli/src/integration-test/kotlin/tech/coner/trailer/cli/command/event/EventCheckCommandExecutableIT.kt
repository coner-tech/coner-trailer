package tech.coner.trailer.cli.command.event

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import org.junit.jupiter.api.Test
import tech.coner.trailer.cli.command.BaseExecutableIT
import tech.coner.trailer.cli.util.error
import tech.coner.trailer.cli.util.output
import tech.coner.trailer.datasource.crispyfish.fixture.SeasonFixture

class EventCheckCommandExecutableIT : BaseExecutableIT() {

    @Test
    fun `It should check an event containing runs with invalid signage`() {
        val databaseName = "64-invalid-signage"
        newArrange { configDatabaseAdd(databaseName) }
        newArrange { configureDatabaseSnoozleInitialize() }
        val seasonFixture = SeasonFixture.Issue64CrispyFishStagingLinesInvalidSignage(temp = crispyFishDir)
        val event = seasonFixture.event.coreSeasonEvent.event
        newArrange { clubSet(event.policy.club) }
        newArrange { policyAdd(event.policy) }
        newArrange { eventAddCrispyFish(
            event = event,
            crispyFishEventControlFile = seasonFixture.event.ecfPath,
            crispyFishClassDefinitionFile = seasonFixture.classDefinitionPath
        ) }

        val processOutcome = newTestCommand { eventCheck(event) }

        assertThat(processOutcome).all {
            output().isNotNull().contains("CS 3 ")
            error().isNull()
        }
    }
}