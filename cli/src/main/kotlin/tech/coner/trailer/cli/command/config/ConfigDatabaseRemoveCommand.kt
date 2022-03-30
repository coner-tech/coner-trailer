package tech.coner.trailer.cli.command.config

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.ProgramResult
import com.github.ajalt.clikt.parameters.arguments.argument
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.io.service.ConfigurationService

class ConfigDatabaseRemoveCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
        name = "remove",
        help = """
                Remove a database from the CLI app's config file.
                
                This is a non-destructive operation -- you can always add the database again. This will not affect the
                database itself.
            """.trimIndent()
), DIAware {

    override val diContext = diContext { global.requireEnvironment() }

    private val service: ConfigurationService by instance()

    private val name: String by argument()

    override fun run() {
        service.removeDatabase(name)
            .onFailure {
                echo("Failed to remove database: ${it.message}", err = true)
                throw ProgramResult(1)
            }
    }
}