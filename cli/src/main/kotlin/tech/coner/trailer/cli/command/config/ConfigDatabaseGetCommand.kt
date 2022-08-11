package tech.coner.trailer.cli.command.config

import com.github.ajalt.clikt.core.ProgramResult
import com.github.ajalt.clikt.parameters.arguments.argument
import org.kodein.di.DI
import org.kodein.di.diContext
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.DatabaseConfigurationView
import tech.coner.trailer.io.service.ConfigurationService

class ConfigDatabaseGetCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "get",
    help = "Get database configuration"
) {

    override val diContext = diContext { global.requireEnvironment() }

    private val service: ConfigurationService by instance()
    private val view: DatabaseConfigurationView by instance()

    private val name: String by argument()

    override suspend fun coRun() {
        service.findDatabaseByName(name)
            .onSuccess { echo(view.render(it)) }
            .onFailure {
                echo("Failed to find database by name: ${it.message}", err = true)
                throw ProgramResult(1)
            }
    }
}