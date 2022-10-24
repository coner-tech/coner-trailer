package tech.coner.trailer.cli.command.webapp

import com.github.ajalt.clikt.parameters.groups.cooccurring
import org.kodein.di.DI
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
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

    private val config by WebappConfigurationOptions().cooccurring()

    override suspend fun coRun() {
        val config = config?.mapToIo()
            ?: global.requireEnvironment().requireConfiguration().requireWebappResults()
        resultsWebapp(di, config)
    }
}