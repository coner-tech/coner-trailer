package org.coner.trailer.cli.command.config

import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.required
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.io.DatabaseConfiguration

class ConfigDatabaseRemoveCommand(
        private val service: ConfigurationService
) : CliktCommand(
        name = "remove",
        help = """
                Remove a database from the CLI app's config file.
                
                This is a non-destructive operation -- you can always add the database again. This will not affect the
                database itself.
            """.trimIndent()
) {

    private val dbConfig: DatabaseConfiguration by databaseNameOption(service)
            .required()

    override fun run() {
        if (dbConfig == service.noDatabase) throw Abort()
        service.removeDatabase(dbConfig)
    }
}