package exploratory.route

import exploratory.resource.HelloResource
import exploratory.service.HelloService
import exploratory.template.HelloTemplate
import io.ktor.http.Parameters
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.plugins.requestvalidation.RequestValidationException
import io.ktor.server.plugins.requestvalidation.ValidationResult
import io.ktor.server.request.receive
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.util.getOrFail
import io.ktor.util.pipeline.PipelineContext
import kotlinx.html.h1
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import tech.coner.trailer.webapp.results.template.RootTemplate

fun Routing.exploratoryRoutes() {
    val service: HelloService by closestDI().instance()
    suspend fun PipelineContext<Unit, ApplicationCall>.sayHello(hello: HelloResource) {
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
    route("/hello") {
        install(RequestValidation) {
            validate<Parameters> {
                when (val to = it["to"]) {
                    is String -> {
                        if (to.length > 32) ValidationResult.Invalid("Parameters: excessive greeting")
                        else ValidationResult.Valid
                    }
                    null -> ValidationResult.Invalid("Parameters: missing 'to'")
                    else -> ValidationResult.Valid
                }
            }
            validate<HelloResource> {
                if (it.to.length > 32) ValidationResult.Invalid("Object: excessive greeting")
                else ValidationResult.Valid
            }
        }
        get {
            val hello = call.request.queryParameters["to"]
                ?.let {
                    if (it.length > 32) throw RequestValidationException(it, listOf("Query parameter: excessive greeting"))
                    HelloResource(it)
                }
                ?: HelloResource()
            sayHello(hello)
        }
        post("json") {
            val hello = call.receive<HelloResource>()
            call.respond(mapOf("hello" to hello.to))
        }
        post("form") {
            val hello = call.receiveParameters().getOrFail("to").let { HelloResource(it) }
            call.respondText(service.sayHello(hello.to))
        }
    }
}
