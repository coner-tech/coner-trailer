package exploratory.resource

import exploratory.exploratoryModule
import io.ktor.server.routing.routing
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import org.kodein.di.ktor.subDI
import tech.coner.trailer.webapp.results.scaffoldResultsWebapp

fun testExploratoryResources(fn: suspend ApplicationTestBuilder.() -> Unit) = testApplication {
    application {
        scaffoldResultsWebapp()
        routing {
            subDI {
                import(exploratoryModule)
            }
            helloRoutes()
        }
    }
    fn()
}