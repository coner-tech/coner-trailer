package org.coner.trailer.cli.command.config

import com.github.ajalt.clikt.core.*
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.io.DatabaseConfiguration
import org.coner.trailer.cli.view.DatabaseConfigurationView
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.direct
import org.kodein.di.instance

class ConfigDatabaseGetCommand(
    di: DI,
    useConsole: CliktConsole
) : CliktCommand(
    name = "get",
    help = "Get database configuration"
), DIAware {

    init {
        context {
            console = useConsole
        }
    }

    override val di: DI by findOrSetObject { di }
    private val service: ConfigurationService by instance()
    private val view: DatabaseConfigurationView by instance()

    private val name: String by argument()

    override fun run() {
        val dbConfig = service.listDatabasesByName()[name]
        if (dbConfig == null) {
            echo("No database found with name")
            throw Abort()
        }
        echo(view.render(dbConfig))
    }
}