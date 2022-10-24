package tech.coner.trailer.cli.command.webapp

import com.github.ajalt.clikt.parameters.groups.cooccurring
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.enum
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.io.Webapp
import tech.coner.trailer.io.service.ConfigurationService
import tech.coner.trailer.webapp.results.resultsWebapp

class WebappCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "webapp",
    help = "Start a webapp"
) {

    private val webapp by option().enum<Webapp>().required()
    private val config by WebappConfigurationOptions().cooccurring()

    private val service: ConfigurationService by instance()

    override suspend fun coRun() {
        val config = config?.mapToIo()
            ?: service.getWebappConfiguration(webapp).getOrThrow()
        when (webapp) {
            Webapp.RESULTS -> resultsWebapp(di, config)
        }
    }
}