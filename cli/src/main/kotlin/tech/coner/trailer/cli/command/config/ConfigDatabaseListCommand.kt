package tech.coner.trailer.cli.command.config

import com.github.ajalt.clikt.core.CliktCommand
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.DatabaseConfigurationView
import tech.coner.trailer.io.service.ConfigurationService

class ConfigDatabaseListCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
        name = "list",
        help = "List database configurations"
), DIAware {

    override val diContext = diContext { global.requireEnvironment() }

    private val service: ConfigurationService by instance()
    private val view: DatabaseConfigurationView by instance()

    override fun run() {
        echo(view.render(service.listDatabases()))
    }
}