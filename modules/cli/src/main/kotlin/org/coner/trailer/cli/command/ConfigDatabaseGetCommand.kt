package org.coner.trailer.cli.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.choice
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.io.DatabaseConfiguration
import org.coner.trailer.cli.view.DatabaseConfigurationView

class ConfigDatabaseGetCommand(
        useConsole: CliktConsole,
        config: ConfigurationService,
        private val view: DatabaseConfigurationView
) : CliktCommand(
        name = "get",
        help = "Get database configuration"
), ConfigDatabaseCommand.Subcommand {

    init {
        context {
            console = useConsole
        }
    }

    private val dbConfig: DatabaseConfiguration by option(
            names = *arrayOf("--name")
    )
            .choice(config.listDatabasesByName())
            .required()

    override fun run() {
        echo(view.render(dbConfig))
    }
}