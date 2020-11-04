package org.coner.trailer.cli.command.config

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.parameters.options.required
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.io.DatabaseConfiguration
import org.coner.trailer.cli.view.DatabaseConfigurationView

class ConfigDatabaseGetCommand(
        useConsole: CliktConsole,
        service: ConfigurationService,
        private val view: DatabaseConfigurationView
) : CliktCommand(
        name = "get",
        help = "Get database configuration"
) {

    init {
        context {
            console = useConsole
        }
    }

    private val dbConfig: DatabaseConfiguration by databaseNameOption(service)
            .required()

    override fun run() {
        echo(view.render(dbConfig))
    }
}