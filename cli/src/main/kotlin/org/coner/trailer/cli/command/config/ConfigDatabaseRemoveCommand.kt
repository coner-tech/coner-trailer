package org.coner.trailer.cli.command.config

import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.required
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.io.DatabaseConfiguration
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class ConfigDatabaseRemoveCommand(
        di: DI
) : CliktCommand(
        name = "remove",
        help = """
                Remove a database from the CLI app's config file.
                
                This is a non-destructive operation -- you can always add the database again. This will not affect the
                database itself.
            """.trimIndent()
), DIAware {

    override val di: DI by findOrSetObject { di }
    private val service: ConfigurationService by instance()

    private val name: String by argument()

    override fun run() {
        val dbConfig = service.listDatabasesByName()[name]
        if (dbConfig == null) {
            echo("No database found with name")
            throw Abort()
        }
        service.removeDatabase(dbConfig)
    }
}