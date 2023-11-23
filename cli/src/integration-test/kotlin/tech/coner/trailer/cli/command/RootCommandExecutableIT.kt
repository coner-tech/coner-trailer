package tech.coner.trailer.cli.command

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import assertk.assertions.startsWith
import org.junit.jupiter.api.Test
import tech.coner.trailer.cli.util.error
import tech.coner.trailer.cli.util.exitCode
import tech.coner.trailer.cli.util.output

class RootCommandExecutableIT : BaseExecutableIT() {

    @Test
    fun `It should print help`() {
        val processOutcome = newTestCommand { root(help = true) }

        assertThat(processOutcome).all {
            exitCode().isEqualTo(0)
            output().isNotNull().startsWith("Usage: coner-trailer-cli")
            error().isNull()
        }
    }
}