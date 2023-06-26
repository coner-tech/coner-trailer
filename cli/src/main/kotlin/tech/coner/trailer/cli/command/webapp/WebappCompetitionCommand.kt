package tech.coner.trailer.cli.command.webapp

import io.ktor.server.engine.stopServerOnCancellation
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.io.Webapp
import tech.coner.trailer.io.service.ConfigurationService
import tech.coner.trailer.webapp.competition.competitionWebapp

class WebappCompetitionCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "competition",
    help = "Start the competition webapp server"
) {

    override val diContext = diContextEnvironment()
    private val service: ConfigurationService by instance()

    private val options by lazy { WebappConfigurationOptions(di) }
    private val port by options.port()
    private val exploratory by options.exploratory()

    override suspend fun coRun() {
        val config = service.getWebappConfiguration(Webapp.COMPETITION)
            .map { service.mergeWebappConfiguration(
                original = it,
                overridePort = port,
                overrideExploratory = exploratory
            ) }
            .getOrThrow()
        competitionWebapp(di, config)
            .stopServerOnCancellation()
    }


}