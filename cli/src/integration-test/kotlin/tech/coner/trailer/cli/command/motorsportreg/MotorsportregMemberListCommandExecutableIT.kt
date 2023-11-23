package tech.coner.trailer.cli.command.motorsportreg

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isNotEqualTo
import assertk.assertions.isNotNull
import org.junit.jupiter.api.Test
import tech.coner.trailer.cli.command.BaseExecutableIT
import tech.coner.trailer.cli.util.error
import tech.coner.trailer.cli.util.exitCode

class MotorsportregMemberListCommandExecutableIT : BaseExecutableIT() {

    @Test
    fun `It should make a motorsportreg request with wrong credentials and get an unauthorized response`() {
        val databaseName = "motorsportreg-wrong-credentials"
        newArrange { configDatabaseAdd(databaseName) }
        newArrange { configureDatabaseSnoozleInitialize() }

        val processOutcome = newTestCommand {
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