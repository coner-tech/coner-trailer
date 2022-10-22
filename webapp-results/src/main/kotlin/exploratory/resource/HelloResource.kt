package exploratory.resource

import exploratory.service.HelloService
import exploratory.template.HelloTemplate
import io.ktor.resources.Resource
import io.ktor.server.application.call
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.resources.get
import io.ktor.server.routing.Route
import kotlinx.html.h1
import kotlinx.serialization.Serializable
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import tech.coner.trailer.webapp.results.template.RootTemplate

@Serializable
@Resource("/hello")
class HelloResource(val to: String = "World")

fun Route.helloRoutes() {
    val service: HelloService by closestDI().instance()
    get<HelloResource> { hello ->
        call.respondHtmlTemplate(RootTemplate { HelloTemplate() }) {
            title {
                text("Hello")
            }
            nav {
                h1 {
                    text("Hello")
                }
            }
            content {
                to {
                    text(service.sayHello(hello.to))
                }
            }
        }
    }
}
