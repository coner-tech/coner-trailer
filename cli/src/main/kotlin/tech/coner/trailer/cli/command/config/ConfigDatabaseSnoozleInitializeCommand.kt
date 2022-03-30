package tech.coner.trailer.cli.command.config

import com.github.ajalt.clikt.core.ProgramResult
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.datasource.snoozle.ConerTrailerDatabase

class ConfigDatabaseSnoozleInitializeCommand(
    override val di: DI,
    global: GlobalModel
) : BaseCommand(
    global = global,
    name = "initialize",
    help = """
        Initialize the snoozle database. This is a prerequisite for any other database operations.
    """.trimIndent()
),
    DIAware {

    override val diContext = diContext { global.requireEnvironment() }

    private val database: ConerTrailerDatabase by instance()

    override fun run() {
        val adminSession = database.openAdministrativeSession()
            .getOrElse {
                echo("Failed to open administrative session: ${it.message}", err = true)
                throw ProgramResult(1)
            }
        try {
            adminSession.initializeDatabase()
                .onFailure {
                    echo("Failed to initialize database: ${it.message}", err = true)
                    throw ProgramResult(1)
                }
        } finally {
            adminSession.close()
        }
    }
}