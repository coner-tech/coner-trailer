package org.coner.trailer.cli.command.config

import org.coner.trailer.cli.command.BaseCommand
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.datasource.snoozle.ConerTrailerDatabase
import org.kodein.di.*

class ConfigDatabaseSnoozleInitializeCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    global = global,
    name = "initialize",
    help = """
        Initialize the snoozle database. This is a prerequisite for any other database operations.
    """.trimIndent()
), DIAware by di {

    override val diContext = diContext { global.requireEnvironment() }

    private val database: ConerTrailerDatabase by instance()

    override fun run() {
        val adminSession = database.openAdministrativeSession().getOrThrow()
        try {
            val result = adminSession.initializeDatabase()
            result.getOrThrow()
        } finally {
            adminSession.close()
        }
    }
}