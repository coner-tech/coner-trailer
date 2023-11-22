package tech.coner.trailer.cli.command.club

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import org.junit.jupiter.api.Test
import tech.coner.trailer.TestClubs
import tech.coner.trailer.cli.command.BaseExecutableIT
import tech.coner.trailer.cli.util.error
import tech.coner.trailer.cli.util.exitCode
import tech.coner.trailer.cli.util.output

class ClubSetCommandExecutableIT : BaseExecutableIT() {

    @Test
    fun `It should set club in newly created database`() {
        val club = TestClubs.lscc
        val databaseName = "newly-created"
        arrange { configDatabaseAdd(databaseName) }
        arrange { configureDatabaseSnoozleInitialize() }

        val processOutcome = testCommand { clubSet(club) }

        assertThat(processOutcome).all {
            exitCode().isEqualTo(0)
            output().isNotNull().contains(club.name)
            error().isNull()
        }
    }
}