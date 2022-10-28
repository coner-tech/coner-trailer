package tech.coner.trailer.cli.command.webapp

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

    private val options by lazy { WebappConfigurationOptions(di) }
    private val port by options.port()
    private val exploratory by options.exploratory()

    override suspend fun coRun() {
        val config = service.getWebappConfiguration(Webapp.RESULTS)
            .map { service.mergeWebappConfiguration(
                original = it,
                overridePort = port,
                overrideExploratory = exploratory
            ) }
            .getOrThrow()
        resultsWebapp(di, config)
    }
}