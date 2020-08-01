package org.coner.trailer.cli.command

import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.choice
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.io.DatabaseConfiguration

class ConfigDatabaseRemoveCommand(
        private val config: ConfigurationService
) : CliktCommand(
        name = "remove",
        help = """
                Remove a database from the CLI app's config file.
                
                This is a non-destructive operation -- you can always add the database again. This will not affect the
                database itself.
            """.trimIndent()
) {

    val dbConfig: DatabaseConfiguration by option(names = *arrayOf("--name"))
            .choice(config.listDatabasesByName())
            .required()

    override fun run() {
        if (dbConfig == config.noDatabase) throw Abort()
        config.removeDatabase(dbConfig)
    }
}