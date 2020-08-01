package org.coner.trailer.cli.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.output.CliktConsole
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.view.DatabaseConfigurationView

class ConfigDatabaseListCommand(
        useConsole: CliktConsole,
        private val view: DatabaseConfigurationView,
        private val config: ConfigurationService
) : CliktCommand(
        name = "list",
        help = "List database configurations"
) {

    init {
        context {
            console = useConsole
        }
    }

    override fun run() {
        echo(view.render(config.listDatabases()))
    }
}