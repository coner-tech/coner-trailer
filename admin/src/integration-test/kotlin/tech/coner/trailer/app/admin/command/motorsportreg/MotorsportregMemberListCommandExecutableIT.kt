package tech.coner.trailer.app.admin.command.motorsportreg

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isNotEqualTo
import assertk.assertions.isNotNull
import org.junit.jupiter.api.Test
import tech.coner.trailer.app.admin.util.error
import tech.coner.trailer.app.admin.util.exitCode

class MotorsportregMemberListCommandExecutableIT : tech.coner.trailer.app.admin.command.BaseExecutableIT() {

    @Test
    fun `It should make a motorsportreg request with wrong credentials and get an unauthorized response`() {
        val databaseName = "motorsportreg-wrong-credentials"
        arrange { configDatabaseAdd(databaseName) }
        arrange { configureDatabaseSnoozleInitialize() }

        val processOutcome = testCommand {
            motorsportregMemberList(
                motorsportRegUsername = "wrong",
                motorsportRegPassword = "wrong",
                motorsportRegOrganizationId = "wrong"
            )
        }

        assertThat(processOutcome).all {
            exitCode().isNotEqualTo(0)
            error().isNotNull().all {
                contains("Failed to fetch members", ignoreCase = true)
                contains("401")
            }
        }
    }
}