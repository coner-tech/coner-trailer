package tech.coner.trailer.cli.command.config

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEmpty
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import org.junit.jupiter.api.Test
import tech.coner.trailer.cli.command.BaseExecutableIT
import tech.coner.trailer.cli.util.error
import tech.coner.trailer.cli.util.exitCode
import tech.coner.trailer.cli.util.output

class ConfigDatabaseAddCommandExecutableIT : BaseExecutableIT() {

    @Test
    fun `It should add a database config`() {
        val databaseName = "arbitrary-database-name"

        val processOutcome = testCommand { configDatabaseAdd(databaseName) }

        assertThat(processOutcome).all {
            exitCode().isEqualTo(0)
            output().isNotNull().isNotEmpty()
            error().isNull()
        }
    }
}