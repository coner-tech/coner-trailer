package tech.coner.trailer.cli.command.config

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.exists
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import kotlin.io.path.readText
import org.junit.jupiter.api.Test
import tech.coner.trailer.cli.command.BaseExecutableIT
import tech.coner.trailer.cli.util.error
import tech.coner.trailer.cli.util.isSuccess
import tech.coner.trailer.cli.util.output

class ConfigDatabaseAddCommandExecutableIT : BaseExecutableIT() {

    @Test
    fun `It should add a database config`() {
        val databaseName = "arbitrary-database-name"

        val processOutcome = testCommand { configDatabaseAdd(databaseName) }

        assertThat(processOutcome).all {
            isSuccess()
            output().isNotNull().contains(databaseName)
            error().isNull()
        }
        assertThat(configDir.resolve("config.json")).all {
            exists()
            transform("contents") { it.readText() }.contains(databaseName)
        }
    }
}