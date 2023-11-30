package tech.coner.trailer.app.admin.command.config

import com.github.ajalt.clikt.parameters.arguments.argument
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.diContext
import org.kodein.di.instance
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel
import tech.coner.trailer.app.admin.util.succeedOrThrow
import tech.coner.trailer.app.admin.view.DatabaseConfigurationView
import tech.coner.trailer.io.service.ConfigurationService

class ConfigDatabaseSetDefaultCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
        name = "set-default",
        help = "Set named database to default"
) {

    override val diContext = diContext { global.requireEnvironment() }

    private val service: ConfigurationService by instance()
    private val view: DatabaseConfigurationView by instance()

    private val name: String by argument()

    override suspend fun CoroutineScope.coRun() {
        service.setDefaultDatabase(name)
            .succeedOrThrow { echo(view.render(it.defaultDbConfig)) }
    }
}