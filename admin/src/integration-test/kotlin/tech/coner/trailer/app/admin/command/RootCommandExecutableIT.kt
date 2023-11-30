package tech.coner.trailer.app.admin.command

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import assertk.assertions.startsWith
import org.junit.jupiter.api.Test
import tech.coner.trailer.app.admin.util.error
import tech.coner.trailer.app.admin.util.exitCode
import tech.coner.trailer.app.admin.util.output

class RootCommandExecutableIT : BaseExecutableIT() {

    @Test
    fun `It should print help`() {
        val processOutcome = testCommand { root(help = true) }

        assertThat(processOutcome).all {
            exitCode().isEqualTo(0)
            output().isNotNull().startsWith("Usage: coner-trailer-admin")
            error().isNull()
        }
    }
}