package exploratory

import exploratory.resource.helloRoutes
import exploratory.resource.staticAssetRoutes
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.resources.Resources
import io.ktor.server.routing.routing
import io.ktor.server.webjars.Webjars
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
        install(Webjars) {
            path = "assets"
        }

        routing {
            subDI {
                import(exploratoryModule)
            }
            staticAssetRoutes()
            helloRoutes()
        }
    }
        .start(wait = true)
}