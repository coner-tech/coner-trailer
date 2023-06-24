package tech.coner.trailer.cli.command.config

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.enum
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.WebappConfigurationView
import tech.coner.trailer.io.Webapp
import tech.coner.trailer.io.service.ConfigurationService

class ConfigWebappGetCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "get",
    help = "Get configuration for a webapp"
) {

    override val diContext = diContextEnvironment()
    private val service: ConfigurationService by instance()
    private val view: WebappConfigurationView by instance()

    private val webapp: Webapp by argument().enum()

    override suspend fun coRun() {
        val config = service.getWebappConfiguration(webapp).getOrThrow()
        echo(view.render(webapp to config))
    }
}