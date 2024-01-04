package tech.coner.trailer.app.admin.command.club

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import org.junit.jupiter.api.Test
import tech.coner.trailer.TestClubs
import tech.coner.trailer.app.admin.command.BaseExecutableIT
import tech.coner.trailer.app.admin.util.error
import tech.coner.trailer.app.admin.util.exitCode
import tech.coner.trailer.app.admin.util.output
import kotlin.io.path.readText

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
        assertThat(snoozleDir.resolve("club.json")).all {
            exists()
            transform("contents") { it.readText() }.contains(club.name)
        }
    }
}