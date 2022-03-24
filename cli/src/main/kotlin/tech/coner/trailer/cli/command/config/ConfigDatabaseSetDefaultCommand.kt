package tech.coner.trailer.cli.command.config

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.ProgramResult
import com.github.ajalt.clikt.parameters.arguments.argument
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.DatabaseConfigurationView
import tech.coner.trailer.io.service.ConfigurationService

class ConfigDatabaseSetDefaultCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
        name = "set-default",
        help = "Set named database to default"
), DIAware {

    override val diContext = diContext { global.requireEnvironment() }

    private val service: ConfigurationService by instance()
    private val view: DatabaseConfigurationView by instance()

    private val name: String by argument()

    override fun run() {
        service.setDefaultDatabase(name)
            .onSuccess { echo(view.render(it.defaultDbConfig)) }
            .onFailure {
                echo("Failed to set default database: ${it.message}", err = true)
                throw ProgramResult(1)
            }
    }
}