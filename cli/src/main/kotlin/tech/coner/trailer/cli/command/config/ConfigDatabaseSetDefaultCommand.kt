package tech.coner.trailer.cli.command.config

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.factory
import org.kodein.di.instance
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.DatabaseConfigurationView
import tech.coner.trailer.di.ConfigurationServiceFactory

class ConfigDatabaseSetDefaultCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
        name = "set-default",
        help = "Set named database to default"
), DIAware {

    private val serviceFactory: ConfigurationServiceFactory by factory()
    private val view: DatabaseConfigurationView by instance()

    private val name: String by argument()

    override fun run() {
        val service = serviceFactory(global.requireEnvironment().configurationServiceArgument)
        service.setDefaultDatabase(name)
            .onSuccess { echo(view.render(it)) }
            .onFailure { echo("Failed to set default database: ${it.message}", err = true) }
    }
}