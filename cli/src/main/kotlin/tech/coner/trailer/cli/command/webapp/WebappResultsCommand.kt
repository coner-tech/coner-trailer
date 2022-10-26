package tech.coner.trailer.cli.command.webapp

import com.github.ajalt.clikt.parameters.groups.cooccurring
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.io.Webapp
import tech.coner.trailer.io.service.ConfigurationService
import tech.coner.trailer.webapp.results.resultsWebapp

class WebappResultsCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "results",
    help = "Start the results web server"
) {

    override val diContext = diContextEnvironment()
    private val service: ConfigurationService by instance()

    private val config by WebappConfigurationOptions(di).cooccurring()

    override suspend fun coRun() {
        val config = config?.mapToIo()
            ?: service.getWebappConfiguration(Webapp.RESULTS).getOrThrow()
        resultsWebapp(di, config)
    }
}