package exploratory.route

import exploratory.resource.HelloResource
import exploratory.service.HelloService
import exploratory.template.HelloTemplate
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.plugins.requestvalidation.ValidationResult
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import kotlinx.html.h1
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import tech.coner.trailer.webapp.results.template.RootTemplate


fun Route.helloRoutes() {
    val service: HelloService by closestDI().instance()
//    install(ResourcesValidation) {
//        validate<HelloResource> {
//            println("HelloResource validation")
//            ValidationResult.Valid
//        }
//    }
    install(RequestValidation) {
        validate {
            filter {
                println("validate > filter")
                println(it)
                false
            }
            validation {
                println("validate > validation")
                ValidationResult.Valid
            }
        }
    }
    get("/hello") {
        val hello = call.request.queryParameters["to"]?.let { HelloResource(it) }
            ?: HelloResource()
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
