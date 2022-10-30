package tech.coner.trailer.cli.command.webapp

import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.switch
import com.github.ajalt.clikt.parameters.types.choice
import io.ktor.server.engine.stopServerOnCancellation
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.currentCoroutineContext
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
    private val wait by option(hidden = true)
        .choice(
            "true" to true,
            "false" to false
        )
    private val stop by option(hidden = true).flag()

    private var serverJob: CompletableJob? = null

    override suspend fun coRun() {
        if (stop) {
            serverJob?.complete()
            return
        }
        val config = service.getWebappConfiguration(Webapp.RESULTS)
            .map { service.mergeWebappConfiguration(
                original = it,
                overridePort = port,
                overrideExploratory = exploratory,
                overrideWait = wait
            ) }
            .getOrThrow()
        serverJob = resultsWebapp(di, config)
            .stopServerOnCancellation()
    }


}