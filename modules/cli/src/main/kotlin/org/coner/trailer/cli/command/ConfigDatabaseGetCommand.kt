package org.coner.trailer.cli.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.choice
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.io.DatabaseConfiguration

class ConfigDatabaseGetCommand(
        config: ConfigurationService
) : CliktCommand(
        name = "get",
        help = "Get database configuration"
), ConfigDatabaseCommand.Subcommand {
    val dbConfig: DatabaseConfiguration by option(names = *arrayOf("--name"))
            .choice(config.listDatabasesByName())
            .required()

    override fun run() {
        echo(render(dbConfig))
    }
}