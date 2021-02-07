package org.coner.trailer.cli.command.config

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.findOrSetObject
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.view.DatabaseConfigurationView
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class ConfigDatabaseListCommand(
    di: DI
) : CliktCommand(
        name = "list",
        help = "List database configurations"
), DIAware {

    override val di: DI by findOrSetObject { di }
    private val service: ConfigurationService by instance()
    private val view: DatabaseConfigurationView by instance()

    override fun run() {
        echo(view.render(service.listDatabases()))
    }
}