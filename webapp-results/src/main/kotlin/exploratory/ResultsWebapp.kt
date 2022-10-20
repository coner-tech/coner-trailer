package exploratory

import exploratory.resource.helloRoutes
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.resources.Resources
import io.ktor.server.routing.routing
import org.kodein.di.DI
import org.kodein.di.ktor.di
import org.kodein.di.ktor.subDI
import tech.coner.trailer.io.WebappConfiguration

fun resultsWebapp(di: DI, config: WebappConfiguration) {
    embeddedServer(CIO, port = config.port) {
        di {
            // scaffold
        }
        install(Resources)
        routing {
            subDI {
                import(exploratoryModule)
            }
            helloRoutes()
        }
    }
        .start(wait = true)
}