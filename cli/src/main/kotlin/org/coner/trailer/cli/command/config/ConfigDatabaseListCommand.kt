package org.coner.trailer.cli.command.config

import com.github.ajalt.clikt.core.CliktCommand
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.cli.view.DatabaseConfigurationView
import org.coner.trailer.di.ConfigurationServiceFactory
import org.kodein.di.*

class ConfigDatabaseListCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
        name = "list",
        help = "List database configurations"
), DIAware {

    override val diContext = diContext { global.requireEnvironment() }
    private val configurationServiceFactory: ConfigurationServiceFactory by factory()
    private val view: DatabaseConfigurationView by instance()

    override fun run() {
        val service = configurationServiceFactory(global.requireEnvironment().configurationServiceArgument)
        echo(view.render(service.listDatabases()))
    }
}