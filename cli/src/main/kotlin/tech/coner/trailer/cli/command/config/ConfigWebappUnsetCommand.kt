package tech.coner.trailer.cli.command.config

import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.enum
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.io.Webapp
import tech.coner.trailer.io.service.ConfigurationService

class ConfigWebappUnsetCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "unset",
    help = "Unset configuration for a webapp"
) {

    override val diContext = diContextEnvironment()
    private val service: ConfigurationService by instance()

    private val webapp: Webapp by option().enum<Webapp>().required()

    override suspend fun CoroutineScope.coRun() {
        service.configureWebapp(webapp, null).getOrThrow()
    }
}