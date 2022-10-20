package exploratory.resource

import exploratory.service.HelloService
import io.ktor.resources.Resource
import io.ktor.server.application.call
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import kotlinx.serialization.Serializable
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

@Serializable
@Resource("/hello")
class HelloResource(val to: String = "World")

fun Route.helloRoutes() {
    val service: HelloService by closestDI().instance()
    get<HelloResource> {
        call.respondText(service.sayHello(it.to))
    }
    post<HelloResource> {
        call.respondText(service.sayHello(it.to))
    }
}
