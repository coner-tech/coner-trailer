package tech.coner.trailer.cli.command.config

import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.DatabaseConfigurationView
import tech.coner.trailer.di.ConfigurationServiceFactory
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.factory
import org.kodein.di.instance

class ConfigDatabaseGetCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
    name = "get",
    help = "Get database configuration"
), DIAware {

    private val configurationServiceFactory: ConfigurationServiceFactory by factory()
    private val view: DatabaseConfigurationView by instance()

    private val name: String by argument()

    override fun run() {
        val service = configurationServiceFactory(global.requireEnvironment().configurationServiceArgument)
        val dbConfig = service.listDatabasesByName()[name]
        if (dbConfig == null) {
            echo("No database found with name")
            throw Abort()
        }
        echo(view.render(dbConfig))
    }
}